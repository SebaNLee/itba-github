

int igualesRet(int a[], int b[]){
  
    if(*a != *b){ // esta comparación tenía que venir antes que el otro, por si uno de los vectores empieza con -1
        return 0;
    }  
    if(*a == -1 || *b == -1){
        return 1;
    }

    return igualesRet(a + 1, b + 1);
}



void igualesVoid(int a[], int b[], int * rta){

    if(*a != *b){
        *rta = 0;
        return;
    }

    if(*a == -1 || *b == -1){
        *rta = 1;
        return;
    }
    
    igualesVoid(a + 1, b + 1, rta);
    return;
}







