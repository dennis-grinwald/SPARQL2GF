package finalDesign;

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
import org.graphframes.GraphFrame;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;

import testpackage.WalkOperations;

public class SparqlToGfTranslator extends OpVisitorBase {
	
	public static List<String> patternList = new ArrayList<String>();


	public static Dataset<Row> translateQuery(GraphFrame graphframe, String queryString) {
		
		Query query= QueryFactory.create(queryString);
		Op opRoot = Algebra.compile(query);
		OpWalker.walk(opRoot, new SparqlToGfTranslator());
		String motif = MotifBuilder.organizePatternList(patternList);
		
		return graphframe.find(motif);
	}
    
	public void visit(OpBGP opBGP) {
		System.out.println("BGP recognized : "+opBGP);		
		final List<Triple> triples = opBGP.getPattern().getList();		
		for (final Triple triple : triples) {	
			MotifBuilder motif = new MotifBuilder();
			motif.transform(triple);
			
	
		}			
	}
    public void visit(OpProject opProject) {
    	System.out.println("Project recognized : "+opProject);
   
    }
	
	public static void add(String motifPattern) {
		patternList.add(motifPattern);			
	}

}
