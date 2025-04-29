public class Levenshtein2 {

    public static int implementation(String str1, String str2){
        int length1 = str1.length(); //anda a saber que hace
        int length2 = str2.length(); //anda a saber que hace
        int[][] ldistance = new int[length1+1][length2+1];

        for(int k=0; k<length1+1; ++k) { //2*length1 (suma y comp) + 1 (length1+1)
            ldistance[k][0] = k;               //+
        }                                      //+
        for(int m=0; m<length2+1; ++m) { //2*length2 (suma y comp) + 1 (length2+1)
            ldistance[0][m] = m;
        }

        for(int i=1; i<length1+1; ++i) { //2*length1 (la suma y la comp) + 1 (length1+1)
            for(int j=1; j<length2+1; ++j) { //2*length2 (la suma y la comp) + 1 (length2+1)
                ldistance[i][j] = Math.min(Math.min(ldistance[i-1][j-1]+(str1.charAt(i-1)==str2.charAt(j-1)?0:1), ldistance[i-1][j]+1),ldistance[i][j-1]+1);
                //2*length1*length2 + 9 (sumas y restas) + 2charAt+
                System.out.print(ldistance[i][j]);
            }
            System.out.println();
        }

        return ldistance[length1][length2];
    }
    //12*length1*length2+length1+length2 -> O(length1*length2) TEMPORAL

    public static double similitude(String str1, String str2){
        if(str1.length()==0 && str2.length()==0)
            return 0;
        return 1-((double)implementation(str1,str2)/Math.max(str1.length(),str2.length()));
    }


    public static int implementation2(String str1, String str2) {
        if(str2.length() < str1.length()) { //dejo en str1 el string con length menor
            String aux = str1;
            str1 = str2;
            str2 = aux;
        }
        int maxLength = str2.length();
        int minLength = str1.length();
        int[] aux = new int[minLength+1];
        int[] ldistance = new int[minLength+1];

        for(int i=1; i<minLength+1; i++)
            aux[i] = i; //un vector con 1, 2, 3, ... ,length1
        ldistance[0] = 1; //simulo posicion [1][0] de la matriz

        for(int i=1; i<maxLength+1; ++i) { //hago una fila de la matriz, teniendo la anterior fila y voy intercalando entre los vectores ldistance y aux
            for(int j=1; j<minLength+1; ++j) {
                boolean b = str1.charAt(j - 1) == str2.charAt(i - 1);
                if(i % 2 != 0) {
                    ldistance[j] = Math.min(Math.min(aux[j - 1] + (b ? 0 : 1), aux[j] + 1), ldistance[j - 1] + 1);
                }
                else {
                    aux[j] = Math.min(Math.min(ldistance[j-1]+(b?0:1), ldistance[j]+1),aux[j-1]+1);
                }
            }
            ldistance[0]++; //simulo la primer columna de la matriz
            aux[0]++; //con este tambien
        }
        return Math.min(aux[minLength], ldistance[minLength]); //retorno el menor, para no preguntar cual fue el ultimo vector modificado, total el menor es el que cuenta
    }

}
