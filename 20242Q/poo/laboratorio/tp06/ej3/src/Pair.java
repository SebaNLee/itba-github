public class Pair<E> {

    private E left;
    private E right;

    Pair(E left, E right) {
        this.left = left;
        this.right = right;
    }

    @Override
    public String toString() {
        return "# %s + %s #".formatted(left, right);
    }
}
