import java.util.Objects;

public class WeightedEdge implements Comparable<WeightedEdge>{
    private Integer weight;

    public WeightedEdge(int weight) {
        this.weight= weight;
    }

    public int getWeight() {
        return weight;
    }

    @Override
    public int hashCode() {
        return Objects.hash(weight);
    }

    public String toString() {
        return String.format("[weight %s]", weight);
    }

    @Override
    public int compareTo(WeightedEdge o) {
        return weight.compareTo(o.weight);
    }
}