#include <stdio.h>
#include <string.h>
#include <stdlib.h>
#include <netdb.h>
#include <errno.h>
#include <unistd.h>   
#include <arpa/inet.h>    
#include <sys/epoll.h>
#include <netinet/in.h>
#include "logger.h"
#include "tcpServerUtil.h"

#define max(n1,n2)     ((n1)>(n2) ? (n1) : (n2))

#define TRUE   1
#define FALSE  0
#define PORT 8888
#define MAX_SOCKETS 3000
#define BUFFSIZE 1024
#define PORT_UDP 8888
#define MAX_PENDING_CONNECTIONS   3    // un valor bajo, para realizar pruebas

typedef struct socketInfo {
	int  fd;		// file descriptor que identifica al socket
	char * buffer;
	size_t len;     // longitud del buffer
	size_t from;    // desde donde falta escribir
} socketInfo;f

/**
  Se encarga de escribir la respuesta faltante en forma no bloqueante
  Retorna 1 si escribió todos los datos que habia en el buffer
  */
int handleWrite(socketInfo * buffer);

/**
  Limpia el buffer de escritura asociado a un socket
  */
void clear(socketInfo * buffer);

/**
  Crea y "bindea" el socket server UDP
  */
int udpSocket(int port);

/**
  Lee el datagrama del socket, obtiene info asociado con getaddrInfo y envia la respuesta
  */
void handleAddrInfo(int socket);


struct socketInfo * newSocket(int fd) {
	socketInfo * ptr = calloc(1, sizeof(struct socketInfo)); // TODO verificar calloc
	ptr->fd = fd;
	return ptr;
}

int main(int argc , char *argv[]) {
	int opt = TRUE;
	int tcpSocket;  
	int new_socket;
	long valread;

	char buffer[BUFFSIZE + 1];  // buffer de lectura

    close(0);
	
	// dual stack socket (IPv4 and IPv6)
	if( (tcpSocket = socket(AF_INET6 , SOCK_STREAM , 0)) < 0) {
		perror("socket dual stacck failed");
		log(FATAL, "socket dual stack failed");
	} else {
		int disable_v6only=0;
		setsockopt(tcpSocket, IPPROTO_IPV6, IPV6_V6ONLY, &disable_v6only, sizeof(disable_v6only));
		
		// Permite reutilizar la dirección local (evita "Address already in use" tras reinicios rápidos)
		if( setsockopt(tcpSocket, SOL_SOCKET, SO_REUSEADDR, (char *)&opt, sizeof(opt)) < 0 )
		{
			log(ERROR, "set IP socket options failed");
		}

		struct sockaddr_in6 address;
		address.sin6_family = AF_INET6;
		address.sin6_addr = in6addr_any;
		address.sin6_port = htons( PORT );

		if (bind(tcpSocket, (struct sockaddr *)&address, sizeof(address))<0) 
		{
			log(ERROR, "bind for IP failed");
			perror("");
			close(tcpSocket);
		}
		else {
			if (listen(tcpSocket, MAX_PENDING_CONNECTIONS) < 0)
			{
				log(ERROR, "listen on IP socket failes");
				close(tcpSocket);
			} else {
				log(DEBUG, "Waiting for TCP IPv4 connections on socket %d\n", tcpSocket);
			}
		}
	}

	// Socket UDP para responder en base a addrInfo
	int udpSock = udpSocket(PORT);
	if ( udpSock < 0) {
		log(FATAL, "UDP socket failed");
	} else {
		log(DEBUG, "Waiting for UDP IPv4 on socket %d\n", udpSock);
	}

	// Inicializamos epoll
	struct epoll_event ev, events[MAX_SOCKETS];
	int epoll_fd = epoll_create1(0);
	log(DEBUG, "epoll created on file descriptor %d\n", epoll_fd);
	// registrar socket TCP
	ev.events = EPOLLIN;
	ev.data.ptr = newSocket(tcpSocket);
    epoll_ctl(epoll_fd, EPOLL_CTL_ADD, tcpSocket, &ev);
	
	// registrar socket UDP
	ev.events = EPOLLIN;
	ev.data.ptr = newSocket(udpSock);
    epoll_ctl(epoll_fd, EPOLL_CTL_ADD, udpSock, &ev);

	while(TRUE){
		int nfds = epoll_wait(epoll_fd, events, MAX_SOCKETS, -1);
        if (nfds < 0) {
            perror("epoll_wait");
            break;
        }

		for (int i = 0; i < nfds; ++i) {
            struct socketInfo * aux = events[i].data.ptr;
			int fd = aux->fd;

			if (fd == tcpSocket) {
				if ((new_socket = acceptTCPConnection(tcpSocket)) < 0)
				{
					log(ERROR, "Accept error on master socket %d", tcpSocket);
					continue;
				}
				ev.events = EPOLLIN;
                ev.data.ptr = newSocket(new_socket);
                epoll_ctl(epoll_fd, EPOLL_CTL_ADD, new_socket, &ev);
				log(DEBUG, "New TCP client on socket %d", new_socket);
			} else if ( fd == udpSock) {
				handleAddrInfo(udpSock);
			} else { // es un socket activo
				if ( events[i].events & EPOLLOUT ) {
					if ( handleWrite(aux) ) {
						// Si retorna true es porque escribio todo lo que habia en el buffer
						// Cambiamos para que solo registre el evento de lectura
						ev.events = EPOLLIN;
						ev.data.ptr = aux;
						epoll_ctl(epoll_fd, EPOLL_CTL_MOD, fd, &ev);
					}
				}
				// El mismo socket tambien puede tener datos para lectura
				if ( events[i].events & EPOLLIN ) {
					//Check if it was for closing , and also read the incoming message
					if ((valread = read( fd , buffer, BUFFSIZE)) <= 0)
					{
						// El cliente cerro la sesion
						log(DEBUG, "Host disconnected , socket %d\n" , fd);

						// Cerrar el socket, des-registrar el evento y liberar el struct con el buffer
						close(fd);
						epoll_ctl(epoll_fd, EPOLL_CTL_DEL, fd, NULL);
						free(aux->buffer);
						free(aux);
					}
					else {
						log(DEBUG, "Received %zu bytes from socket %d\n", valread, fd);
						// activamos el evento para escritura y almacenamos en el buffer de salida
						ev.events = EPOLLIN | EPOLLOUT;
						ev.data.ptr = aux;
						epoll_ctl(epoll_fd, EPOLL_CTL_MOD, fd, &ev);

						// Tal vez ya habia datos en el buffer
						// TODO: validar realloc != NULL
						aux->buffer = realloc(aux->buffer, aux->len + valread);
						memcpy(aux->buffer + aux->len, buffer, valread);
						aux->len += valread;
					}
				}
			}
		}
	}
	return 0;
}

