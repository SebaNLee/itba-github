

abstract public class GraphFactory<V, E> {

	public static <V, E> GraphService<V, E> create(GraphService.Multiplicity edgeMultiplicity, GraphService.EdgeMode theEdgeMode, 
			GraphService.SelfLoop acceptSelfLoops, GraphService.Weight hasWeight, GraphService.Storage theStorage) {
		
		if (theStorage== GraphService.Storage.SPARSE)  // manejando 8 tipos con 2 clases concretas
			if (edgeMultiplicity== GraphService.Multiplicity.SIMPLE)
				return new SimpleOrDefault<V,E>(theEdgeMode==GraphService.EdgeMode.DIRECTED, 
												acceptSelfLoops==GraphService.SelfLoop.YES, 
											    hasWeight==GraphService.Weight.YES );
			else
				return new Multi<V,E>(theEdgeMode==GraphService.EdgeMode.DIRECTED, 
											acceptSelfLoops==GraphService.SelfLoop.YES, 
											hasWeight==GraphService.Weight.YES );
		
		// todavia no lo hemos implementado en forma Densa Matriz
		//	return new AdjacencymatrixGraph<V,E>(isSimple, isDirected, isWeighted );
		
		
		throw new RuntimeException("Not yet implemented");
	}
	
	private GraphFactory() {
	}
}
