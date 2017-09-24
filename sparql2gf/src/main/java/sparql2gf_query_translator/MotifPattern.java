package sparql2gf_query_translator;

import org.apache.jena.graph.Node;

public class MotifPattern {
	
	public String v;
	public String e;
	public String v2;
	
	//Transform Subject,Predicate,Object of SPARQL-query to Vertex v, Edge e, Vertex v2 for GraphFrames MotifPattern
	public MotifPattern(Node s, Node p, Node o) {
		
		if (!s.isConcrete()) {
			this.v = s.getName();
		}else if (s.isURI()) {
			PrefixBuilder subjectPrefix = new PrefixBuilder(s.getURI());
			v= subjectPrefix.getUriValue();
		}else {
			this.v=s.toString();
			v=v.substring(1, v.length()-1);	
		}
		
		if (!p.isConcrete()) {
			this.e = p.getName();
		}else if (p.isURI()) {
			PrefixBuilder predicatePrefix = new PrefixBuilder(p.getURI());
			e= predicatePrefix.getUriValue();
		}else {
			this.e=p.toString();
			e=e.substring(1, e.length()-1);	
		}
		
		if (!o.isConcrete()) {
			this.v2 = o.getName();
		}else if (o.isURI()) {
			PrefixBuilder objectPrefix = new PrefixBuilder(o.getURI());
			v2= objectPrefix.getUriValue();
		}else if (o.isLiteral()) {
			this.v2 = o.getLiteral().toString();
			
		}
		
		//code for object node with special symbol
		/*else if (o.isLiteral()) {
			this.v2= "object"; 
		} */
		
	}
	

	//Creates MotifPattern in GraphFrame Syntax, e.g. "(v)-[e]->(v2)" 	
	public String createPattern() {
		String pattern = "(" + v + ")"+"-"+"[" + e + "]"+"->"+"(" + v2 + ")";
		return pattern;
	}

}

