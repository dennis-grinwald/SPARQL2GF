package sparql2gf;

import java.io.PrintWriter;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.algebra.OpWalker;

public class SparqlToGfTranslator {
	
	//Query string in User application
	private String queryString;
	
	/* Instead of "STRING" use input format of GF-motif */
	public String translateQuery(String q) {
		
		this.queryString=q;
		
		//Create SPARQL query string ---!!! Add Prefix Mapping
		Query query = QueryFactory.create(queryString);
		
		//Prefix Mapping
//		PrefixMapping prefixes = query.getPrefixMapping();

		
		//Generate translation logfile
		PrintWriter logWriter = new PrintWriter(System.out);
		
		
		//Output original query to log
		logWriter.println("SPARQL Input Query:");
		logWriter.println("###################");
		logWriter.println(query);
		logWriter.println();
		
		//Transform SPARQL query string to AST / Op Tree
		Op opRoot = Algebra.compile(query);
		
		//Output original Algebra Tree to log
		logWriter.println("Algebra Tree of Query:");
		logWriter.println("######################");
//		logWriter.println(opRoot.toString(prefixes));
		logWriter.println(opRoot);
		logWriter.println();
		
		OpWalker.walk(opRoot, null);

		
		
		return opRoot.toString();
				
	}

}
