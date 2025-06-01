// import GraphService.EdgeMode;
// import GraphService.Multiplicity;
// import GraphService.SelfLoop;
// import GraphService.Storage;
// import GraphService.Weight;

// import GraphService.*;
// import GraphBuilder.*;


public class Tester {
    public static void main(String[] args) {

        GraphService<Character, EmptyEdgeProp> g1 = new GraphBuilder<Character, EmptyEdgeProp>()
            .withAcceptSelfLoop(GraphService.SelfLoop.YES)
            .withAcceptWeight(GraphService.Weight.NO)
            .withDirected(GraphService.EdgeMode.UNDIRECTED)
            .withStorage(GraphService.Storage.SPARSE)
            .withMultiplicity(GraphService.Multiplicity.SIMPLE)
            .build();

        g1.addEdge('E', 'B', new EmptyEdgeProp());
        g1.addEdge('A', 'B', new EmptyEdgeProp());
        g1.addEdge('F', 'B', new EmptyEdgeProp());
        g1.addVertex('D');
        g1.addVertex('G');
        g1.addEdge('E', 'F', new EmptyEdgeProp());
        g1.addEdge('F', 'A', new EmptyEdgeProp());
        g1.addEdge('F', 'G', new EmptyEdgeProp());
        g1.addEdge('U', 'G', new EmptyEdgeProp());
        g1.addEdge('T', 'U', new EmptyEdgeProp());
        g1.addEdge('C', 'G', new EmptyEdgeProp());

        System.out.println(g1.numberOfEdges()); // 9

        GraphService<Character, EmptyEdgeProp> g2 = GraphFactory.create(
            GraphService.Multiplicity.SIMPLE,
            GraphService.EdgeMode.UNDIRECTED,
            GraphService.SelfLoop.NO,
            GraphService.Weight.NO,
            GraphService.Storage.SPARSE);

        g2.addEdge('D', 'C', new EmptyEdgeProp());
        g2.addEdge('A', 'B', new EmptyEdgeProp());
        g2.addEdge('C', 'A', new EmptyEdgeProp());
        g2.addEdge('B', 'D', new EmptyEdgeProp());
        g2.addEdge('B', 'F', new EmptyEdgeProp());
        g2.addEdge('C', 'E', new EmptyEdgeProp());
        g2.addEdge('C', 'F', new EmptyEdgeProp());


        System.out.println("===============");
        g2.dump();
        
        System.out.println(g2.degree('A'));

        g2.removeEdge('A', 'B');
        System.out.println(g2.degree('A'));
        
        
        System.out.println("===============");
        System.out.println("BFS");
        g2.printBFS('A');

        System.out.println("===============");
        System.out.println("DFS");
        g2.printDFS('A');

        System.out.println("===============");
        System.out.println("Ahora con Iterables:");

        System.out.println();
        System.out.println("BFS");
        Iterable<Character> iteratorBFS = g2.getBFS('A');
        for (Character character : iteratorBFS) {
            System.out.println(character);
        }
        

        System.out.println();
        System.out.println("DFS");
        Iterable<Character> iteratorDFS = g2.getDFS('A');
        for (Character character : iteratorDFS) {
            System.out.println(character);
        }



        System.out.println("=================");
        System.out.println("Dijkstra");
        System.out.println();

        // GraphService<Character, WeightedEdge> g = GraphFactory.create(GraphService.Multiplicity.SIMPLE, GraphService.EdgeMode.DIRECTED, GraphService.SelfLoop.NO, GraphService.Weight.YES, GraphService.Storage.SPARSE);
        GraphService<Character,WeightedEdge> g = new GraphBuilder<Character, WeightedEdge>()
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptSelfLoop(GraphService.SelfLoop.NO)
                .withAcceptWeight(GraphService.Weight.YES)
                .withDirected(GraphService.EdgeMode.DIRECTED)
                .withMultiplicity(GraphService.Multiplicity.SIMPLE)
                .build();


        g.addEdge('A', 'B', new WeightedEdge(10));
        g.addEdge('A', 'C', new WeightedEdge(3));
        g.addEdge('B', 'C', new WeightedEdge(1));
        g.addEdge('B', 'D', new WeightedEdge(2));
        g.addEdge('C', 'A', new WeightedEdge(1));
        g.addEdge('C', 'B', new WeightedEdge(4));
        g.addEdge('C', 'D', new WeightedEdge(8));
        g.addEdge('C', 'E', new WeightedEdge(2));
        g.addEdge('D', 'E', new WeightedEdge(7));
        g.addEdge('E', 'D', new WeightedEdge(9));
        g.addEdge('Z', 'K', new WeightedEdge(17));
        g.addEdge('K', 'A', new WeightedEdge(19));

        DijkstraPath<Character, WeightedEdge> pathRta = g.dijsktra('A');

        System.out.println(pathRta);

        System.out.println("Test");

        System.out.println();
        System.out.println("All paths");

        g.printAllPaths('A', 'C');


        System.out.println();
        System.out.println(g.isBipartite());
        
    }
}
