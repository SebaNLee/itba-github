package slee;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.function.BiFunction;

public class MultiDirectedGraph<V, E> {

    private Map<V, Collection<InternalEdge>> adjacencyList= new HashMap<>();

    public Collection<V> getVertices() {
        return adjacencyList.keySet() ;
    }

    protected MultiDirectedGraph(){
    }

    public void addVertex(V aVertex) {

        if (aVertex == null )
            throw new IllegalArgumentException("addVertexParamCannotBeNull");

        // no edges yet
        adjacencyList.putIfAbsent(aVertex,
                new ArrayList<InternalEdge>());
    }

    public void addEdge(V aVertex, V otherVertex, E theEdge) {

        if (aVertex == null || otherVertex == null || theEdge == null)
            throw new IllegalArgumentException("addEdgeParamCannotBeNull");

        addVertex(aVertex);
        addVertex(otherVertex);

        adjacencyList.get(aVertex).add( new InternalEdge(theEdge, otherVertex) );
    }

    void printAllVertexes(){
        for( V v : getVertices() )
            System.out.println( v );
    }

    void printAllEdges(){
        for( Map.Entry<V, Collection<InternalEdge>> entry : adjacencyList.entrySet() )
            for( InternalEdge i : entry.getValue() )
                System.out.println( entry.getKey().toString() + " -> "  + i.target.toString() + "[" + i.edge.toString() + "]" );
    }


    class InternalEdge {
        E edge;
        V target;

        InternalEdge(E propEdge, V target) {
            this.target = target;
            this.edge = propEdge;
        }
    }

    MultiDirectedGraph<V,E> transposeSummarize( BiFunction<E, E, E> summarizer ){
        // TODO: ej1
        

        MultiDirectedGraph<V, E> newGraph = new MultiDirectedGraph<>();

        // para cada nodo con sus ejes
        for (V from : getVertices()) {

            // y para cada par
            for (V to : getVertices()) {

                boolean flagIsFirst = true;
                E acum = null;
             
                // recorro el vector from->to
                for (InternalEdge i : adjacencyList.get(from)) {
                    
                    // aplico operación en orden de llegada de valores
                    
                    if(i.target == to) {

                        // si el primero sería el acumulador
                        if (flagIsFirst)
                        {
                            flagIsFirst = false;
                            acum = i.edge;
                        }
                        else
                        {
                            acum = summarizer.apply(acum, i.edge);
                        }
                    }
                }

                if(!flagIsFirst) {
                    newGraph.addEdge(to, from, acum);
                }   
            }   
        }

        return newGraph;
    }

    // ejemplo 1
    public static void main(String[] args) { // lo pongo como main para testear y que compile
        MultiDirectedGraph<String, Integer> myGraph = new MultiDirectedGraph<>();

        myGraph.addEdge("A", "B", new Integer(1) );
        myGraph.addEdge("B", "C", new Integer(2) );
        myGraph.addEdge("B", "D", new Integer(3) );
        myGraph.addEdge("D", "C", new Integer(4) );
        myGraph.addEdge("D", "E", new Integer(5) );
        myGraph.addEdge("A", "E", new Integer(6) );
        myGraph.addEdge("E", "G", new Integer(7) );
        myGraph.addEdge("G", "F", new Integer(8) );
        myGraph.addEdge("F", "E", new Integer(9) );
        myGraph.addEdge("A", "B", new Integer(10) );
        myGraph.addEdge("B", "C", new Integer(11) );
        myGraph.addEdge("B", "C", new Integer(12) );
        myGraph.addEdge("E", "G", new Integer(13) );

        System.out.println("ORIGINAL GRAPH");
        myGraph.printAllVertexes();
        myGraph.printAllEdges();

        MultiDirectedGraph<String, Integer> transposedGraph =  myGraph.transposeSummarize( (n,m) -> n+m );

        System.out.println("TRANSPOSED SUMMARIZED GRAPH");
        transposedGraph.printAllVertexes();
        transposedGraph.printAllEdges();

    }


   // ejemplo 2
   public static void main2(String[] args) {
        MultiDirectedGraph<String, Integer> myGraph = new MultiDirectedGraph<>();

        myGraph.addEdge("B", "C", new Integer(11) );
        myGraph.addEdge("B", "C", new Integer(12) );
        myGraph.addEdge("C", "B", new Integer(2) );
        
        myGraph.addEdge("B", "D", new Integer(3) );
        myGraph.addEdge("D", "C", new Integer(4) );
        
        myGraph.addEdge("E", "G", new Integer(7) );
        
        myGraph.addEdge("G", "F", new Integer(8) );
        myGraph.addEdge("F", "G", new Integer(-3) );
        
        myGraph.addEdge("F", "E", new Integer(9) );
        
        myGraph.addEdge("E", "G", new Integer(13) );

        System.out.println("ORIGINAL GRAPH");
        myGraph.printAllVertexes();
        myGraph.printAllEdges();

        MultiDirectedGraph<String, Integer> transposedGraph =  myGraph.transposeSummarize( (n,m) -> n+m );

        System.out.println("TRANSPOSED SUMMARIZED GRAPH");
        transposedGraph.printAllVertexes();
        transposedGraph.printAllEdges();

    }
}

// mvn compile && mvn exec:java -Dexec.mainClass="slee.MultiDirectedGraph"