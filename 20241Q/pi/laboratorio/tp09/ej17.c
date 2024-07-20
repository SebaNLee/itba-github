

int bienformado(char * s)
{
    if(s[0] == '\0')
        return 0;



    int aux = bienformado(s + 1);

    if(aux == 0 && s[0] >= '0' && s[0] <= '9')
        return s[0] - '0';

    else if(aux >= 0 && (s[0] >= 'a' && s[0] <= 'z'))
        return aux - 1;

    else
        return -1;
}

// gcc ej17.c tests/tp09_ej17_test.c -o ej17 -Wall -pedantic -std=c99