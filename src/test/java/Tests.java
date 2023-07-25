import freshqiwi.cbr.rts.app.CurrencyRateApp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

public class Tests {
	@ParameterizedTest
	@ValueSource(strings = {"USD", "KZT", "CNY"})
	public void testSuccess(String currency) {
		CurrencyRateApp.main(new String[]{"--code=" + currency, "--date=2022-10-08"});
	}

	@Test
	public void testNoCode() {
		CurrencyRateApp.main(new String[]{"--code=", "--date=2022-10-08"});
	}

	@Test
	public void testInvalidDateFormat() {
		CurrencyRateApp.main(new String[]{"--code=USD", "--date=25-07-2023"});
	}
}
