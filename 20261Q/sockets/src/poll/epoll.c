#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <sys/epoll.h>
#include <sys/socket.h>
#include <errno.h>
#include <fcntl.h>

#define TCP_PORT 5000
#define UDP_PORT 6000
#define MAX_EVENTS 20
#define BUFFER_SIZE 1024

// Función auxiliar para poner un socket en modo no bloqueante
int set_nonblocking(int fd) {
    int flags = fcntl(fd, F_GETFL, 0);
    if (flags == -1) return -1;
    return fcntl(fd, F_SETFL, flags | O_NONBLOCK);
}

int main(void) {
    int tcp_sock, udp_sock, epoll_fd;
    struct sockaddr_in tcp_addr, udp_addr;
    struct epoll_event ev, events[MAX_EVENTS];
    char buffer[BUFFER_SIZE];

    // Crear socket TCP
    tcp_sock = socket(AF_INET, SOCK_STREAM, 0);
    if (tcp_sock < 0) {
        perror("Error al crear socket TCP");
        exit(EXIT_FAILURE);
    }

    // Reutilizar dirección
    int opt = 1;
    setsockopt(tcp_sock, SOL_SOCKET, SO_REUSEADDR, &opt, sizeof(opt));

    // Configurar dirección TCP
    memset(&tcp_addr, 0, sizeof(tcp_addr));
    tcp_addr.sin_family = AF_INET;
    tcp_addr.sin_addr.s_addr = INADDR_ANY;
    tcp_addr.sin_port = htons(TCP_PORT);

    if (bind(tcp_sock, (struct sockaddr *)&tcp_addr, sizeof(tcp_addr)) < 0) {
        perror("Error al enlazar socket TCP");
        close(tcp_sock);
        exit(EXIT_FAILURE);
    }

    listen(tcp_sock, SOMAXCONN);
    set_nonblocking(tcp_sock);

    // Crear socket UDP
    udp_sock = socket(AF_INET, SOCK_DGRAM, 0);
    if (udp_sock < 0) {
        perror("Error al crear socket UDP");
        close(tcp_sock);
        exit(EXIT_FAILURE);
    }

    memset(&udp_addr, 0, sizeof(udp_addr));
    udp_addr.sin_family = AF_INET;
    udp_addr.sin_addr.s_addr = INADDR_ANY;
    udp_addr.sin_port = htons(UDP_PORT);

    if (bind(udp_sock, (struct sockaddr *)&udp_addr, sizeof(udp_addr)) < 0) {
        perror("Error al enlazar socket UDP");
        close(tcp_sock);
        close(udp_sock);
        exit(EXIT_FAILURE);
    }

    set_nonblocking(udp_sock);

    epoll_fd = epoll_create1(0);
    if (epoll_fd < 0) {
        perror("Error al crear epoll");
        exit(EXIT_FAILURE);
    }

    // Registrar socket TCP
    // EPOLLIN: recibir eventos cuando hay datos para leer en el descriptor de archivo
    ev.events = EPOLLIN;
    ev.data.fd = tcp_sock;
    epoll_ctl(epoll_fd, EPOLL_CTL_ADD, tcp_sock, &ev);

    // Registrar socket UDP
    ev.events = EPOLLIN;
    ev.data.fd = udp_sock;
    epoll_ctl(epoll_fd, EPOLL_CTL_ADD, udp_sock, &ev);

    while (1) {
        int nfds = epoll_wait(epoll_fd, events, MAX_EVENTS, -1);
        if (nfds < 0) {
            perror("epoll_wait");
            break;
        }

        for (int i = 0; i < nfds; ++i) {
            int fd = events[i].data.fd;

            if (fd == tcp_sock) {
                // Aceptar nueva conexión TCP
                int client_sock = accept(tcp_sock, NULL, NULL);
                if (client_sock < 0) {
                    perror("accept");
                    continue;
                }
                printf("Nueva conexión TCP aceptada (fd=%d)\n", client_sock);
                set_nonblocking(client_sock);

                // Registrar nuevo cliente en epoll
                // Algunos agregan | EPOLLET, en ese caso sólo avisa cuando cambia el estado, pero si se lee parcialmente el buffer de TCP
                // en la siguiente iteración no se desbloquea, no avisa que hay datos para leer. En caso de usar EPOLLET se deben leer
                // todos los datos disponibles
                ev.events = EPOLLIN ;
                ev.data.fd = client_sock;
                epoll_ctl(epoll_fd, EPOLL_CTL_ADD, client_sock, &ev);

            } else if (fd == udp_sock) {
                // Manejar mensaje UDP
                struct sockaddr_in sender_addr;
                socklen_t sender_len = sizeof(sender_addr);
                ssize_t bytes_received = recvfrom(fd, buffer, BUFFER_SIZE - 1, 0,
                                                  (struct sockaddr *)&sender_addr, &sender_len);
                if (bytes_received > 0) {
                    buffer[bytes_received] = '\0';
                    printf("UDP: %s\n", buffer);
                }

            } else {
                // Manejar datos de cliente TCP
                ssize_t bytes = recv(fd, buffer, BUFFER_SIZE - 1, 0);
                if (bytes <= 0) {
                    if (bytes == 0) {
                        printf("Cliente TCP (fd=%d) cerró la conexión\n", fd);
                    } else {
                        perror("recv");
                    }
                    // Eliminar del epoll y cerrar
                    epoll_ctl(epoll_fd, EPOLL_CTL_DEL, fd, NULL);
                    close(fd);
                } else {
                    buffer[bytes] = '\0';
                    printf("TCP (fd=%d): %s\n", fd, buffer);
                }
            }
        }
    }

    close(tcp_sock);
    close(udp_sock);
    close(epoll_fd);
    return 0;
}
