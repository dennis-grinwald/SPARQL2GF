package sparql2gf_query_translator;

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
	
	//these string variables are relevant ONLY for MotifFilterBuilding, therefore only here present!!!
	public String vs;
	public String ep;
	public String v2o;
	
	public String subjectFilter;
	public String predicateFilter;
	public String objectFilter;
	
	public List<String> filterList = new ArrayList<String>();
	
	
	//FIX URI/Prefix usage!!! --- In order to add new node types (e.g. literal,uri ...etc. appropriate mappings have to be added here !)
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
				vs=v;
			}else if (s.isURI()) {
				vs=s.getURI();
				PrefixBuilder subjectPrefix = new PrefixBuilder(s.getURI());
				v=subjectPrefix.getUriValue();
			}else if (s.isBlank()) {
				v = null;
			}
		}  
					
		if (!p.isVariable()) {
			if (p.isLiteral()) {
				e=p.getLiteral().toString();
				ep=e;
			}else if (p.isURI()) {
				ep=p.getURI();
				PrefixBuilder predicatePrefix = new PrefixBuilder(p.getURI());
				e= predicatePrefix.getUriValue();
			}else if (p.isBlank()) {
				e = null;
			}
		}
		if (!o.isVariable()) {
			if (o.isLiteral()) {
				v2=o.getLiteral().toString();
				v2o=v2;
			}else if (o.isURI()) {
				v2o=o.getURI();
				PrefixBuilder objectPrefix = new PrefixBuilder(o.getURI());
				v2=objectPrefix.getUriValue();
				
			}else if (o.isBlank()) {
				v2 = null;
			}
		}   
	}
	
	public MotifFilter() {}
	
	/**Should also be able to be able to apply operations such as : josh.age='23' **/
	//creates MotifFilter in GraphFrame syntax : e.g. "josh(as vertex).name = 'josh'" --- Fix  : double naming - Prefix Mapping!!!
	public List<String> createMotifFilter() {
		
		if (v!=null) {
			String subjectFilter = v + "." + "id=" +"'"+ vs+"'";
			filterList.add(subjectFilter);
		} 
		
		
		/*if (v!=null) {
			String subjectFilter = v + "." + "name=" +"'"+ vs+"'";
			filterList.add(subjectFilter);
		} */
		
		if (e!=null) {
			String predicateFilter = e + "." + "relationship=" +"'"+ ep +"'";
			filterList.add(predicateFilter);
		} 
		
		
		
		
		
		//loop for objects with special symbols(e.g. - , . ? etc.)
		/*if (v2!=null) {
			String objectFilter = "object" + "." + "id=" +"'"+ v2o+"'";
			filterList.add(objectFilter);
		}*/		
		
		
		if (v2!=null) {
			String objectFilter = v2 + "." + "id=" +"'"+ v2o+"'";
			filterList.add(objectFilter);
		} 
		return filterList;
		
	}

}
