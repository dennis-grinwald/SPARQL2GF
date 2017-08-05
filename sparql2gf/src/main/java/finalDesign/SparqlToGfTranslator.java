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


//main class that translates SPARQL query into GraphFrame query, including Motifpattern, Projections/Selections,MotifFilter
public class SparqlToGfTranslator extends OpVisitorBase {
	
	public static List<String> patternList = new ArrayList<String>();
	public static String motifFilter;


	public static Dataset<Row> translateQuery(GraphFrame graphframe, String queryString) {
		
		//parse SPARQL query from client input
		Query query= QueryFactory.create(queryString);
		//create Algebra Tree 
		Op opRoot = Algebra.compile(query);
		//Walk Algebra tree
		OpWalker.walk(opRoot, new SparqlToGfTranslator());
		
		//create final MotifPattern by organizing collected MotifPatterns
		String motif = MotifBuilder.organizePatternList(patternList);
		
		//Apply query on GraphFrame, that is initialized by client
		//return graphframe.find(motif);
		
	/**	int number;
		switch (number) {
		case 1: return graphframe.find(motif).filter(motifFilter);
		case 2: return graphframe.find(motif).filter(motiFilter).filter(motifFilter2);
		case 3: return graphframe.find(motif).filter(motifFilter).filter(motifFilter2).filter(motifFilter3);
	
		} **/
		
		if (motifFilter!=null) {
			return graphframe.find(motif).filter(motifFilter);
		}
		return graphframe.find(motif).filter(motifFilter);
		
	}
    
	
	//While OpWalker Visitors will apply "visit(OpBGP opBGP)"-functions on BGP of SPARQL query
	public void visit(OpBGP opBGP) {
		System.out.println("BGP recognized : "+opBGP);		
		final List<Triple> triples = opBGP.getPattern().getList();		
		for (final Triple triple : triples) {	
			MotifBuilder motif = new MotifBuilder();
			motif.transform(triple);
			
	
		}			
	}
	
	//While OpWalker Visitors will apply "visit(OpProject opProject)"-functions on Projection-Mechanism of SPARQL query
    public void visit(OpProject opProject) {
    	System.out.println("Project recognized : "+opProject);
   
    }
	
    
    //Collects all motifPatterns - called by MotifBuilder
	public static void addMotifPattern(String motifPattern) {
		patternList.add(motifPattern);			
	}
	
	public static void addMotifFilter(String filter) {
		motifFilter = filter;
	}
}
