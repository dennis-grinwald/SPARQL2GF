package finalDesign;

import org.apache.jena.graph.Node;

public class MotifPattern {
	
	public String v;
	public String e;
	public String v2;
	
	//Transform Subject,Predicate,Object of SPARQL-query to Vertex v, Edge e, Vertex v2 for GraphFrames MotifPattern
	public MotifPattern(Node s, Node p, Node o) {
		if (!s.isConcrete()) {
			this.v = s.getName();
		}else {
			this.v=s.toString();
			v=v.substring(1, v.length()-1);	
		}
		if (!p.isConcrete()) {
			this.e = p.getName();
		}else {
			this.e=p.toString();
			e=e.substring(1, e.length()-1);	
		}
		if (!o.isConcrete()) {
			this.v2 = o.getName();
		}else {
			this.v2=o.toString();
			v2=v2.substring(1, v2.length()-1);
		}
	}
	

	//Creates MotifPattern in GraphFrame Syntax, e.g. "(v)-[e]->(v2)" 	
	public String createPattern() {
		String pattern = "(" + v + ")"+"-"+"[" + e + "]"+"->"+"(" + v2 + ")";
		return pattern;
	}

}

