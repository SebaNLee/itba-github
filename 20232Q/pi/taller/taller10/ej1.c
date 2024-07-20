


int esPotenciaDe3(int n){

    if(n == 1){
        return 1;
    }
    if(n == 0 || n % 3 != 0){
        return 0;
    }

    return esPotenciaDe3(n / 3);
}


