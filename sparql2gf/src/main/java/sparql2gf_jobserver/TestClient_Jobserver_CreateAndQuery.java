package sparql2gf_jobserver;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.graphframes.GraphFrame;

import com.typesafe.config.Config;

import spark.jobserver.api.JobEnvironment;
import spark.jobserver.japi.JSessionJob;
import sparql2gf_query_translator.SparqlToGfTranslator;

public class TestClient_Jobserver_CreateAndQuery extends JSessionJob<Dataset<Row>> {

	@Override
	public Dataset<Row> run(SparkSession spark, JobEnvironment runtime, Config data) {
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

		// DataFrame creation on local machine
		final Dataset<Row> vertexraw = spark.read().format("csv").option("header", true).schema(verSchema)
				.load("/home/dennis/workspace/bulkloader/data/vertices.csv");
		final Dataset<Row> edgeraw = spark.read().format("csv").option("header", true).schema(edgSchema)
				.load("/home/dennis/workspace/bulkloader/data/edges.csv");

		// create a GraphFrame
		GraphFrame graphFrame = new GraphFrame(vertexraw, edgeraw);

		String queryString = data.getString("SPARQL");

		Query query = QueryFactory.create(queryString);

		Dataset<Row> graphQuery = SparqlToGfTranslator.translateQuery(graphFrame, queryString);

		SparqlToGfTranslator.motifFilterList.clear();
		SparqlToGfTranslator.motifSelectionList.clear();
		SparqlToGfTranslator.patternList.clear();

		return graphQuery;

	}

	@Override
	public Config verify(SparkSession contextWrapper, JobEnvironment runtime, Config config) {
		return config;
	}

}
