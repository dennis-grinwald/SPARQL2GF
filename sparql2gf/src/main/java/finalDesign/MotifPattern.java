package finalDesign;

import org.apache.jena.graph.Node;

public class MotifPattern {
	
	public String v;
	public String e;
	public String v2;
	
	public MotifPattern(Node s, Node p, Node o) {
		this.v = s.getName();
		this.e = p.getName();
		this.v2 = o.getName();
	}
	

	public String createPattern() {
		String pattern = "(" + v + ")"+"-"+"[" + e + "]"+"->"+"(" + v2 + ")";
		return pattern;
	}

}

