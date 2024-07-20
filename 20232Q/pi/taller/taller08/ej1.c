


// 1

int i;
arr = malloc(TAM * sizeof(int)); // reserva TAM espacios con basura

// entonces se crea ciclo para asignar 0s
for(int i = 0; i < TAM; i++){
    arr[i] = 0;
}


mostrarArreglo(arr, TAM);
free(arr);



// 2

arr = realloc(arr, TAM * sizeof(int)); // se asume que ya tenía algo inicializado, como arr = NULL de antes
// simplemente le agrego el tamaño que quiero
// puede ser NULL si algo falla
// porque no hay memoria (poco probable) || si TAM es negativo o racional (por no validar) 

// y le agrego el mismo ciclo para asignar 0s
for(int i = 0; i < TAM; i++){
    arr[i] = 0;
}



// 3

arr = calloc(TAM, sizeof(int));
// el calloc me asigna todo en 0s
// no hace falta el ciclo, simplemente agrego el tamaño
