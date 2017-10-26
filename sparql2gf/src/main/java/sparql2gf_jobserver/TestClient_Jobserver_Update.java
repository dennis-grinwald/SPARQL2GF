package sparql2gf_jobserver;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.sql.AnalysisException;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Encoder;
import org.apache.spark.sql.Encoders;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.RowFactory;
import org.apache.spark.sql.SparkSession;
import org.apache.spark.sql.types.DataTypes;
import org.apache.spark.sql.types.StructField;
import org.apache.spark.sql.types.StructType;

import com.typesafe.config.Config;

import breeze.linalg.scaleAdd;
import spark.jobserver.api.JobEnvironment;
import spark.jobserver.japi.JSessionJob;

public class TestClient_Jobserver_Update extends JSessionJob<Long> {

	//table-creation arguments for added DataFrame Row
	private final String tableCreate = "CREATE TABLE `newTable`";
	private final String tableArgs = "(`src` String, `dst` String, `relationship` String)";
	private final String tableRowFormat = "ROW FORMAT DELIMITED FIELDS TERMINATED BY ','";
	private final String tableColFormat = "COLLECTION ITEMS TERMINATED BY '\002'";
	private final String tableMapFormat = "MAP KEYS TERMINATED BY '\003' STORED";
	private final String tableAs = "AS TextFile";
	private final String loadPath = "'/home/dennis/workspace/TESTsparql2gf/data/JobserverPost.txt'";
	
	@Override
	public Long run(SparkSession spark, JobEnvironment runtime, Config data) {

		
		//fetch present DF from spark context
		final Dataset<Row> dfOld = spark.sql("SELECT * FROM `default`.`test_addresses`");

		//create new DF from textFile - here it's a single Row
		spark.sql("DROP TABLE if exists `newTable`");
		spark.sql(String.format("%s %s %s %s %s %s", tableCreate, tableArgs, tableRowFormat, tableColFormat,
				tableMapFormat, tableAs));
		spark.sql(String.format("LOAD DATA LOCAL INPATH %s OVERWRITE INTO TABLE `newTable`", loadPath));

		final Dataset<Row> dfNew = spark.sql("SELECT * FROM `newTable`");

		//Update Graph by concatenating both the present DF with the new One ---> only for this test purpose & not working multiple times ! -> ERROR: table: dfUnion exists --> Look for Overwrite SQL-method or other way
		final Dataset<Row> dfUnion = dfOld.union(dfNew);
		spark.sql("CREATE TABLE dfUnion (SELECT * FROM `default`.`test_addresses`    UNION    SELECT * FROM `newTable`)");

		//return number of rows to test if Graph has been updated		
		return dfUnion.count();

	}

	public Config verify(SparkSession contextWrapper, JobEnvironment runtime, Config config) {
		// TODO Auto-generated method stub
		return config;
	}

}
