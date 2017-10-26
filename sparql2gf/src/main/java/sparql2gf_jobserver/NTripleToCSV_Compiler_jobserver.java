package sparql2gf_jobserver;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.semanticweb.yars.nx.Node;
import org.semanticweb.yars.nx.parser.NxParser;

public class NTripleToCSV_Compiler_jobserver {

	private static Scanner scanner;

	public static void main(String[] args) throws IOException {

		// data from local machine
		scanner = new Scanner(new File("/home/dennis/Documents/BA/bsbmtools-0.2/dataset.nt"));

		// data from VM in cloud
		// scanner = new Scanner(new
		// File("/home/dgrinwald/server/data/bsbmtools-0.2/dataset.nt"));

		// FileWriter that writes to local disk
		FileWriter DFWriter = new FileWriter("/home/dennis/workspace/TESTsparql2gf/data/dataFrame.txt");

		while (scanner.hasNextLine()) {
			String line = scanner.nextLine();
			String delimiter = "[ ]+";
			String[] nodes = line.split(delimiter);

			String regex = "[\"].*\"";
			Pattern checkRegex = Pattern.compile(regex);
			Matcher regexMatcher = checkRegex.matcher(line);

			boolean flag = false;

			if (regexMatcher.find()) {
				if (regexMatcher.group().length() != 0) {
					flag = true;

					DFWriter.write(nodes[0].substring(1, nodes[0].length() - 1) + ","
							+ regexMatcher.group().substring(1, regexMatcher.group().length() - 1) + ","
							+ nodes[1].substring(1, nodes[1].length() - 1));
					DFWriter.write("\n");

				}
			}

			if (flag == false) {

				DFWriter.write(nodes[0].substring(1, nodes[0].length() - 1) + ","
						+ nodes[2].substring(1, nodes[2].length() - 1) + ","
						+ nodes[1].substring(1, nodes[1].length() - 1));
				DFWriter.write("\n");

			}

			DFWriter.flush();
		}

		DFWriter.close();
	}
}
