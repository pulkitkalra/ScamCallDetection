package export;

import java.io.IOException;
import java.io.Writer;

/**
 * Class is used to perform the formatting required to write to a file in
 * the CSV format.
 * Thanks to Orion Health's open-source RLCAnalyser!
 */
public class CSVUtils {

	private static final char DEFAULT_SEPARATOR = ',';

	public static void writeLine(final Writer w, final String[] values) throws IOException {
		writeLine(w, values, DEFAULT_SEPARATOR, ' ');
	}

	public static void writeLine(final Writer w, final String[] values, final char separators) throws IOException {
		writeLine(w, values, separators, ' ');
	}

	//https://tools.ietf.org/html/rfc4180
	private static String followCVSformat(final String value) {

		String result = value;
		if (result.contains("\"")) {
			result = result.replace("\"", "\"\"");
		}
		return result;
	}

	public static void writeLine(final Writer w, final String[] values, char separators, final char customQuote) throws IOException {

		boolean first = true;

		//default customQuote is empty

		if (separators == ' ') {
			separators = DEFAULT_SEPARATOR;
		}

		final StringBuilder sb = new StringBuilder();
		for (final String value : values) {
			if (!first) {
				sb.append(separators);
			}
			if (customQuote == ' ') {
				sb.append(followCVSformat(value));
			} else {
				sb.append(customQuote).append(followCVSformat(value)).append(customQuote);
			}

			first = false;
		}
		sb.append("\n");
		w.append(sb.toString());
	}
}
