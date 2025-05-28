
public class Tester {
    public static void main(String[] args) {

        GraphService<Character, EmptyEdgeProp> g1 = new GraphBuilder()
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
        g2.addEdge('B', 'X', new EmptyEdgeProp());
        g2.addEdge('H', 'G', new EmptyEdgeProp());
        g2.addEdge('A', 'B', new EmptyEdgeProp());
        g2.addEdge('C', 'A', new EmptyEdgeProp());
        g2.addEdge('C', 'H', new EmptyEdgeProp());
        g2.addEdge('E', 'F', new EmptyEdgeProp());

        g2.dump();
        System.out.println();
        System.out.println(g2.degree('A'));

        g2.removeEdge('A', 'B');
        System.out.println(g2.degree('A'));
    }
}
