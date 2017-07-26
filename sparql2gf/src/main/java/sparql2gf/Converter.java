package sparql2gf;

import java.io.FileNotFoundException;
import java.io.PrintWriter;
import org.apache.log4j.Logger;
import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.jena.shared.PrefixMapping;



/* Main class that generates Algebra Tree for a SPARQL query and converts SPARQL-BGP into motif */

public class Converter {
	
	private String inputFile = null;
	
	// Define a static logger variable so that it references the corresponding Logger instance
	private static final Logger logger = Logger.getLogger(Converter.class);
	
	
	public String convertQuery() {

	//Query parsing and Prefix mapping
	
	Query query = QueryFactory.read("file:"+inputFile);
	PrefixMapping prefixes = query.getPrefixMapping();
	
	PrintWriter logWriter;
	try {
		logWriter = new PrintWriter(inputFile + ".log");
	} catch (FileNotFoundException ex) {
		logger.warn("Cannot open translation logfile, using stdout instead!", ex);
		logWriter = new PrintWriter(System.out);
	}
	
	//Output original query to log
			logWriter.println("SPARQL Input Query:");
			logWriter.println("###################");
			logWriter.println(query);
			logWriter.println();
			
			
			
			return motif;
	
	
	}
	
	
	
	

}
