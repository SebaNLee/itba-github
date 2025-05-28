
import java.util.*;

public class GraphDFSIterator<V, E> implements Iterator<V> {
    private final AdjacencyListGraph<V, E> graph;
    private final Set<V> visited = new HashSet<>();
    private final Stack<V> stack = new Stack<>();

    public GraphDFSIterator(AdjacencyListGraph<V, E> graph, V initialVertex) {
        this.graph = graph;
        if (initialVertex == null || graph.getAdjacencyList().get(initialVertex) == null)
            throw new RuntimeException("initial vertex no exist, u dum dum");
        stack.push(initialVertex);
        visited.add(initialVertex);
    }

    @Override
    public boolean hasNext() {
        return !stack.isEmpty();
    }

    @Override
    public V next() {
        if (!hasNext())
            throw new NoSuchElementException();

        V next = stack.pop();
        for (AdjacencyListGraph<V, E>.InternalEdge e : graph.getAdjacencyList().get(next))
            if (visited.add(e.target))
                stack.push(e.target);

        return next;
    }
}