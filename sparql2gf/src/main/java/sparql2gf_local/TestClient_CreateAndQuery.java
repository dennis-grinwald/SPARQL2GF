package sparql2gf_local;

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

public class TestClient_CreateAndQuery {

	public static void main(String[] args) {
		SparkConf conf = new SparkConf().setAppName("test").setMaster("local[*]"); // ("spark://dennis-HP-ENVY-Notebook-13-ab0XX:7077");
																					// //.set("spark.scheduler.mode",
																					// "FAIR");
		JavaSparkContext sc = new JavaSparkContext(conf);
		SQLContext spark = new SQLContext(sc);

		/* Create schema for Vertex DataFrame */

		List<StructField> verFields = new ArrayList<StructField>();

		verFields.add(DataTypes.createStructField("id", DataTypes.StringType, true));

		StructType verSchema = DataTypes.createStructType(verFields);

		/* Create Schema for Edge DataFrame */

		List<StructField> edgFields = new ArrayList<StructField>();

		edgFields.add(DataTypes.createStructField("src", DataTypes.StringType, true));
		edgFields.add(DataTypes.createStructField("dst", DataTypes.StringType, true));
		edgFields.add(DataTypes.createStructField("relationship", DataTypes.StringType, true));

		StructType edgSchema = DataTypes.createStructType(edgFields);

		/* Populate DataFrames with input data */

		// DataFrame creation on cloud VM
		// Dataset<Row> vertexraw =
		// spark.read().format("csv").option("header",true).schema(verSchema).load("/home/dgrinwald/server/data/vertices.csv");
		// Dataset<Row> edgeraw =
		// spark.read().format("csv").option("header",true).schema(edgSchema).load("/home/dgrinwald/server/data/edges.csv");

		// DataFrame creation on local machine
		Dataset<Row> vertexraw = spark.read().format("csv").option("header", true).schema(verSchema)
				.load("/home/dennis/git/SPARQL2GF/sparql2gf/data/testdata_RDF/vertices.csv");
		Dataset<Row> edgeraw = spark.read().format("csv").option("header", true).schema(edgSchema)
				.load("/home/dennis/git/SPARQL2GF/sparql2gf/data/testdata_RDF/edges.csv");

		// create a GraphFrame
		GraphFrame graphFrame = new GraphFrame(vertexraw, edgeraw);

		// Different views of the GraphFrame
		// graphFrame.vertices().show((int) graphFrame.vertices().count(), false);
		// graphFrame.edges().show((int) graphFrame.edges().count(), false);
		// graphFrame.triplets().show((int) graphFrame.triplets().count(), false);

		// create SPARQL query string

		// bsbm-tools dataset

		String queryString = "SELECT ?o WHERE { ?s <http://purl.org/dc/elements/1.1/date> ?o }";
		// String queryString = "SELECT ?o WHERE { ?s ?p ?o}";
		// String queryString = "SELECT ?o3 WHERE { ?s ?p ?o . ?o ?p2 ?o2 . ?o2 ?p3
		// ?o3}";
		// String queryString = "SELECT * WHERE { ?s ?p
		// <http://www4.wiwiss.fu-berlin.de/bizer/bsbm/v01/instances/dataFromRatingSite1/RatingSite1>
		// }";

		// special case (object with extra symbols "-") -> change motifFilter class
		// line:
		// String queryString = "SELECT * WHERE { ?s ?p '2008-06-17' }";

		// Translate SPARQL query into GraphFrames graph Query and apply on GraphFrame
		// "graphFrame"
		// - returns DataFrame(table) that represents Query Evaluation
		Dataset<Row> graphQuery = SparqlToGfTranslator.translateQuery(graphFrame, queryString);

		/*
		 * SparqlToGfTranslator.motifFilterList.clear();
		 * SparqlToGfTranslator.motifSelectionList.clear();
		 * SparqlToGfTranslator.patternList.clear();
		 */

		// Print DataFrame (show params: make sure that the whole output DataFrame is
		// shown
		// graphQuery.show((int) graphQuery.count(), false);

		List<Row> rowList = graphQuery.collectAsList();

		System.out.println(rowList);

	}

}
