
// está mal, no quedó muy claro

void unirArreglos(int arr1[], int arr2[], int resultado[]){

    int i, j = 0, dim = 0;


    for(i = 0; arr1[i] != -1; i++){

        while(arr1[i] <= arr2[j]){

            if(arr1[i] == arr2[j]){

                resultado[dim++] = arr1[i];
            }

            j++;
        }
    }



}










