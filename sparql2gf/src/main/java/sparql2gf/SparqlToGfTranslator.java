package sparql2gf;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;

import org.apache.jena.graph.Triple;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.algebra.OpVisitorBase;
import org.apache.jena.sparql.algebra.OpWalker;
import org.apache.jena.sparql.algebra.op.OpBGP;


public class SparqlToGfTranslator extends OpVisitorBase {
	
	//Query string in User application
	private String queryString;
	
	//Initialize Filter List
	private ArrayList<String> filterList = new ArrayList<String>();
	
	//motifPatternList
	private ArrayList<String> motifPatternList = new ArrayList<String>();
    
	//motifOperationList
	private ArrayList<String> motifOperationList = new ArrayList<String>();


	/* List<Motif> motif = new ArrayList<Motif>(); */

	
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
		Op op = Algebra.compile(query);
		
		
		//Output original Algebra Tree to log
		logWriter.println("Algebra Tree of Query:");
		logWriter.println("######################");
//		logWriter.println(opRoot.toString(prefixes));
		logWriter.println(op);
		logWriter.println();
		
		//Gives back PROJECTION, BGP/ later also OPTIONAL etc.
		OpWalker.walk(op, this);
		
		return "Op Tree:"+" " + op.toString();
				
	}

	public void visit(final OpBGP opBGP) {
		{
			final List<Triple> triples = opBGP.getPattern().getList();
	//		final Traversal[] matchTraversals = new Traversal[triples.size()]; 
			for (final Triple triple : triples) {

				ArrayList<String> motif = MotifBuilder.transform(triple);
				     // add ready motif Strings
			    // add operational strings, e.g: "s.name=''" //
			}

		}

	} 
	
	//add motifs  
	
	
}
