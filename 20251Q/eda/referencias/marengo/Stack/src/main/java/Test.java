public class Test {
    public static void main(String[] args) {
        CompactIndex index = new CompactIndex();
        index.initialize(new String[]{"hola", "hola", "hola", "hola", "casa", "casa", "casa", "aaaaa"});
        index.printElements();
    }
}
