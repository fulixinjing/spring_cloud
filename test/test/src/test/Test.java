package test;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;



public class Test {
	public static void main(String[] args) {
		  BigDecimal loanLimit=new BigDecimal(36);
		  BigDecimal loanRate =new BigDecimal(0.88).divide(new BigDecimal(100),8,BigDecimal.ROUND_HALF_UP);         // 借款利率
		BigDecimal _temp1 = (loanRate.add(new BigDecimal(1))).pow(loanLimit.intValue());//（1+月利率）^借款期限
		 BigDecimal _temp2 = _temp1.subtract(new BigDecimal(1));
		 BigDecimal auditMonthPaymoney = new BigDecimal(60310).multiply(new BigDecimal(0.0088)).multiply(_temp1).divide(_temp2,2,BigDecimal.ROUND_HALF_UP); 
		System.out.println(auditMonthPaymoney+"11");
	}
}
