package sparql2gf;

public class TestSparqlToGfTranslatorQueryParsing {

	public static void main(String[] args) {
		
		
		//Example Query ---!!! Add Prefix
		String query1 = "SELECT ?s WHERE { ?s ?p ?o }";
		
		
		// Create new translator object
		SparqlToGfTranslator translator1 = new SparqlToGfTranslator();
		
		
		// Should print BGP of SPARQL Query
		System.out.println(translator1.translateQuery(query1));
	}
}
