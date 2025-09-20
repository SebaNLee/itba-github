




#include <stdio.h>
#include <stdlib.h>
#include <pthread.h>

#define N_THREADS 7
#define N_INCREMENTOS 100000

int contador = 0;

void* incrementar(void* arg) {
    for (int i = 0; i < N_INCREMENTOS; i++) {

        contador++; // acá carrera
    }
    return NULL;
}

int main() {
    pthread_t threads[N_THREADS];

    for (int i = 0; i < N_THREADS; i++) {
        pthread_create(&threads[i], NULL, incrementar, NULL);
    }

    for (int i = 0; i < N_THREADS; i++) {
        pthread_join(threads[i], NULL);
    }

    printf("Valor final del contador (sin semáforo): %d\n", contador);
    printf("Valor esperado: %d\n", N_THREADS * N_INCREMENTOS);
    return 0;
}
