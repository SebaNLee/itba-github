

int comprimeCar(char s[], char c, int vec[], int maxDim){

    int i, j = 1, cantApariciones = 0;


    for(i = 1; s[i] && sizeof(vec[]) <= maxDim ; i++){
        
        if(s[i] != s[i - 1] && s[i] != " "){

            s[j++] = s[i];
        }else if(s[i] == " "){

        }else{
            cantApariciones;
        }


    }


// está mal, repasar de solucionario



    return cantApariciones;
}








