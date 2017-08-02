package testpackage;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.algebra.OpWalker;

public class Client {

	public static void main(String[] args) {
		
		String queryString = "SELECT ?s WHERE { ?s ?p ?o }";
		Walk testWalk1 = new Walk();
		
		testWalk1.translateQuery(queryString);
		
	}

}
