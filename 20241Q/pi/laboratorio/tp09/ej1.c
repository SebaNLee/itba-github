

int sumaVec(const int *vector, unsigned int dim)
{
    if(dim == 0)
    {
        return 0;
    }
    
    return sumaVec(vector + 1, dim - 1) + vector[0];
}

// gcc ej1.c tests/tp09_ej01_test.c -o ej1 -Wall -pedantic -std=c99