void clear( struct socketInfo * buffer) {
	free(buffer->buffer);
	buffer->buffer = NULL;
	buffer->from = buffer->len = 0;
}

// Hay algo para escribir?
// Si está listo para escribir, escribimos. El problema es que a pesar de tener buffer para poder
// escribir, tal vez no sea suficiente. Por ejemplo podría tener 100 bytes libres en el buffer de
// salida, pero le pido que mande 1000 bytes.Por lo que tenemos que hacer un send no bloqueante,
// verificando la cantidad de bytes que pudo consumir TCP.
int handleWrite(struct socketInfo * buffer) {
	int socket = buffer->fd;
	size_t bytesToSend = buffer->len - buffer->from;
	if (bytesToSend > 0) {  // Puede estar listo para enviar, pero no tenemos nada para enviar
							// Esto no deberia pasar si seteamos el evento para escritura solo si hay datos para enviar
		log(INFO, "Trying to send %zu bytes to socket %d\n", bytesToSend, socket);
		size_t bytesSent = send(socket, buffer->buffer + buffer->from,bytesToSend,  MSG_DONTWAIT); 
		log(INFO, "Sent %zu bytes\n", bytesSent);

		if ( bytesSent < 0) {
			// Esto no deberia pasar ya que el socket estaba listo para escritura
			log(FATAL, "Error sending to socket %d", socket);
		} else {
			size_t bytesLeft = bytesToSend - bytesSent;
			// Si se pudieron mandar todos los bytes limpiamos el buffer
			if ( bytesLeft == 0) {
				clear(buffer);
				return 1;
			} else {
				buffer->from += bytesSent;
				return 0;
			}
		}
	}
	return 1;
}

