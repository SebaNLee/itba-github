#include <stdio.h>

int main(void){


    int c, flagBlank = 0;

    while((c = getchar()) != EOF){
        if(c == ' '){
            flagBlank = 1;
        }
        else if(c != ' ' && flagBlank == 1){
            printf(" %c", c);
            flagBlank = 0;
        }
        else{
        printf("%c", c);
        }
    }






    return 0;
}
