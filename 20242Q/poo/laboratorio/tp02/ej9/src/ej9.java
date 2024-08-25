public class ej9 {
    public static void main(String[] args) {
        Integer[][] matriz = new Integer[3][4];
        for(int i = 0; i < 3; i++) {
            for(int j = 0; j < 4; j++) {
                matriz[i][j] = 3 - j + i;
            }
        }
    }
}


/*
 * en memoria quedaría:
 * 
 * 3 2 1 0
 * 4 3 2 1
 * 5 4 3 2
 */