package finalDesign;

import java.util.List;

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

	public static String organizePatternList(List<String> patternList) {
		
		String motif = new String();
		for (String pattern : patternList) {
			motif=motif.concat(pattern+";");
		}
		motif=motif.substring(0, motif.length()-1);
		return motif;
	}

			

}
	

