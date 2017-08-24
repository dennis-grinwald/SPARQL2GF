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


//input N-Triple file , output 2 CSV files (Vertex DF + Edge DF)
public class NTripleToCSV_Compiler {
	
	static Set<String> nodeList = new HashSet<String>();
	static List<String> tripleList = new ArrayList<String>();
	
	public static void main(String[] args) throws IOException {
		
		//inout N-triple file
		FileInputStream is = new FileInputStream("/home/dennis/Documents/BA/bsbmtools-0.2/dataset.nt");

		//parse N-triples, add subject and object nodes of RDF triples to nodeList & full triples to tripleList, format : "src,dst,realtionship"
		NxParser nxp = new NxParser();
		nxp.parse(is);

		for (Node[] nx : nxp) {
			
		  nodeList.add(nx[0].getLabel());
		  nodeList.add(nx[2].getLabel());  
		  tripleList.add(nx[0].getLabel()+","+nx[2].getLabel()+","+nx[1].getLabel());
		}
				
		//write nodeList elements into vertex.csv file
		FileWriter vertexWriter = new FileWriter("/home/dennis/git/SPARQL2GF/sparql2gf/data/testdata_RDF/vertices.csv");
			
		for (String s : nodeList) {
			vertexWriter.write(s);
			vertexWriter.write("\n");
			}
		vertexWriter.close();
		
		//write tripleList elements into edge.csv file
		FileWriter edgeWriter = new FileWriter("/home/dennis/git/SPARQL2GF/sparql2gf/data/testdata_RDF/edges.csv");
		
		for (String s : tripleList) {
			edgeWriter.write(s);
			edgeWriter.write("\n");
		}
		edgeWriter.close();	
		
		//testPrint if vertex and edge file creation was successful
		System.out.println("CSVs successfully created");
		
		}
	

}
