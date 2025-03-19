

public class Levenshtein {
    
    // constructor default

    public static int distance(String str1, String str2) {

        int[][] data = new int[str1.length() + 1][str2.length() + 1];

        // relleno valores iniciales

        data[0][0] = 0;

        for (int i = 1; i < str1.length() + 1; i++) {
            data[i][0] = i;
        }

        for (int j = 1; j < str2.length() + 1; j++) {
            data[0][j] = j;
        }

        // calculo los demás valores

        for (int i = 1; i < str1.length() + 1; i++) {
            for (int j = 1; j < str2.length() + 1; j++) {

                data[i][j] = Math.min(Math.min(
                    data[i - 1][j - 1] + ((str1.charAt(i - 1) == str2.charAt(j - 1)) ? 0 : 1), // charAt() comienza desde 1
                    data[i - 1][j] + 1),
                    data[i][j - 1] + 1
                );
            }
        }

        return data[str1.length()][str2.length()];
    }   

    public static float normalizedSimilarity(String str1, String str2) {
        return 1 - ((float) distance(str1, str2) / Math.max(str1.length(), str2.length()));
    }
}
