package sparql2gf_jobserver;

import java.util.ArrayList;
import java.util.List;

import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;
import com.typesafe.config.Config;

import spark.jobserver.NamedObjectSupport;
import spark.jobserver.NamedObjects;
import spark.jobserver.api.JobEnvironment;
import spark.jobserver.japi.JSessionJob;

public class DataFrameLoader extends JSessionJob<Long>  {

	/*
	 * The following run method will be implemented if following actions take place:
	 * 1. custom Spark context is being implemented, that contains a vertex, and
	 * edge DataFrame 
	 * 2. TestClient_Jobserver_Query.class will be modified in order work directly with extracted DF as mentioned above
	 */

	/*
	 * public String run(SparkSession spark, JobEnvironment runtime, Config data) {
	 * 
	 * //Create vertex- and edge-table schema List<StructField> verFields = new
	 * ArrayList<StructField>() ;
	 * 
	 * verFields.add(DataTypes.createStructField("id", DataTypes.StringType, true));
	 * 
	 * StructType verSchema = DataTypes.createStructType(verFields);
	 * 
	 * List<StructField> edgFields = new ArrayList<StructField>();
	 * 
	 * edgFields.add(DataTypes.createStructField("src", DataTypes.StringType,
	 * true)); edgFields.add(DataTypes.createStructField("dst",
	 * DataTypes.StringType, true));
	 * edgFields.add(DataTypes.createStructField("relationship",
	 * DataTypes.StringType, true));
	 * 
	 * StructType edgSchema = DataTypes.createStructType(edgFields);
	 * 
	 * final Dataset<Row> vertexraw =
	 * spark.read().format("csv").option("header",true).schema(verSchema).load(
	 * "/home/dennis/workspace/bulkloader/data/verticesO.csv"); final Dataset<Row>
	 * edgeraw =
	 * spark.read().format("csv").option("header",true).schema(edgSchema).load(
	 * "/home/dennis/workspace/bulkloader/data/edgesO.csv");
	 * 
	 * return "DataFrames are created"+ vertexraw.toString()+ ","
	 * +edgeraw.toString(); }
	 */

	/*
	 * This method+fields loads one single DataFrame out of edges- and vertices.csv
	 * files ([src][dst][relation][vertex]) The spark context holding that DataFrame
	 * is then used in the TestClient_Jobserver_Query.class and processed: 1.
	 * "vertex"-column is extracted and itself transformed into a
	 * DataFrame(vertices) 2. "src,dst,relation"-columns are also extracted and
	 * loaded into a additional DataFrame(edges)
	 */


	private final String tableCreate = "CREATE TABLE `default`.`test_addresses`";
	private final String tableArgs = "(`src` String, `dst` String, `relationship` String)";
	private final String tableRowFormat = "ROW FORMAT DELIMITED FIELDS TERMINATED BY ','";
	private final String tableColFormat = "COLLECTION I	TEMS TERMINATED BY '\002'";
	private final String tableMapFormat = "MAP KEYS TERMINATED BY '\003' STORED";
	private final String tableAs = "AS TextFile";
	private final String loadPath = "'/home/dennis/workspace/TESTsparql2gf/data/dataFrame.txt'";  
	
	public Long run(SparkSession spark, JobEnvironment runtime, Config data) {

		
		//DataFrame is created using SQL statements - alternatively create Struct fields like in sparql2gf_local.TestClient_CreateAndQuery
		spark.sql("DROP TABLE if exists `default`.`test_addresses`");
		spark.sql(String.format("%s %s %s %s %s %s", tableCreate, tableArgs, tableRowFormat, tableColFormat,
				tableMapFormat, tableAs));
		spark.sql(String.format("LOAD DATA LOCAL INPATH %s OVERWRITE INTO TABLE `default`.`test_addresses`", loadPath));

		
		
		final Dataset<Row> DataFrame = spark.sql("SELECT * FROM `default`.`test_addresses`");
		
		return DataFrame.count();
	}

	@Override
	public Config verify(SparkSession contextWrapper, JobEnvironment runtime, Config config) {
		return config;
	}

}
