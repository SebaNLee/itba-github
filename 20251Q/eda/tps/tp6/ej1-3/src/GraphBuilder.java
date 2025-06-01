
public class GraphBuilder<V,E> {
	private GraphService.Multiplicity multiplicity= GraphService.Multiplicity.SIMPLE;
	private GraphService.EdgeMode edgeMode= GraphService.EdgeMode.DIRECTED;
	private GraphService.SelfLoop acceptSelfLoops= GraphService.SelfLoop.NO;
	private GraphService.Weight hasWeight= GraphService.Weight.NO;
	private GraphService.Storage implementation= GraphService.Storage.SPARSE;
	
	public GraphBuilder<V,E> withMultiplicity(GraphService.Multiplicity param) {
		this.multiplicity= param;
		return this;
	}
	
	public GraphBuilder<V,E> withDirected(GraphService.EdgeMode param) {
		this.edgeMode= param;
		return this;
	}
	
	public GraphBuilder<V,E> withAcceptSelfLoop(GraphService.SelfLoop param) {
		this.acceptSelfLoops= param;
		return this;
	}
	
	public GraphBuilder<V,E> withAcceptWeight(GraphService.Weight param) {
		this.hasWeight= param;
		return this;
	}
	
	public GraphBuilder<V,E> withStorage(GraphService.Storage param) {
		this.implementation= param;
		return this;
	}
	
	public GraphService<V,E> build() {
	  return GraphFactory.create(multiplicity, edgeMode, acceptSelfLoops, hasWeight, implementation);
  }

}
