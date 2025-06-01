

import java.util.*;

public class GraphBFSIterator<V, E> implements Iterator<V> {
    private final AdjacencyListGraph<V, E> graph;
    private final Set<V> visited = new HashSet<>();
    private final Queue<V> queue = new LinkedList<>();

    public GraphBFSIterator(AdjacencyListGraph<V, E> graph, V initialVertex) {
        this.graph = graph;
        if (initialVertex == null || graph.getAdjacencyList().get(initialVertex) == null)
            throw new RuntimeException("initial vertex no exist, u dum dum");
        queue.add(initialVertex);
        visited.add(initialVertex);
    }

    @Override
    public boolean hasNext() {
        return !queue.isEmpty();
    }

    @Override
    public V next() {
        if (!hasNext())
            throw new NoSuchElementException();

        V next = queue.remove();
        for (AdjacencyListGraph<V, E>.InternalEdge e : graph.getAdjacencyList().get(next))
            if (visited.add(e.target))
                queue.add(e.target);

        return next;
    }
}