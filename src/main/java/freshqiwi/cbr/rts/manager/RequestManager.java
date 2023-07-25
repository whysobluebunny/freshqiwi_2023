package freshqiwi.cbr.rts.manager;

import freshqiwi.cbr.rts.dto.Rate;
import freshqiwi.cbr.rts.utils.XMLParser;
import io.restassured.RestAssured;
import io.restassured.response.Response;
import org.xml.sax.SAXException;

import javax.xml.parsers.ParserConfigurationException;
import java.io.IOException;

public class RequestManager {
	private static final String API_BASE_URL = "https://www.cbr.ru/scripts/XML_daily.asp";

	public Rate getCurrencyRate(String code, String date) throws IOException, ParserConfigurationException, SAXException {
		String url = API_BASE_URL + "?date_req=" + date;
		Response response = RestAssured.get(url);

		if (response.getStatusCode() == 200) {
			return XMLParser.parse(response.getBody().asPrettyString(), code);
		} else {
			System.err.println("Ошибка при получении курса валюты. Код ошибки: " + response.getStatusCode());
		}

		return null;
	}
}
