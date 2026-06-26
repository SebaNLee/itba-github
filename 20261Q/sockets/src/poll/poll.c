#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <unistd.h>
#include <arpa/inet.h>
#include <poll.h>

#define TCP_PORT 5000
#define UDP_PORT 6000
#define MAX_CLIENTS 10
#define BUFFER_SIZE 1024

int main(void) {
    int tcp_sock, udp_sock;
    struct sockaddr_in tcp_addr, udp_addr;
    struct pollfd fds[MAX_CLIENTS + 1];
    char buffer[BUFFER_SIZE];

    // Crear socket TCP
    tcp_sock = socket(AF_INET, SOCK_STREAM, 0);
    if (tcp_sock < 0) {
        perror("Error al crear socket TCP");
        exit(EXIT_FAILURE);
    }

    // Configurar dirección TCP
    memset(&tcp_addr, 0, sizeof(tcp_addr));
    tcp_addr.sin_family = AF_INET;
    tcp_addr.sin_addr.s_addr = INADDR_ANY;
    tcp_addr.sin_port = htons(TCP_PORT);

    // Enlazar el socket TCP
    if (bind(tcp_sock, (struct sockaddr *)&tcp_addr, sizeof(tcp_addr)) < 0) {
        perror("Error al enlazar socket TCP");
        close(tcp_sock);
        exit(EXIT_FAILURE);
    }

    // Escuchar conexiones entrantes
    listen(tcp_sock, MAX_CLIENTS);

    // Crear socket UDP
    udp_sock = socket(AF_INET, SOCK_DGRAM, 0);
    if (udp_sock < 0) {
        perror("Error al crear socket UDP");
        close(tcp_sock);
        exit(EXIT_FAILURE);
    }

    // Configurar dirección UDP
    memset(&udp_addr, 0, sizeof(udp_addr));
    udp_addr.sin_family = AF_INET;
    udp_addr.sin_addr.s_addr = INADDR_ANY;
    udp_addr.sin_port = htons(UDP_PORT);

    // Enlazar el socket UDP
    if (bind(udp_sock, (struct sockaddr *)&udp_addr, sizeof(udp_addr)) < 0) {
        perror("Error al enlazar socket UDP");
        close(tcp_sock);
        close(udp_sock);
        exit(EXIT_FAILURE);
    }

    // Configurar pollfd
    fds[0].fd = tcp_sock;
    fds[0].events = POLLIN;
    fds[1].fd = udp_sock;
    fds[1].events = POLLIN;

    while (1) {
        // Esperar eventos en los sockets
        int poll_count = poll(fds, 2, -1);
        if (poll_count < 0) {
            perror("Error en poll");
            break;
        }

        // Manejar eventos en el socket TCP
        if (fds[0].revents & POLLIN) {              // Posibles flags: POLLIN, POLLOUT, POLLER, POLLHUP, POLLNVAL
            int client_sock = accept(tcp_sock, NULL, NULL);
            if (client_sock < 0) {
                perror("Error al aceptar conexión");
                continue;
            }
            printf("Nueva conexión TCP aceptada\n");
            close(client_sock); // Manejar la conexión
        }

        // Manejar eventos en el socket UDP
        if (fds[1].revents & POLLIN) {
            struct sockaddr_in sender_addr;
            socklen_t sender_len = sizeof(sender_addr);
            ssize_t bytes_received = recvfrom(udp_sock, buffer, BUFFER_SIZE, 0,
                                              (struct sockaddr *)&sender_addr, &sender_len);
            if (bytes_received < 0) {
                perror("Error al recibir datos UDP");
                continue;
            }
            buffer[bytes_received] = '\0'; 
            printf("Datos recibidos por UDP: %s\n", buffer);
        }
    }

    // Cerramos los sockets antes de salir
    close(tcp_sock);
    close(udp_sock);
    return 0;
}