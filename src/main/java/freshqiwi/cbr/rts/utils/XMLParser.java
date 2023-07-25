package freshqiwi.cbr.rts.utils;

import freshqiwi.cbr.rts.dto.Rate;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

public class XMLParser {
	// хотел я красиво попользоваться xml path у restAssured'a, но ребята решили выдумать свой собственный синтаксис, который еле гуглится
	// и, судя по всему, мог успеть обновиться
	// поэтому вот костыль
	public static Rate parse(String content, String keyWord) throws IOException, SAXException, ParserConfigurationException {
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		factory.setValidating(true);
		factory.setIgnoringElementContentWhitespace(true);
		DocumentBuilder builder = factory.newDocumentBuilder();
		builder.setErrorHandler(null);
		Document document = builder.parse(new ByteArrayInputStream(content.getBytes(StandardCharsets.UTF_8)));
		NodeList currencyNodes = document.getElementsByTagName("Valute");
		for (int i = 0; i < currencyNodes.getLength(); i++) {
			Element currencyElement = (Element) currencyNodes.item(i);
			String currencyCode = currencyElement.getElementsByTagName("CharCode").item(0).getTextContent();
			if (currencyCode.equals(keyWord)) {
				// учитываем номинал, а то где-то цена за 1, а где-то за 100
				String nominal = currencyElement.getElementsByTagName("Nominal").item(0).getTextContent();
				String currencyName = currencyElement.getElementsByTagName("Name").item(0).getTextContent();
				String rateStr = currencyElement.getElementsByTagName("Value").item(0).getTextContent().replace(",", ".");
				double rate = Double.parseDouble(rateStr) / Integer.parseInt(nominal);
				Rate currencyRate = new Rate();
				currencyRate.setCode(currencyCode);
				currencyRate.setName(currencyName);
				currencyRate.setRate(rate);
				return currencyRate;
			}
		}
		return null;
	}
}
