package client;

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

import finalDesign.SparqlToGfTranslator;

public class TestClient_RDF_noPrefix {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("test").setMaster("local");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext spark = new SQLContext(sc);
		
		
/*Create Schema for Vertex DataFrame*/ 
		
		List<StructField> verFields = new ArrayList<StructField>() ;
		
		verFields.add(DataTypes.createStructField("id", DataTypes.LongType, true));
		verFields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
		//verFields.add(DataTypes.createStructField("name", DataTypes.StringType, true));
		//verFields.add(DataTypes.createStructField("language", DataTypes.StringType, true));
		//verFields.add(DataTypes.createStructField("age", DataTypes.IntegerType, true));
		
		StructType verSchema = DataTypes.createStructType(verFields);
	
		
		/*Create Schema for Edge DataFrame*/
		
		List<StructField> edgFields = new ArrayList<StructField>();
		
		edgFields.add(DataTypes.createStructField("src", DataTypes.LongType, true));
		edgFields.add(DataTypes.createStructField("dst", DataTypes.LongType, true));
		edgFields.add(DataTypes.createStructField("relationship", DataTypes.StringType, true));
		//edgFields.add(DataTypes.createStructField("weight", DataTypes.FloatType, true));
		//edgFields.add(DataTypes.createStructField("id", DataTypes.LongType, true));
		
		StructType edgSchema = DataTypes.createStructType(edgFields);
		
		/*Populate DataFrames with input data*/
		
		Dataset<Row> vertexraw = spark.read().format("csv").option("header",true).schema(verSchema).load("data/testdata_RDF/vertices2.csv");
		Dataset<Row> edgeraw = spark.read().format("csv").option("header",true).schema(edgSchema).load("data/testdata_RDF/edges2.csv");
		
		//create a GraphFrame
		GraphFrame graphFrame = new GraphFrame(vertexraw,edgeraw);
		
		graphFrame.vertices().show((int) graphFrame.vertices().count(), false);
		graphFrame.edges().show((int) graphFrame.edges().count(), false);
		graphFrame.triplets().show((int) graphFrame.triplets().count(), false);
        
		//create SPARQL query string
		//String queryString = "SELECT ?p WHERE { 'josh' ?p 'IOP' }";
		
		//QueryString with URIs-no prefix bindings
		String queryString = "SELECT ?s WHERE {  ?s ?p <https://www.abc.de/person>  }";

		
		//Translate SPARQL query into GraphFrames graph Query and apply on GraphFrame "graphFrame" 
		//- returns DataFrame(table) that represents Query Evaluation
		Dataset<Row> graphQuery = SparqlToGfTranslator.translateQuery(graphFrame, queryString);
		
		//Print DataFrame (show params: make sure that the whole output DataFrame is shown 
		graphQuery.show((int) graphQuery.count(), false);
	}	

}
