package finalDesign;

import org.apache.jena.graph.Node;

public class MotifFilter {
	
	public static String v;
	public String e;
	public String v2;
	
	public MotifFilter(Node s, Node p, Node o) {
		MotifFilter.v=s.getName();
		this.e=p.getName();
		this.v2=o.getName();
	}
	
	public MotifFilter(Node concreteNode) {
		
	}
	
	public MotifFilter(Node concretePredicate , Node concreteObject) {
		
	}

	public void createMotif() {
		// TODO Auto-generated method stub
		
	}

	public static String createMotifFilter(Node subject) {
		
		return v+".name="+subject.toString();
		
	}
	
}
