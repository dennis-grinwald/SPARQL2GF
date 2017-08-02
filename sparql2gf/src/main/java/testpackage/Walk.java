package testpackage;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.sparql.algebra.Algebra;
import org.apache.jena.sparql.algebra.Op;
import org.apache.jena.sparql.algebra.OpVisitorBase;
import org.apache.jena.sparql.algebra.OpWalker;
import org.apache.jena.sparql.algebra.op.OpBGP;
import org.apache.jena.sparql.algebra.op.OpProject;

public class Walk extends OpVisitorBase {
	
	public Walk() {}
	
	public void translateQuery(String queryString) {
		
		Query query= QueryFactory.create(queryString);
		Op opRoot = Algebra.compile(query);
		OpWalker.walk(opRoot, this); 
	}
    
	public void visit(OpBGP opBGP) {
		System.out.println("BGP recognized : "+opBGP);
	}
    public void visit(OpProject opProject) {
    	System.out.println("Project recognized : "+opProject);
   
    }
   
}
