package export;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

/**
 * The Export Controller manages the logic for exporting call profiles to a csv file.
 * The outputFilePath string is the name of the file to which the file is exported. 
 * @author Pulkit
 *
 */
public class ExportProfile {
	private final static String outputFilePath = "output.csv";
	
	/**
	 * Takes in a list of string arrays and writes them in horizontal order to a 
	 * CSV File. This uses the CSV Utils class to perform the writing. 
	 * @param data
	 * @throws IOException
	 */
	public static void exportToCSV(List<String[]> data) throws IOException {
		final FileWriter writer = new FileWriter(outputFilePath);
		try {
			for (String[] array: data) {
				CSVUtils.writeLine(writer, array); 
			}
			writer.flush();
		} finally {
			writer.close();
		}
	}
}
