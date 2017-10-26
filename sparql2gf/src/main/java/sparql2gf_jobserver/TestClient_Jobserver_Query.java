package sparql2gf_jobserver;

import java.util.ArrayList;
import java.util.List;

import org.apache.jena.query.Query;
import org.apache.jena.query.QueryFactory;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SaveMode;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import org.graphframes.GraphFrame;

import com.typesafe.config.Config;

import spark.jobserver.api.JobEnvironment;
import spark.jobserver.japi.JSessionJob;
import sparql2gf_query_translator.SparqlToGfTranslator;

public class TestClient_Jobserver_Query extends JSessionJob<Dataset<Row>> {

	public Dataset<Row> run(SparkSession spark, JobEnvironment runtime, Config data) {

		Dataset<Row> verticesRaw = spark.sql("select 'src' from `default`.`test_addresses`");
		Dataset<Row> vertices = verticesRaw.withColumnRenamed("src", "id");

		Dataset<Row> edges = spark.sql("select * from `default`.`test_addresses`");

		GraphFrame graphFrame = new GraphFrame(vertices, edges);

		String queryString = data.getString("SPARQL");

		// Output format : "src : String"
		Dataset<Row> graphQuery = SparqlToGfTranslator.translateQuery(graphFrame, queryString);

		// Clear or "Ambiguous" exception might occur" !!!! --- Important
		SparqlToGfTranslator.motifFilterList.clear();
		SparqlToGfTranslator.motifSelectionList.clear();
		SparqlToGfTranslator.patternList.clear();

		// graphQuery.write().format("org.apache.spark.sql.json").mode(SaveMode.Append).save("/home/dennis/graphQueryJS.txt");

		// List<Row> rowList = graphQuery.collectAsList();

		// System.out.println(rowList);

		return graphQuery;

	}

	@Override
	public Config verify(SparkSession contextWrapper, JobEnvironment runtime, Config config) {
		return config;
	}

}
