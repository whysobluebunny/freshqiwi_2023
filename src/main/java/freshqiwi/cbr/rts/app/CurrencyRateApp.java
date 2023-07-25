package freshqiwi.cbr.rts.app;


import freshqiwi.cbr.rts.dto.Rate;
import freshqiwi.cbr.rts.manager.RequestManager;
import freshqiwi.cbr.rts.utils.DataFormatter;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;
import java.time.format.DateTimeParseException;

public class CurrencyRateApp {
	private static String[] handleArgs(String[] args) throws DateTimeParseException {
		String[] handledArgs = new String[args.length];
		if (args.length != 2) {
			throw new IllegalArgumentException("Использование: currency_rates --code=USD --date=2022-10-08");
		}

		for (int i = 0; i < args.length; i++) {
			if (args[i].startsWith("--code=")) {
				handledArgs[i] = args[i].substring("--code=".length());
			} else if (args[i].startsWith("--date=")) {
				handledArgs[i] = DataFormatter.parse(args[i].substring("--date=".length()), "yyyy-MM-dd", "dd-MM-yyyy");
			}
		}

		if (handledArgs[0].isEmpty() || handledArgs[1].isEmpty()) {
			throw new IllegalArgumentException("Не указан код валюты или дата.");
		}

		return handledArgs;
	}

	public static void main(String[] args) {
		String code = "", date = "";
		try {
			args = handleArgs(args);
			code = args[0];
			date = args[1];
		} catch (IllegalArgumentException e) {
			System.err.println(e.getMessage());
			return;
		} catch (DateTimeParseException e) {
			System.err.println("Дата указывается в формате YYYY-MM-DD");
			return;
		}

		RequestManager apiClient = new RequestManager();
		try {
			Rate currencyRate = apiClient.getCurrencyRate(code, date);
			if (currencyRate != null) {
				System.out.println(currencyRate.getName() + " (" + currencyRate.getCode() + "): " + currencyRate.getRate());
			} else {
				System.out.println("Курс валюты не найден.");
			}
		} catch (IOException | ParserConfigurationException | SAXException e) {
			System.err.println("Ошибка при получении курса валюты: " + e.getMessage());
		}
	}
}
