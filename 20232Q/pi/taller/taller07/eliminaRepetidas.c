

void eliminaRepetidas(char cadena[]){

    char anterior;
    int i = 1, j = 1;

    anterior = cadena[0];

    do{

        if(anterior != cadena[i]){
            cadena[j] = cadena[i];
            j++;
        }

        anterior = cadena[i];
        i++;

    }while(cadena[i]);

    cadena[j] = 0;


    return;
}


// bien el ejercicio.\, pero asume que siempre existe el primer carácter

// también se puede comenzar con 0 los índices y comparar con el siguiente en vez del anterior
// también se puede implementar un ciclo for que termine cuando sea \0