int udpSocket(int port) {
	int sock;
	struct sockaddr_in serverAddr;
	if ( (sock = socket(AF_INET, SOCK_DGRAM, 0)) < 0 ) {
		log(ERROR, "UDP socket creation failed, errno: %d %s", errno, strerror(errno));
		return sock;
	}
	log(DEBUG, "UDP socket %d created", sock);
	memset(&serverAddr, 0, sizeof(serverAddr));
	serverAddr.sin_family    = AF_INET; // IPv4cle
	serverAddr.sin_addr.s_addr = INADDR_ANY;
	serverAddr.sin_port = htons(port);

	if ( bind(sock, (const struct sockaddr *)&serverAddr, sizeof(serverAddr)) < 0 )
	{
		log(ERROR, "UDP bind failed, errno: %d %s", errno, strerror(errno));
		close(sock);
		return -1;
	}
	log(DEBUG, "UDP socket bind OK ");

	return sock;
}


void handleAddrInfo(int socket) {
	// En el datagrama viene el nombre a resolver
	// Se le responde con la informacion asociada
	char buffer[BUFFSIZE];
	unsigned int len, n;

	struct sockaddr_in clntAddr;
	len = sizeof(clntAddr);

	// Es bloqueante, deberian invocar a esta funcion solo si hay algo disponible en el socket    
	n = recvfrom(socket, buffer, BUFFSIZE, 0, ( struct sockaddr *) &clntAddr, &len);
	if ( buffer[n-1] == '\n') 	// Por si lo estan probando con netcat, en modo interactivo
		n--;
	buffer[n] = '\0';
	log(DEBUG, "UDP received:%s", buffer );
	// TODO: parsear lo recibido para obtener nombre, puerto, etc. Asumimos viene solo el nombre

	// Especificamos solo SOCK_STREAM para que no duplique las respuestas
	struct addrinfo addrCriteria;                   // Criteria for address match
	memset(&addrCriteria, 0, sizeof(addrCriteria)); // Zero out structure
	addrCriteria.ai_family = AF_UNSPEC;             // Any address family
	addrCriteria.ai_socktype = SOCK_STREAM;         // Only stream sockets
	addrCriteria.ai_protocol = IPPROTO_TCP;         // Only TCP protocol


	// Armamos el datagrama con las direcciones de respuesta, separadas por \r\n
	char bufferOut[BUFFSIZE];
	bufferOut[0] = '\0';

	struct addrinfo *addrList;
	int rtnVal = getaddrinfo(buffer, NULL, &addrCriteria, &addrList);
	if (rtnVal != 0) {
		log(ERROR, "getaddrinfo() failed: %d: %s", rtnVal, gai_strerror(rtnVal));
		strcat(strcpy(bufferOut,"Can't resolve "), buffer);  	// TODO: hacer una concatenacion segura (strncat)
		strcat(bufferOut, "\r\n");
	} else {
		for (struct addrinfo *addr = addrList; addr != NULL; addr = addr->ai_next) {
			struct sockaddr *address = addr->ai_addr;
			char addrBuffer[INET6_ADDRSTRLEN];

			void *numericAddress = NULL;
			switch (address->sa_family) {
				case AF_INET:
					numericAddress = &((struct sockaddr_in *) address)->sin_addr;
					break;
				case AF_INET6:
					numericAddress = &((struct sockaddr_in6 *) address)->sin6_addr;
					break;
			}
			if ( numericAddress == NULL) {
				strcat(bufferOut, "[Unknown Type]");  
			} else {
				// Convert binary to printable address
				if (inet_ntop(address->sa_family, numericAddress, addrBuffer, sizeof(addrBuffer)) == NULL)
					strcat(bufferOut, "[invalid address]");
				else {
					strcat(bufferOut, addrBuffer);  // TODO: hacer una concatenacion segura (strncat)
				}
			}
			strcat(bufferOut, "\r\n");
		}
		freeaddrinfo(addrList);
	}
	// Enviamos respuesta (el sendto no bloquea)
	sendto(socket, bufferOut, strlen(bufferOut), 0, (const struct sockaddr *) &clntAddr, len);

	log(DEBUG, "UDP sent:%s", bufferOut );
}