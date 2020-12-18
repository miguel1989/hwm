package hwm.util;

import java.math.BigDecimal;
import java.math.RoundingMode;

public class BigDecimalUtils {
	//	public static final MathContext MATH_CTX = new MathContext(2, RoundingMode.CEILING);
	public static final Integer SCALE = 2;

	public static BigDecimal fromStr(String str) {
		return new BigDecimal(str).setScale(SCALE, RoundingMode.CEILING);
	}

	public static BigDecimal fromInt(Integer num) {
		return new BigDecimal(num).setScale(SCALE, RoundingMode.CEILING);
	}

	public static BigDecimal fromLong(Long num) {
		return new BigDecimal(num).setScale(SCALE, RoundingMode.CEILING);
	}

	public static BigDecimal sum(BigDecimal num1, BigDecimal num2) {
		return num1.add(num2);
	}

	public static BigDecimal subtract(BigDecimal num1, BigDecimal num2) {
		return num1.subtract(num2);
	}
}
