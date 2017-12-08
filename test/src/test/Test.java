package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;



public class Test {
	public static void main(String[] args) {
	/*	BigDecimal auditAmount = new BigDecimal(66000.00).divide(new BigDecimal(1.10740000),2,BigDecimal.ROUND_HALF_UP);
		System.out.println(auditAmount);*/
		
		/*String aa="33333333;ssss";
		for(String a : aa.split(";")){
			System.out.println(a);
		}*/
	/*	List<String> a =new ArrayList<String>();
		a.add("ddd");
		a.add("cccc");
		System.out.println(a.toString());*/
	/*	String [][] fileNames =new String[][]{{"222","xxx"}};
		for(String[] fileName : fileNames){
			System.out.println(fileName[0]);
			System.out.println(fileName[1]);
		}*/
		String a =aa();
		
		System.out.println(a);
	}

	private static String aa() {
		String a="aaa";
		try{
			return a;
		}catch(Exception e){
			
		}finally{
			a="bbb";
			System.out.println(a);
		}
		System.out.println("ddd");
		return "ccc";
	}
}
