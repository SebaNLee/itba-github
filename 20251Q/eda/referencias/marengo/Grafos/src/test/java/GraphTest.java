import core.DijkstraPath;
import core.GraphFactory;
import core_service.GraphService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestTemplate;

public class GraphTest {
    private GraphService<Character, EmptyEdgeProp> undirectedSimpleLoopNoWeight;
    private GraphService<Character, EmptyEdgeProp> undirectedMultipleLoopNoWeight;
    private GraphService<Character, EmptyEdgeProp> directedMultipleLoopNoWeight;
    private GraphService<Character, WeightedEdge> directedMultipleLoopWeight;
    private GraphService<Character, WeightedEdge> directedSimpleLoopWeight;
    private GraphService<Character, WeightedEdge> undirectedMultipleLoopWeight;

    private GraphService<String, WeightedEdge> rutas;

    @BeforeEach
    void setup() {
        undirectedSimpleLoopNoWeight = new GraphBuilder<Character, EmptyEdgeProp>()
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptSelfLoop(GraphService.SelfLoop.YES)
                .withAcceptWeight(GraphService.Weight.NO)
                .withDirected(GraphService.EdgeMode.UNDIRECTED)
                .withMultiplicity(GraphService.Multiplicity.SIMPLE)
                .build();

        directedMultipleLoopNoWeight = new GraphBuilder<Character, EmptyEdgeProp>()
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptSelfLoop(GraphService.SelfLoop.YES)
                .withAcceptWeight(GraphService.Weight.NO)
                .withDirected(GraphService.EdgeMode.DIRECTED)
                .withMultiplicity(GraphService.Multiplicity.MULTIPLE)
                .build();

        directedMultipleLoopWeight = new GraphBuilder<Character, WeightedEdge>()
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptSelfLoop(GraphService.SelfLoop.YES)
                .withAcceptWeight(GraphService.Weight.YES)
                .withDirected(GraphService.EdgeMode.DIRECTED)
                .withMultiplicity(GraphService.Multiplicity.MULTIPLE)
                .build();

        undirectedMultipleLoopNoWeight = new GraphBuilder<Character, EmptyEdgeProp>()
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptSelfLoop(GraphService.SelfLoop.YES)
                .withAcceptWeight(GraphService.Weight.NO)
                .withDirected(GraphService.EdgeMode.UNDIRECTED)
                .withMultiplicity(GraphService.Multiplicity.MULTIPLE)
                .build();

        directedSimpleLoopWeight = new GraphBuilder<Character, WeightedEdge>()
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptSelfLoop(GraphService.SelfLoop.YES)
                .withAcceptWeight(GraphService.Weight.YES)
                .withDirected(GraphService.EdgeMode.DIRECTED)
                .withMultiplicity(GraphService.Multiplicity.SIMPLE)
                .build();

        undirectedMultipleLoopWeight = new GraphBuilder<Character, WeightedEdge>()
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptSelfLoop(GraphService.SelfLoop.YES)
                .withAcceptWeight(GraphService.Weight.YES)
                .withDirected(GraphService.EdgeMode.UNDIRECTED)
                .withMultiplicity(GraphService.Multiplicity.MULTIPLE)
                .build();

        rutas = new GraphBuilder<String, WeightedEdge>()
                .withAcceptSelfLoop(GraphService.SelfLoop.NO)
                .withStorage(GraphService.Storage.SPARSE)
                .withAcceptWeight(GraphService.Weight.YES)
                .withDirected(GraphService.EdgeMode.DIRECTED)
                .withMultiplicity(GraphService.Multiplicity.SIMPLE)
                .build();
    }

