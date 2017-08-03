package testpackage;

import org.apache.jena.graph.Triple;

public class Element {
	
	//ALWAYS create elementPattern
	public static String createMotif(Triple triple) {
		
		String subject = triple.getSubject().toString();
		String predicate = triple.getPredicate().toString();
		String object = triple.getObject().toString();
		
		String elementPattern = "(" + subject + ")"+"-"+"(" + predicate + ")"+"->"+"(" + object + ")";
		return elementPattern;
	}
	
	//If subject concrete
	
	
	

}
