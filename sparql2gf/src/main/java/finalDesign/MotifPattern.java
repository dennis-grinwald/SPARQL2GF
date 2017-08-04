package finalDesign;

import org.apache.jena.graph.Node;

public class MotifPattern {
	
	public String v;
	public String e;
	public String v2;
	
	//Transform Subject,Predicate,Object of SPARQL-query to Vertex v, Edge e, Vertex v2 for GraphFrames MotifPattern
	public MotifPattern(Node s, Node p, Node o) {
		this.v = s.getName();
		this.e = p.getName();
		this.v2 = o.getName();
	}
	

	//Creates MotifPattern in GraphFrame Syntax, e.g. "(v)-[e]->(v2)" 	
	public String createPattern() {
		String pattern = "(" + v + ")"+"-"+"[" + e + "]"+"->"+"(" + v2 + ")";
		return pattern;
	}

}

