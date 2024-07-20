




void eliminaBlancos(char string[]){

    int i, j;

    for(i = 0, j = 0; string[i]; i++){

        if(!(string[i] == ' ' && string[i - 1] == ' ') || j == 0){
            string[j++] = string[i];

        }


    }

    string[j] = 0;

    return;
}





