

int balance(const char *texto)
{
    if(texto[0] == '\0')
        return 0;
    
    if(texto[0] == '(')
        return 1 + balance(texto + 1);

    if(texto[0] == ')')
        return -1 + balance(texto + 1);

    return balance(texto + 1);
}

// obs.: se pudo hacer con un switch()

// gcc ej12.c tests/tp09_ej12_test.c -o ej12 -Wall -pedantic -std=c99