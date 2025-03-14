public class Fibonacci {

    public static void main(String[] args) {

        int counter = 1;

        while (true) {

            System.out.println(fibonacci(counter));

            counter++;
        }

    }

    public static long fibonacci(long position) {
        if (position < 2) {
            return position;
        }

        return fibonacci(position - 1) + fibonacci(position - 2);
    }
}
