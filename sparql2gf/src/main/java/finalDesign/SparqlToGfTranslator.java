package finalDesign;

import java.util.ArrayList;
import java.util.List;
import java.util.Stack;

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

//main class that translates SPARQL query into GraphFrame query, including Motifpattern, Projections/Selections,MotifFilter
public class SparqlToGfTranslator extends OpVisitorBase {

	public static List<String> patternList = new ArrayList<String>();
	public static List<String> motifFilterList = new ArrayList<String>();
	public static List<String> motifSelectionList = new ArrayList<String>();
	

	public static Dataset<Row> translateQuery(GraphFrame graphframe, String queryString) {

		// parse SPARQL query from client input
		Query query = QueryFactory.create(queryString);

		// Prefix-Mapping
		PrefixMapping prefixes = query.getPrefixMapping();

		// create Algebra Tree
		Op opRoot = Algebra.compile(query);
		// Walk Algebra tree
		OpWalker.walk(opRoot, new SparqlToGfTranslator());

		// create final MotifPattern by organizing collected MotifPatterns
		String motifPattern = MotifBuilder.organizePatternList(patternList);

		for (String select : motifSelectionList) {
			System.out.println(select);
		}
		
		

			// Apply query on GraphFrame, that is initialized by client

			// return graphframe.find(motif);

		// GraphFrame query with PROJECTION/SELECTION enabled - allow more than 1 variable selection in the future!
		if (motifSelectionList.size()!=0) {
				int number = motifFilterList.size();
				Dataset<Row> gfQuery = null;
				switch (number) {
				case 0:
				return gfQuery = graphframe.find(motifPattern).select(motifSelectionList.get(0));
				
				case 1:
				return gfQuery = graphframe.find(motifPattern).filter(motifFilterList.get(0)).select(motifSelectionList.get(0));
				
				case 2:
				return gfQuery = graphframe.find(motifPattern).filter(motifFilterList.get(0)).filter(motifFilterList.get(1)).select(motifSelectionList.get(0));
				
				case 3:
				return gfQuery = graphframe.find(motifPattern).filter(motifFilterList.get(0)).filter(motifFilterList.get(1)).filter(motifFilterList.get(2)).select(motifSelectionList.get(0));
				
			}	
				
		}
		//GraphFrame query - BGP only - no PROJECTION/SELECTION
		int number = motifFilterList.size();
		Dataset<Row> gfQuery = null;
		switch (number) {
		case 0:
		return gfQuery = graphframe.find(motifPattern);
		
		case 1:
		return gfQuery = graphframe.find(motifPattern).filter(motifFilterList.get(0));
		
		case 2:
		return gfQuery = graphframe.find(motifPattern).filter(motifFilterList.get(0)).filter(motifFilterList.get(1));
		
		case 3:
		return gfQuery = graphframe.find(motifPattern).filter(motifFilterList.get(0)).filter(motifFilterList.get(1)).filter(motifFilterList.get(2));
		
		}
		return gfQuery;
	}

	
	
	
	// While OpWalker Visitors will apply "visit(OpBGP opBGP)"-functions on BGP of
	// SPARQL query
	public void visit(OpBGP opBGP) {
		System.out.println("BGP recognized : " + opBGP);
		final List<Triple> triples = opBGP.getPattern().getList();
		for (final Triple triple : triples) {
			MotifBuilder motif = new MotifBuilder();
			motif.transform(triple);

		}
	}

	// While OpWalker Visitors will apply "visit(OpProject opProject)"-functions on
	// Projection-Mechanism of SPARQL query
	public void visit(OpProject opProject) {
		System.out.println("Project recognized : " + opProject);
		final List<Var> vars = opProject.getVars();
		for (Var var : vars) {

			MotifSelection selection = new MotifSelection(var);
			motifSelectionList.add(selection.createSelection(var));
		}
	}

	// Collects all motifPatterns - called by MotifBuilder
	public static void addMotifPattern(String motifPattern) {
		patternList.add(motifPattern);
	}

	public static void addMotifFilter(String filter) {
		motifFilterList.add(filter);
	}

	public static void addSelection(String selection) {
		motifSelectionList.add(selection);
	}
}
