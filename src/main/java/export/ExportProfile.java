package export;

import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class ExportProfile {
	private final static String outputFilePath = "output.csv";
	
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
	
	
	
	/*public static void main(String[] args) {
		try {
			export("output.csv");
		} catch (IOException e) {
			e.printStackTrace();
		}
	}*/

}
