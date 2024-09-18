
public class StackTester {

    public static void main(String[] args) {
        Stack<Integer> stack = new ArrayStack<>();
        stack.push(2);
        stack.push(3);
        System.out.println(stack);
        System.out.println(stack.peek());
        System.out.println(stack.pop());
        System.out.println(stack.isEmpty());
        System.out.println(stack.pop());
        System.out.println(stack.isEmpty());
        System.out.println(stack.pop());
    }

}

/* salida esperada:
    [3,2]
    3
    3
    false
    2
    true
    Exception in thread "main" java.util.EmptyStackException
    at ar.edu.itba.poo.tp5.stack.ArrayStack.pop(ArrayStack.java:34)
    at ar.edu.itba.poo.tp5.stack.StackTester.main(StackTester.java:15)
*/


// salteo diagrama de clases