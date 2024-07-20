


typedef int (*fn2Int) (int, int);

int operatoria(int v[], int dim, int base, fn2Int f){

    if(dim == 0){
        return base;
    }

    return f(*v, operatoria(v + 1, dim - 1, base, f));
}


