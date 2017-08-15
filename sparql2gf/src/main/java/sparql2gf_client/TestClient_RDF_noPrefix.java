package sparql2gf_client;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.graphframes.GraphFrame;


import sparql2gf_query_translator.SparqlToGfTranslator;

public class TestClient_RDF_noPrefix {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("test").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext spark = new SQLContext(sc);
		
		
/*Create Schema for Vertex DataFrame*/ 
		
		List<StructField> verFields = new ArrayList<StructField>() ;
		
		verFields.add(DataTypes.createStructField("id", DataTypes.StringType, true));
		//verFields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
		//verFields.add(DataTypes.createStructField("language", DataTypes.StringType, true));
		//verFields.add(DataTypes.createStructField("age", DataTypes.IntegerType, true));
		
		StructType verSchema = DataTypes.createStructType(verFields);
	
		
		/*Create Schema for Edge DataFrame*/
		
		List<StructField> edgFields = new ArrayList<StructField>();
		
		edgFields.add(DataTypes.createStructField("src", DataTypes.StringType, true));
		edgFields.add(DataTypes.createStructField("dst", DataTypes.StringType, true));
		edgFields.add(DataTypes.createStructField("relationship", DataTypes.StringType, true));
		//edgFields.add(DataTypes.createStructField("weight", DataTypes.FloatType, true));
		//edgFields.add(DataTypes.createStructField("id", DataTypes.LongType, true));
		
		StructType edgSchema = DataTypes.createStructType(edgFields);
		
		/*Populate DataFrames with input data*/
		
		Dataset<Row> vertexraw = spark.read().format("csv").option("header",true).schema(verSchema).load("data/testdata_RDF/vertexCSV.csv");
		Dataset<Row> edgeraw = spark.read().format("csv").option("header",true).schema(edgSchema).load("data/testdata_RDF/edgeCSV.csv");
		
		//create a GraphFrame
		GraphFrame graphFrame = new GraphFrame(vertexraw,edgeraw);
		
		//show parameters: show whole width of table
		//graphFrame.vertices().show((int) graphFrame.vertices().count(), false);
		//graphFrame.edges().show((int) graphFrame.edges().count(), false);
		//graphFrame.triplets().show((int) graphFrame.triplets().count(), false);
        
		//create SPARQL query string
		String queryString = "SELECT ?o WHERE { ?s <http://purl.org/dc/elements/1.1/date> ?o }";
		
		//Various testQueryStrings with URIs-no prefix bindings
		//String queryString = "SELECT * WHERE { ?s ?p ?o}";
        //String queryString = "SELECT * WHERE {  ?s ?p ?o }";
		//String queryString = "SELECT ?petersCreations WHERE {  <https://www.abc.de/peter> <https://www.relation.de/created> ?petersCreations }";
		//BUG String queryString = "SELECT * WHERE {  <https://abc.de/marko> ?p ?o }";
		//String queryString = "SELECT ?personNode WHERE { ?personNode <https://www.relation.de/label> <https://www.abc.de/person> } ";
		//String queryString = "SELECT ?s WHERE { ?s <https://www.relation.de/knows> <https://www.abc.de/josh> }";

		
		//Translate SPARQL query into GraphFrames graph Query and apply on GraphFrame "graphFrame" 
		//- returns DataFrame(table) that represents Query Evaluation
	//	Dataset<Row> graphQuery = SparqlToGfTranslator.translateQuery(graphFrame, queryString);
		
		//Print DataFrame (show params: make sure that the whole output DataFrame is shown 
	 //   graphQuery.show((int) graphQuery.count(), false);
		
	}	

}
