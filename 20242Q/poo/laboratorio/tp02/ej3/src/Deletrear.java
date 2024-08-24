public class Deletrear {
    public static void main(String[] args) throws Exception {

        for(String curr : args) {

            int i = 0;

            for(; i < curr.length() - 1; i++) {
                System.out.print(curr.charAt(i) + "-");
            }
            System.out.println(curr.charAt(i));
        }


    }
}


// !! para correr código con argumentos custom
// hacer javac para compilar
// y despues correr manualmente con argumentos