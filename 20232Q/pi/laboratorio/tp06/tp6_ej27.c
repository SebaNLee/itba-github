
#define MAX 256

void eliminaCeros(char *chars){

    int i, j = 0;

    for(i = 0; i < MAX; i++){
        if(!(chars[i] == 0)){
            chars[j++] = chars[i];
        }
    }
}



void analize(const char *text, char *chars){

    for(int i = 0; i < MAX; i++){
        chars[i] = 0;
    }

    for(int j = 0; text[j]; j++){
        chars[text[j]] = text[j];
    }

    eliminaCeros(chars);
}








