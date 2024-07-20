

int apareados(const char *texto)
{
    if(texto[0] == '\0')
        return 0;

    int aux = apareados(texto + 1);

    if(aux < 0) // es como un doble caso base
        return aux;

    if(texto[0] == '(')
        return aux - 1;

    if(texto[0] == ')')
        return aux + 1;
}

// gcc ej14.c tests/tp09_ej14_test.c -o ej14 -Wall -pedantic -std=c99