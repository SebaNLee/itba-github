#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>
#include <semaphore.h>

#define N_THREADS 7
#define N_INCREMENTOS 100000

int contador = 0;
sem_t semaforo;

void* incrementar(void* arg) {
    for (int i = 0; i < N_INCREMENTOS; i++) {
        sem_wait(&semaforo);
        contador++; // zona crítica
        sem_post(&semaforo);
    }
    return NULL;
}

int main() {
    pthread_t threads[N_THREADS];
    sem_init(&semaforo, 0, 1);

    for (int i = 0; i < N_THREADS; i++) {
        pthread_create(&threads[i], NULL, incrementar, NULL);
    }

    for (int i = 0; i < N_THREADS; i++) {
        pthread_join(threads[i], NULL);
    }

    printf("Valor final del contador (con semáforo): %d\n", contador);
    printf("Valor esperado: %d\n", N_THREADS * N_INCREMENTOS);

    sem_destroy(&semaforo);
    return 0;
}
