package calculations;

import java.math.BigDecimal;
import java.math.MathContext;

public class Operation {
	
	public double calculate(String operator, double number1, double number2) {
		BigDecimal result = new BigDecimal(0);
		if (operator.equals("+")) {
			result = BigDecimal.valueOf(number1).add(BigDecimal.valueOf(number2));
			return result.doubleValue();
		}
		if (operator.equals("-")) {
			result = BigDecimal.valueOf(number1).subtract(BigDecimal.valueOf(number2));
			return result.doubleValue();
		}
		if (operator.equals("x")) {
			result =  BigDecimal.valueOf(number1).multiply(BigDecimal.valueOf(number2));
			return result.doubleValue();
		}
		if (operator.equals("/")) {
			try {
				// MathContext.DECIMAL128 = IEEE 754R Decimal128 format, 34 digits (pour operations telles que 1/3...)
				result = BigDecimal.valueOf(number1).divide(BigDecimal.valueOf(number2), MathContext.DECIMAL128);
				return result.doubleValue();
			} catch (ArithmeticException e) {
				e.printStackTrace();
				return 0;
			}
			
		}
		return result.doubleValue();
	}
	
	public double calculateNumber2(boolean isOperator, double number1, double number2, String operator) {
		if (isOperator) {
			
			return this.calculate(operator, number2, number1);
		} else {
			return number1;
		}
	}
}
