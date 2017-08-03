package testpackage;

import org.apache.jena.graph.Triple;

public class WalkOperations {
	
	public WalkOperations() {}
	
	public static String transform(Triple triple) {
		
		String motif = Element.createMotif(triple);
		return motif	;	
	}
	
	

}
