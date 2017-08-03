package finalDesign;

import org.apache.jena.graph.Node;

public class MotifPattern {
	
	public String v;
	public String e;
	public String v2;
	
	public MotifPattern(Node s, Node p, Node o) {
		this.v = s.toString();
		this.e = p.toString();
		this.v2 = o.toString();
	}
	

	public String createPattern() {
		String pattern = "(" + v + ")"+"-"+"(" + e + ")"+"->"+"(" + v2 + ")";
		return pattern;
	}

}

