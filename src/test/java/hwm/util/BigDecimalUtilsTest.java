package hwm.util;

import org.junit.jupiter.api.Test;

import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class BigDecimalUtilsTest {

	@Test
	public void simpleSum() {
		BigDecimal result = BigDecimalUtils.sum(BigDecimalUtils.fromStr("12.00"), BigDecimalUtils.fromStr("13.0"));
		assertEquals("25.00", result.toString());

		result = BigDecimalUtils.sum(BigDecimalUtils.fromStr("2.13"), BigDecimalUtils.fromStr("4.56"));
		assertEquals("6.69", result.toString());

		result = BigDecimalUtils.sum(BigDecimalUtils.fromInt(2), BigDecimalUtils.fromInt(3));
		assertEquals("5.00", result.toString());
	}

	@Test
	public void addIni() {
		//add 5% ini to the base "12" ini
		BigDecimal result = BigDecimalUtils.addInitiative(12, 5);
		assertEquals("12.6", result.toString());

		result = BigDecimalUtils.addInitiative(12, 10);
		assertEquals("13.2", result.toString());

		result = BigDecimalUtils.addInitiative(16, 37);
		assertEquals("21.9", result.toString());
	}
}
