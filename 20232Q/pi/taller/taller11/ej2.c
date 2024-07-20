


typedef int (*fnInt) (int);

void transforma(int v[], int n, fnInt f){

    if(!n){
        return;
    }
    *v = f(*v);
    transforma(v + 1, n - 1, f);

    return;
}



