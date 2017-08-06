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
		
		if (!s.isVariable()) {
			if (s.isLiteral()) {
				v=s.getLiteral().toString();
			}else if (s.isURI()) {
				v=s.getURI().toString();
			}else if (s.isBlank()) {
				v = null; }
		}
					
	/*	if (!p.isVariable()) {
			if (p.isLiteral()) {
				p.getLiteral();
			}else if (p.isURI()) {
				p.getURI();
			}else if (p.isBlank()) {
				p = null;
			}
		} */
	}
	
	public MotifFilter() {}
	
	public String createMotifFilter() {
		if (s!=null) {
			String subjectFilter= v+"."+"name="+"'"+s+"'";
			return subjectFilter;
		}
		return subjectFilter;
		/**if (p!=null) {
			String predicateFilter= e+"."+"name="+p;
		}
		if (o!=null && o.isLiteral()) {
			String objectFilter = e+"."+p+"="+o;
		}**/
		
	}
	
	
	
	
	

	
}
