

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.PriorityQueue;

public class DijkstraPath<V,E> {
    private Map<V,Integer> distancesFromSource;
    private Map<V,V> prevVertex;


    public DijkstraPath( Map<V,Integer> distancesFromSource , Map<V,V> prevVertex) {
        this.distancesFromSource= distancesFromSource;
        this.prevVertex= prevVertex;
    }

    @Override

    public String toString() {
        StringBuilder rta= new StringBuilder();

        for(V aV: distancesFromSource.keySet()) {
            if ( distancesFromSource.get(aV) == Integer.MAX_VALUE )
                rta.append("INF: [hacia ").append(aV).append("]\n");
            else
                rta.append(distancesFromSource.get(aV)).append(": ").append(getShortestPathTo(aV)).append("\n");
        }
        return rta.toString();
    }

    public String getShortestPathTo(V targetVertex){
        List<V> path = new ArrayList<>();

        for(V vertex=targetVertex;vertex!=null;vertex=prevVertex.get(vertex)){
            path.add(vertex);
        }

        Collections.reverse(path);
        return path.toString();
    }

    // de ChatGPT
    // public DijkstraPath<V, E> dijkstra(V source) {
    //     Map<V, Integer> dist = new HashMap<>();
    //     Map<V, V> prev = new HashMap<>();
    //     PriorityQueue<V> queue = new PriorityQueue<>(Comparator.comparingInt(dist::get));

    //     for (V v : this.getVertices()) {
    //         dist.put(v, Integer.MAX_VALUE);
    //         prev.put(v, null);
    //     }

    //     dist.put(source, 0);
    //     queue.add(source);

    //     while (!queue.isEmpty()) {
    //         V u = queue.poll();

    //         for (V neighbor : this.getAdjacentVertices(u)) {
    //             int weight = ((WeightedEdge) this.getEdge(u, neighbor)).getWeight();
    //             int alt = dist.get(u) + weight;
    //             if (alt < dist.get(neighbor)) {
    //                 dist.put(neighbor, alt);
    //                 prev.put(neighbor, u);
    //                 // update priority by removing and re-adding
    //                 queue.remove(neighbor);
    //                 queue.add(neighbor);
    //             }
    //         }
    //     }

    //     return new DijkstraPath<>(dist, prev);
    // }
}