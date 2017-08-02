package sparql2gf;

import java.util.ArrayList;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

public class MotifBuilder {
	
	static String concreteSubject = new String();
	static String concretePredicate = new String();
	static String concreteObject = new String();
	
	//Concrete Nodes List
	static ArrayList<String> concreteNodes= new ArrayList<String>();
	
	//Initialize List with motif for the current triple
	public static ArrayList<String> tempMotif = new ArrayList<String>();

	public static ArrayList<String> transform(final Triple triple) {
		
		
		//Abstract triple elements
		final Node subject = triple.getSubject();
		final Node predicate = triple.getPredicate();
		final Node object = triple.getObject();
		
		// IMPROVE! Integrate triple(s,p,o) into form : " (s)-[p]->(o) "
		String motifPattern = "("+subject.getName()+")"+"-"+"("+predicate.getName()+")"+"->"+"("+object.getName()+")";
		
		//Subject filter algorithm
/*		if (subject.isConcrete()==true) {
			concreteSubject=subject.toString();
			concreteNodes.add(concreteSubject);
		}
		//Predicate filter algorithm
		if (predicate.isConcrete()==true) {
			concretePredicate=predicate.toString();
			concreteNodes.add(concretePredicate);
		} 
		//Object filter algorithm
		if (predicate.isConcrete()==true) {
			concreteObject=object.toString();
			concreteNodes.add(concreteObject);
		} */
		
	    //Return motif, arguments
		if (concreteNodes == null) {
		   tempMotif.add(motifPattern);
		   return tempMotif;
		} /* else {
			MotifBuilder.createConcreteMotif(motifPattern, concreteNodes);
		} */
		return tempMotif;
	}
	
	/*public static ArrayList<String> createConcreteMotif(String motifPattern, ArrayList concreteNodes) {
		tempMotif.add(motifPattern);   
		
		
		
		return tempMotif;
		
	}  */

}

