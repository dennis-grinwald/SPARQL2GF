package sparql2gf_graph_builder;

import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.*;

import org.semanticweb.yars.nx.parser.NxParser;
import org.semanticweb.yars.nx.Node;



public class NTripleToCSV_Compiler {
	
	static Set<String> nodeList = new HashSet<String>();
	static List<String> tripleList = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException {
		
		FileInputStream is = new FileInputStream("/home/dennis/Dropbox/sf/BA/testdata/test_n_triples");

		NxParser nxp = new NxParser();
		nxp.parse(is);

		for (Node[] nx : nxp) {
			
		  nodeList.add(nx[0].toString());
		  nodeList.add(nx[2].toString());  
		  tripleList.add(nx[0]+","+nx[1]+","+nx[2]);
		  
		}
				
		FileWriter vertexWriter = new FileWriter("/home/dennis/Dropbox/sf/BA/testdata/vertexCSV.csv");
		
		for (String s : nodeList) {
			vertexWriter.write(s);
			vertexWriter.write("\n");
			}
		vertexWriter.close();
		
		FileWriter edgeWriter = new FileWriter("/home/dennis/Dropbox/sf/BA/testdata/edgeCSV.csv");
		
		for (String s : tripleList) {
			edgeWriter.write(s);
			edgeWriter.write("\n");
		}
		edgeWriter.close();	
		
		}
	

}