    @Test
    void testNumberEdgesSimple() {
        Assertions.assertEquals(0, undirectedSimpleLoopNoWeight.numberOfEdges());
        undirectedSimpleLoopNoWeight.addEdge('E', 'B', new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('A', 'B', new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('F', 'B', new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addVertex('D');
        undirectedSimpleLoopNoWeight.addVertex('G');
        undirectedSimpleLoopNoWeight.addEdge('E', 'F', new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('F', 'A', new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('F', 'G', new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('U', 'G', new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('T', 'U', new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('C', 'G', new EmptyEdgeProp());
        Assertions.assertThrows(IllegalArgumentException.class, () -> undirectedSimpleLoopNoWeight.addEdge('C', 'G',
                new EmptyEdgeProp()));
        Assertions.assertEquals(9, undirectedSimpleLoopNoWeight.numberOfEdges());
    }

    @Test
    void testNumberEdgesMultiple() {
        Assertions.assertEquals(0, directedMultipleLoopNoWeight.numberOfEdges());
        directedMultipleLoopNoWeight.addEdge('E', 'B', new EmptyEdgeProp());
        directedMultipleLoopNoWeight.addEdge('A', 'B', new EmptyEdgeProp());
        directedMultipleLoopNoWeight.addEdge('F', 'B', new EmptyEdgeProp());
        directedMultipleLoopNoWeight.addVertex('D');
        directedMultipleLoopNoWeight.addVertex('G');
        directedMultipleLoopNoWeight.addEdge('E', 'F', new EmptyEdgeProp());
        directedMultipleLoopNoWeight.addEdge('F', 'A', new EmptyEdgeProp());
        directedMultipleLoopNoWeight.addEdge('F', 'G', new EmptyEdgeProp());
        directedMultipleLoopNoWeight.addEdge('U', 'G', new EmptyEdgeProp());
        directedMultipleLoopNoWeight.addEdge('T', 'U', new EmptyEdgeProp());
        directedMultipleLoopNoWeight.addEdge('C', 'G', new EmptyEdgeProp());
        directedMultipleLoopNoWeight.addEdge('F', 'F', new EmptyEdgeProp());
        Assertions.assertEquals(10, directedMultipleLoopNoWeight.numberOfEdges());
    }

    @Test
    void testNumberEdgesMultiple2() {
        directedMultipleLoopWeight.addEdge('E', 'B', new WeightedEdge(3));
        directedMultipleLoopWeight.addEdge('A', 'B', new WeightedEdge(1));
        directedMultipleLoopWeight.addEdge('F', 'B', new WeightedEdge(2));
        directedMultipleLoopWeight.addVertex('D');
        directedMultipleLoopWeight.addVertex('G');
        directedMultipleLoopWeight.addEdge('E', 'F', new WeightedEdge(-2));
        directedMultipleLoopWeight.addEdge('F', 'A', new WeightedEdge(8));
        directedMultipleLoopWeight.addEdge('F', 'G', new WeightedEdge(2));
        directedMultipleLoopWeight.addEdge('U', 'G', new WeightedEdge(-10));
        directedMultipleLoopWeight.addEdge('T', 'U', new WeightedEdge(8));
        directedMultipleLoopWeight.addEdge('C', 'G', new WeightedEdge(1));
        directedMultipleLoopWeight.addEdge('G', 'U', new WeightedEdge(0));
        directedMultipleLoopWeight.addEdge('F', 'F', new WeightedEdge(3));
        directedMultipleLoopWeight.addEdge('F', 'F', new WeightedEdge(2));

        Assertions.assertEquals(12, directedMultipleLoopWeight.numberOfEdges());
    }

    @Test
    void testDegreeDirected() {
        directedMultipleLoopWeight.addVertex('D');
        directedMultipleLoopWeight.addVertex('G');
        directedMultipleLoopWeight.addEdge('G', 'F', new WeightedEdge(2));
        directedMultipleLoopWeight.addEdge('U', 'G', new WeightedEdge(-10));
        directedMultipleLoopWeight.addEdge('U', 'G', new WeightedEdge(0));
        directedMultipleLoopWeight.addEdge('F', 'F', new WeightedEdge(3));
        directedMultipleLoopWeight.addEdge('F', 'F', new WeightedEdge(2));

        Assertions.assertEquals(2, directedMultipleLoopWeight.inDegree('G'));
        Assertions.assertEquals(1, directedMultipleLoopWeight.outDegree('G'));

        Assertions.assertEquals(3, directedMultipleLoopWeight.inDegree('F'));
        Assertions.assertEquals(2, directedMultipleLoopWeight.outDegree('F'));
    }

    @Test
    void testDegree() {
        undirectedMultipleLoopNoWeight.addEdge('E', 'B', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('A', 'B', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('F', 'B', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addVertex('D');
        undirectedMultipleLoopNoWeight.addVertex('G');
        undirectedMultipleLoopNoWeight.addEdge('E', 'F', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('F', 'A', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('F', 'G', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('U', 'G', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('T', 'U', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('C', 'G', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('G', 'U', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('F', 'F', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('F', 'F', new EmptyEdgeProp());

        Assertions.assertEquals(4, undirectedMultipleLoopNoWeight.degree('G'));
        Assertions.assertEquals(8, undirectedMultipleLoopNoWeight.degree('F'));
    }

    @Test
    void testRemoveVertex() {
        undirectedMultipleLoopNoWeight.addEdge('E', 'B', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('A', 'B', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('F', 'B', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addVertex('D');
        undirectedMultipleLoopNoWeight.addVertex('G');
        undirectedMultipleLoopNoWeight.addEdge('E', 'F', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('F', 'A', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('F', 'G', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('U', 'G', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('T', 'U', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('C', 'G', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('G', 'U', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('F', 'F', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('F', 'F', new EmptyEdgeProp());

        Assertions.assertEquals(12, undirectedMultipleLoopNoWeight.numberOfEdges());
        Assertions.assertTrue(undirectedMultipleLoopNoWeight.removeVertex('D'));
        Assertions.assertEquals(12, undirectedMultipleLoopNoWeight.numberOfEdges());
        Assertions.assertTrue(undirectedMultipleLoopNoWeight.removeVertex('F'));
        Assertions.assertEquals(6,undirectedMultipleLoopNoWeight.numberOfEdges());

        directedMultipleLoopWeight.addEdge('E', 'B', new WeightedEdge(3));
        directedMultipleLoopWeight.addEdge('A', 'B', new WeightedEdge(1));
        directedMultipleLoopWeight.addEdge('F', 'B', new WeightedEdge(2));
        directedMultipleLoopWeight.addVertex('D');
        directedMultipleLoopWeight.addVertex('G');
        directedMultipleLoopWeight.addEdge('E', 'F', new WeightedEdge(-2));
        directedMultipleLoopWeight.addEdge('F', 'A', new WeightedEdge(8));
        directedMultipleLoopWeight.addEdge('F', 'G', new WeightedEdge(2));
        directedMultipleLoopWeight.addEdge('U', 'G', new WeightedEdge(-10));
        directedMultipleLoopWeight.addEdge('T', 'U', new WeightedEdge(8));
        directedMultipleLoopWeight.addEdge('C', 'G', new WeightedEdge(1));
        directedMultipleLoopWeight.addEdge('G', 'U', new WeightedEdge(0));
        directedMultipleLoopWeight.addEdge('F', 'F', new WeightedEdge(3));
        directedMultipleLoopWeight.addEdge('F', 'F', new WeightedEdge(2));

        Assertions.assertEquals(12, directedMultipleLoopWeight.numberOfEdges());
        Assertions.assertTrue(directedMultipleLoopWeight.removeVertex('D'));
        Assertions.assertEquals(12, directedMultipleLoopWeight.numberOfEdges());
        Assertions.assertTrue(directedMultipleLoopWeight.removeVertex('F'));
        Assertions.assertEquals(6,directedMultipleLoopWeight.numberOfEdges());
    }

    @Test
    void testDijkstra() {
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
    }

    @Test
    void testPrintAllPaths() {
        undirectedSimpleLoopNoWeight.addEdge('A','C',new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('C','H',new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('H','E',new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('H','F',new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('H','G',new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('A','B',new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('B','X',new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('A','J',new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('J','E',new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.addEdge('E','F',new EmptyEdgeProp());
        undirectedSimpleLoopNoWeight.printAllPaths('A','F');
    }

    @Test
    void testBipartite(){
        GraphService<Character, EmptyEdgeProp> g = new GraphBuilder<Character,EmptyEdgeProp>().
                withMultiplicity(GraphService.Multiplicity.SIMPLE).
                withDirected(GraphService.EdgeMode.UNDIRECTED).
                withAcceptSelfLoop(GraphService.SelfLoop.NO).
                withAcceptWeight(GraphService.Weight.NO).
                withStorage(GraphService.Storage.SPARSE).build();

        g.addEdge('E', 'F', new EmptyEdgeProp());
        g.addEdge('C', 'F', new EmptyEdgeProp());
        g.addEdge('C', 'H', new EmptyEdgeProp());
        g.addEdge('G', 'H', new EmptyEdgeProp());
        g.addEdge('A', 'H', new EmptyEdgeProp());
        g.addEdge('A', 'B', new EmptyEdgeProp());
        g.addEdge('X', 'B', new EmptyEdgeProp());
        g.addVertex('M');
        g.addEdge('D', 'C', new EmptyEdgeProp());

        System.out.println( "isBipartite: " + g.isBipartite());

    }

    @Test
    void testBipartite2() {
        GraphService<Character, EmptyEdgeProp> g = new GraphBuilder<Character,EmptyEdgeProp>().
                withMultiplicity(GraphService.Multiplicity.SIMPLE).
                withDirected(GraphService.EdgeMode.UNDIRECTED).
                withAcceptSelfLoop(GraphService.SelfLoop.NO).
                withAcceptWeight(GraphService.Weight.NO).
                withStorage(GraphService.Storage.SPARSE).build();

        g.addEdge('A', 'B', new EmptyEdgeProp());
        g.addEdge('B', 'C', new EmptyEdgeProp());
        g.addEdge('C', 'D', new EmptyEdgeProp());
        g.addEdge('D', 'E', new EmptyEdgeProp());
        g.addEdge('E', 'F', new EmptyEdgeProp());
        g.addEdge('F', 'A', new EmptyEdgeProp());
        g.addEdge('X', 'Y', new EmptyEdgeProp());
        g.addEdge('X', 'Z', new EmptyEdgeProp());
        g.addVertex('M');
        g.addVertex('N');
        g.addVertex('L');

        System.out.println( "isBipartite: " + g.isBipartite() );
    }

    @Test
    void testBipartite3() {
        GraphService<Character, EmptyEdgeProp> g = new GraphBuilder<Character,EmptyEdgeProp>().
                withMultiplicity(GraphService.Multiplicity.SIMPLE).
                withDirected(GraphService.EdgeMode.UNDIRECTED).
                withAcceptSelfLoop(GraphService.SelfLoop.NO).
                withAcceptWeight(GraphService.Weight.NO).
                withStorage(GraphService.Storage.SPARSE).build();

        g.addEdge('A', 'B', new EmptyEdgeProp());
        g.addEdge('B', 'C', new EmptyEdgeProp());
        g.addEdge('C', 'D', new EmptyEdgeProp());
        g.addEdge('D', 'E', new EmptyEdgeProp());
        g.addEdge('E', 'A', new EmptyEdgeProp());

        System.out.println( "isBipartite: " + g.isBipartite() );
    }

    @Test
    void testCycleDirected() {

        directedMultipleLoopWeight.addEdge('A','B', new WeightedEdge(7));
        directedMultipleLoopWeight.addEdge('B','C', new WeightedEdge(7));
        directedMultipleLoopWeight.addEdge('C','D', new WeightedEdge(7));
        directedMultipleLoopWeight.addEdge('D','E', new WeightedEdge(7));
        directedMultipleLoopWeight.addEdge('E','E', new WeightedEdge(7));


        Assertions.assertTrue(directedMultipleLoopWeight.hasCycle());
    }

    @Test
    void testCycleUndirected() {
        undirectedMultipleLoopNoWeight.addEdge('A', 'B', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('B', 'C', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('C', 'D', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('D', 'E', new EmptyEdgeProp());
        undirectedMultipleLoopNoWeight.addEdge('E', 'E', new EmptyEdgeProp());

        Assertions.assertTrue(undirectedMultipleLoopNoWeight.hasCycle());
    }

    @Test
    void testRutasAereas() {
        rutas.addEdge("HNL", "LAX", new WeightedEdge(2555));
        rutas.addEdge("LAX", "HNL", new WeightedEdge(2555));
        rutas.addEdge("LAX", "SFO", new WeightedEdge(337));
        rutas.addEdge("SFO", "LAX", new WeightedEdge(100));
        rutas.addEdge("DFW", "LAX", new WeightedEdge(1233));
        rutas.addEdge("LAX", "ORD", new WeightedEdge(1743));
        rutas.addEdge("SFO", "ORD", new WeightedEdge(1843));
        rutas.addEdge("ORD", "SFO", new WeightedEdge(100));
        rutas.addEdge("ORD", "DFW", new WeightedEdge(802));
        rutas.addEdge("DFW", "LGA", new WeightedEdge(1387));
        rutas.addEdge("MIA", "DFW", new WeightedEdge(1120));
        rutas.addEdge("MIA", "LGA", new WeightedEdge(1099));
        rutas.addEdge("ORD", "PVD", new WeightedEdge(849));
        rutas.addEdge("MIA", "PVD", new WeightedEdge(1205));
        rutas.addEdge("PVD", "MIA", new WeightedEdge(1205));
        rutas.addEdge("LGA", "PVD", new WeightedEdge(142));
        rutas.addEdge("PVD", "LGA", new WeightedEdge(999999));


        System.out.println(rutas.dijsktra("PVD"));
    }

    @Test
    void testKruskal() {
        undirectedMultipleLoopWeight.addEdge('A','C', new WeightedEdge(6));
        undirectedMultipleLoopWeight.addEdge('A','B', new WeightedEdge(7));
        undirectedMultipleLoopWeight.addEdge('A','X', new WeightedEdge(10));
        undirectedMultipleLoopWeight.addEdge('C','X', new WeightedEdge(3));
        undirectedMultipleLoopWeight.addEdge('C','D', new WeightedEdge(5));
        undirectedMultipleLoopWeight.addEdge('C','B', new WeightedEdge(1));
        undirectedMultipleLoopWeight.addEdge('B','D', new WeightedEdge(3));
        undirectedMultipleLoopWeight.addVertex('Y');
        undirectedMultipleLoopWeight.addEdge('Y','Y', new WeightedEdge(3));

        undirectedMultipleLoopWeight.kruskal();
    }
}
