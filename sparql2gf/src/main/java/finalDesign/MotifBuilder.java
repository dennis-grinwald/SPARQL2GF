package finalDesign;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.graph.Node;
import org.apache.jena.graph.Triple;

public class MotifBuilder {

	public Node subject;
	public Node predicate;
	public Node object;
	
	//transform BGP of SPARQL-query into a List of MotifPatterns and MotifFilter for concrete Nodes
	public void transform(Triple triple) {
		
		this.subject=triple.getSubject();
		this.predicate=triple.getPredicate();
		this.object=triple.getObject();
		
		//Abstract MotifPattern from SPARQL-BGP 
		MotifPattern pattern = new MotifPattern(subject,predicate,object);
		String motifPattern = pattern.createPattern();
		SparqlToGfTranslator.addMotifPattern(motifPattern);	
		
		//Abstract MotifFilter from concrete Nodes in SPARQL query
		if (subject.isConcrete() || predicate.isConcrete() || object.isConcrete()) {
			MotifFilter motifFilter = new MotifFilter(subject, predicate, object);
			List<String>filterList = motifFilter.createMotifFilter();
			for (String filter:filterList) {
				SparqlToGfTranslator.addMotifFilter(filter);
			}
		}
	}

	//TBD : FIX double-directed edges!!! transform the collected MotifPatterns in SparqlToGfTranslator.class into GraphFrames motif-Format
	public static String organizePatternList(List<String> patternList) {
		
		String motif = new String();
		for (String pattern : patternList) {
			motif=motif.concat(pattern+";");
		}
		motif=motif.substring(0, motif.length()-1);
		return motif;
	}

			
    // implement into own class "PREFIXES" in the future
	public static String getURIValue(final String uri) {
        return uri.substring(uri.indexOf("#") + 1);
    }	
}
	

