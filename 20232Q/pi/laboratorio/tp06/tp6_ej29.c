



void deleteChars(char *s1, char *s2){

    int i, j, aux1 = 0, aux2 = 0;

    while(s1[i] && s2[j]){
        if(s1[i] == s2[j]){
            i++;
            j++;
        }
        else{
            s1[aux1++] = s2[i++];
            s2[aux2++] = s1[j++];
        }
    }

    while(s1[aux1++] = s1[i++])
        ;
    while(s2[aux2++] = s2[j++])
        ;

}



