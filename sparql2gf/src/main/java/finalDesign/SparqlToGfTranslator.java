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
import org.apache.jena.sparql.core.Var;
import org.graphframes.GraphFrame;

import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.jena.sparql.core.Var;
import org.apache.jena.shared.PrefixMapping;


import testpackage.WalkOperations;


//main class that translates SPARQL query into GraphFrame query, including Motifpattern, Projections/Selections,MotifFilter
public class SparqlToGfTranslator extends OpVisitorBase {
	
	public static List<String> patternList = new ArrayList<String>();
	public static List<String> motifFilterList = new ArrayList<String>();


	public static Dataset<Row> translateQuery(GraphFrame graphframe, String queryString) {
		
		//parse SPARQL query from client input
		Query query= QueryFactory.create(queryString);
		
		//Prefix-Mapping
		PrefixMapping prefixes = query.getPrefixMapping();

		//create Algebra Tree 
		Op opRoot = Algebra.compile(query);
		//Walk Algebra tree
		OpWalker.walk(opRoot, new SparqlToGfTranslator());
		
		//create final MotifPattern by organizing collected MotifPatterns
		String motifPattern = MotifBuilder.organizePatternList(patternList);
		
		//Apply query on GraphFrame, that is initialized by client
		//return graphframe.find(motif);
	    
		
		int number = motifFilterList.size();
		Dataset<Row> gfQuery = null;
		switch (number) {
		case 0: gfQuery = graphframe.find(motifPattern);
		break;
		case 1: gfQuery = graphframe.find(motifPattern).filter(motifFilterList.get(0));
		break;
		case 2: gfQuery = graphframe.find(motifPattern).filter(motifFilterList.get(0)).filter(motifFilterList.get(1));
		break;
		case 3: gfQuery = graphframe.find(motifPattern).filter(motifFilterList.get(0)).filter(motifFilterList.get(1)).filter(motifFilterList.get(2));
		break;
		}
		return gfQuery;	
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
    	final List<Var> vars = new ArrayList<Var>();
    	for(Var var : vars) {
    		//MotifProjection projection = new MotifProjection(var);
    		
    		
    	}
    	
    	
   
    }
	
    
    //Collects all motifPatterns - called by MotifBuilder
	public static void addMotifPattern(String motifPattern) {
		patternList.add(motifPattern);			
	}
	
	public static void addMotifFilter(String filter) {
		motifFilterList.add(filter);
	}
}
