

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

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
}