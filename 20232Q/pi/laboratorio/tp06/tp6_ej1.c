


#define N 10
#define M 20

int main (void){

    int dim = 7;
    int vectorA[ M * N];
    int vectorB[ -10 ]; // error, negativo
    int vectorC[ 10.0 ]; // error, no entero
    int vectorD[dim];
    vectorC[2.5] = 'a'; // error, no entero
    vectorB[-1] = 5; // error
    vectorA['0'] = 10; // error
    vectorC[vectorA[48]] = 5.5;
    vectorA[1000] = 0; // fuera de rango, error
    vectorA[M * N] = 10;

    return 0;
}



