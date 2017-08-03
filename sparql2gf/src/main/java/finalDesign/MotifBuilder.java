package finalDesign;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

public class MotifBuilder {

	public Node subject;
	public Node predicate;
	public Node object;
	
	public void transform(Triple triple) {
		
		this.subject=triple.getSubject();
		this.predicate=triple.getPredicate();
		this.object=triple.getObject();
		
		MotifPattern pattern = new MotifPattern(subject,predicate,object);
		String motifPattern = pattern.createPattern();
		SparqlToGfTranslator.add(motifPattern);	
	}
	
}
