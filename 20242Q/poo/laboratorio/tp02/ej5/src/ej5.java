public class ej5 {
    public static void main(String[] args) {

        StringBuilder frase = new StringBuilder();

        for(String s : args) {
            frase.append(s);
        }

        String s = frase.toString();

        if(checkPalindromo(s)) {
            System.out.println("ES PALINDROMO");
        }
        else {
            System.out.println("NO ES PALINDROMO");
        }

        return;
    }

    public static boolean checkPalindromo(String s) {

        for(int i = 0; i < s.length() / 2; i++) {
            if(s.charAt(i) != s.charAt(s.length() - i - 1)) {
                return false;
            }
        }

        return true;
    }
}

// !! hay que usar StringBuilder para loops