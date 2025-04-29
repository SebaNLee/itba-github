import core.AVL;
import core.AVLTreeInterface;

public class AVLTest {
    public static void main(String[] args) {
        AVLTreeInterface<Integer> avl = new AVL<>();
        avl.insert(20);
        avl.insert(10);
        avl.insert(30);
        avl.insert(15);
        avl.insert(25);
        avl.insert(70);
        avl.insert(40);
        System.out.println(avl);

        System.out.println("....");
        System.out.println("....");
        avl.insert(50);
        System.out.println(avl);

        System.out.println("....");
        System.out.println("....");
        avl.insert(12);
        System.out.println(avl);

        System.out.println("....");
        System.out.println("....");
        avl.insert(80);
        System.out.println(avl);

        System.out.println("....");
        System.out.println("....");
        avl.insert(90);
        System.out.println(avl);
    }
}
