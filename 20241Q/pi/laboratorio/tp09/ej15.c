

void piramide(int *latas, int *h)
{
    if(*latas == 0)
        return;

    if(*latas >= (*h + 1) * (*h + 1))
    {
        *latas = *latas - ((*h + 1) * (*h + 1));
        *h = *h + 1;

        piramide(latas, h);

        return;
    }    
}

// gcc ej15.c tests/tp09_ej15_test.c -o ej15 -Wall -pedantic -std=c99