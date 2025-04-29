import core.BTree;

public class BTreeTest {

    public static void main(String[] args) {

        BTree<Integer> st = new BTree<>(2);

        for(int rec= 0; rec < 20; rec++) {
            st.add(rec);
            System.out.println(st);
            System.out.println("....");
        }
    }
}