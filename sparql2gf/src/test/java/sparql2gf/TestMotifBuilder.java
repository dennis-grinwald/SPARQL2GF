package sparql2gf;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;

public class TestMotifBuilder {

	public static void main(String[] args) {
		
		String query = "SELECT * WHERE { ?s ?p ?o }";
		Query query1= QueryFactory.create(query);
		Op op = Algebra.compile(query1);
	/*	Triple triple1 = op.getPattern();   */
		
	}     

}
