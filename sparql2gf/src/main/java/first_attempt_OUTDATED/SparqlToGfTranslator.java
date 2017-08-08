package first_attempt_OUTDATED;

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
import org.apache.jena.sparql.algebra.op.OpProject;


/**This is the main class that translates a SPARQL query from a client to a GraphFrame Motif**/

public class SparqlToGfTranslator extends OpVisitorBase {
	
	
	
//Initializes the translation of SPARQL to GraphFrame Motif
//Takes a queryString defined by user as input
public void translateQuery(String queryString) {
		
	
	
	    //Parse SPARQL query string
		Query query= QueryFactory.create(queryString);
	
		//Create an Algebra tree of the Query object
		Op opRoot = Algebra.compile(query);
		
		//Walk the Algebra tree recursively, translating it's BGPs to motifPatterns
		OpWalker.walk(opRoot, this); 
	}
    


	public void visit(OpBGP opBGP) {
		System.out.println("BGP recognized : "+opBGP);
	}
    public void visit(OpProject opProject) {
    	System.out.println("Project recognized : "+opProject);
   
    }

	
} 
	
	
	

