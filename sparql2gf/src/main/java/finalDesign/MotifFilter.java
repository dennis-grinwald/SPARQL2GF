package finalDesign;

import java.util.ArrayList;
import java.util.List;

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
	
	public List<String> filterList = new ArrayList<String>();
	
	
	//FIX URI/Prefix usage!!!
	public MotifFilter(Node s, Node p, Node o) {
		this.s = s;
		this.p = p;
		this.o = o;
		
	/*	if (!s.isConcrete()) {
			this.v = s.getName();
		}else {
			this.v=s.toString();
			v=v.substring(1, v.length()-1);	
		} */
		
		if (!s.isVariable()) {
			if (s.isLiteral()) {
				v=s.getLiteral().toString();
			}else if (s.isURI()) {
				v=s.getURI();
			}else if (s.isBlank()) {
				v = null;
			}
		}  
					
		if (!p.isVariable()) {
			if (p.isLiteral()) {
				e=p.getLiteral().toString();
			}else if (p.isURI()) {
				PrefixBuilder prefix = new PrefixBuilder(p.getURI());
				//e= p.getURI();
			}else if (p.isBlank()) {
				e = null;
			}
		}
	/**	if (!o.isVariable()) {
			if (o.isLiteral()) {
				v2=o.getLiteral().toString();
			}else if (o.isURI()) {
				v2=o.getURI();
			}else if (o.isBlank()) {
				v2 = null;
			}
		}   **/
	}
	
	public MotifFilter() {}
	
	/**Should also be able to be able to apply operations such as : josh.age='23' **/
	//creates MotifFilter in GraphFrame syntax : e.g. "josh(as vertex).name = 'josh'" --- Fix  : double naming - Prefix Mapping!!!
	public List<String> createMotifFilter() {
		if (v!=null) {
			String subjectFilter = v + "." + "name=" +"'"+ v+"'";
			filterList.add(subjectFilter);
		} 
		
		if (e!=null) {
			String predicateFilter = e + "." + "relationship=" +"'"+ p.getURI() +"'";
			filterList.add(predicateFilter);
		} 
		
		
		if (v2!=null) {
			String objectFilter = v2 + "." + "name=" +"'"+ v2+"'";
			filterList.add(objectFilter);
		}
		return filterList;
		
	}

}
