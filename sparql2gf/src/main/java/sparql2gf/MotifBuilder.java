package sparql2gf;

import java.util.ArrayList;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

public class MotifBuilder {
	
	static String concreteSubject = new String();
	static String concretePredicate = new String();
	static String concreteObject = new String();
	
	static ArrayList<String> concreteNodes= new ArrayList<String>();

	
	
	public static String transform(final Triple triple) {
		
		
		//Abstract triple elements
		final Node subject = triple.getSubject();
		final Node predicate = triple.getPredicate();
		final Node object = triple.getObject();
		
		// IMPROVE! Implement triple(s,p,o) to : " (s)-[p]->(o) "
		String motifPattern = "("+subject.toString()+")"+"-"+"("+predicate.toString()+")"+"->"+"("+object.toString()+")";
		
		//Subject filter algorithm
		if (subject.isConcrete()==true) {
			concreteSubject=subject.toString();
			concreteNodes.add(concreteSubject);
		}
		
		//Predicate filter algorithm
		if (predicate.isConcrete()==true) {
			concreteSubject=predicate.toString();
			concreteNodes.add(concretePredicate);
		} 
		
		//Object filter algorithm
		if (predicate.isConcrete()==true) {
					concreteObject=object.toString();
					concreteNodes.add(concreteObject);
		} 
		
		if (concreteNodes != null) {
			return SparqlToGfTranslator.addMotifs(concreteNodes, motifPattern);
		}else {
			return SparqlToGfTranslator.addPattern(motifPattern);
		}
		
		
		

	}
		
}
