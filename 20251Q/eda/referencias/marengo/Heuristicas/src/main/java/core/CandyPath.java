package core;

public class CandyPath {

    public static void main(String[] args) {
        CandyPath hansel = new CandyPath();
        System.out.println(hansel.solve(new int [][] {{2, 4, 1, 3}, {5, 1, 2, 4}, {2, 2, 3, 2}, {1, 7, 1, 3}}));
    }

    public int solve(int[][] matrix) {
        Integer[][] aux = new Integer[matrix.length][matrix.length];
        for(int i = 0; i < matrix.length; ++i) {
            aux[i][0] = matrix[i][0];
        }

        for(int i=1; i < matrix.length; ++i) {
            for(int j=0; j < matrix.length; ++j) {
                aux[j][i] = matrix[j][i] + Math.max(aux[Math.max(0,j-1)][i-1], Math.max(aux[j][i-1],
                        aux[Math.min(matrix.length-1, j+1)][i-1]));
            }
        }

        int max = 0;
        for(int i=0; i < matrix.length; ++i) {
            if(max < aux[i][matrix.length-1])
                max = aux[i][matrix.length-1];
        }

        return max;
    }



}