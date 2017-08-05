package finalDesign;

import org.apache.jena.graph.Node;

public class MotifFilter {
	
	public Node s;
	public Node p;
	public Node o;
	
	public String v;
	public String e;
	public String v2;
	
	public String subjectFilter;
	public String predicateFilter;
	public String objectFilter;
	
	
	
	public MotifFilter(Node s, Node p, Node o) {
		this.s = s;
		this.p = p;
		this.o = o;
		
		if (!s.isConcrete()) {
			this.v = s.getName();
		}else {this.v=null;}
		if (!p.isConcrete()) {
			this.e = p.getName();
		}else {this.e=null;}
		if (!o.isConcrete()) {
			this.v2 = o.getName();
		}else {this.v=null;}
	}
	
	public MotifFilter() {}
	
	public void createMotifFilter() {
		if (s!=null) {
			String subjectFilter= v+"."+"name="+s;
			SparqlToGfTranslator.addMotifFilter(subjectFilter);
		}
		/**if (p!=null) {
			String predicateFilter= e+"."+"name="+p;
		}
		if (o!=null && o.isLiteral()) {
			String objectFilter = e+"."+p+"="+o;
		}**/
		
	}
	
	
	
	
	

	
}
