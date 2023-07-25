package freshqiwi.cbr.rts.utils;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class DataFormatter {
	public static String parse(String date, String format, String targetFormat) {
		DateTimeFormatter originalFormatDF = DateTimeFormatter.ofPattern(format);
		DateTimeFormatter targetFormatDF = DateTimeFormatter.ofPattern(targetFormat);
		return targetFormatDF.format(LocalDate.parse(date, originalFormatDF));
	}
}
