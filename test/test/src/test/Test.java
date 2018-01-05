package test;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeSet;



public class Test {
	public static void main(String[] args) {
		 /* BigDecimal loanLimit=new BigDecimal(36);
		  BigDecimal loanRate =new BigDecimal(0.88).divide(new BigDecimal(100),8,BigDecimal.ROUND_HALF_UP);         // 借款利率
		BigDecimal _temp1 = (loanRate.add(new BigDecimal(1))).pow(loanLimit.intValue());//（1+月利率）^借款期限
		 BigDecimal _temp2 = _temp1.subtract(new BigDecimal(1));
		 BigDecimal auditMonthPaymoney = new BigDecimal(60310).multiply(new BigDecimal(0.0088)).multiply(_temp1).divide(_temp2,2,BigDecimal.ROUND_HALF_UP); 
		System.out.println(auditMonthPaymoney+"11");*/
		/*List<Map<String,String>> list =new ArrayList<Map<String,String>>();
		for(int i = 0 ; i<10 ; i++){
			Map<String,String> map =new HashMap<String, String>();
			map.put("a"+i, "map"+i+"i");
			list.add(map);
		}
		Iterator iterator = list.iterator();
		while(iterator.hasNext()){
			Map<String, String> m = (Map<String, String>) iterator.next();
			Set<String> keySet = m.keySet();
			for(String key : keySet){
				System.out.println(m.get(key));
			}
		}
		Map<String,String> map =new HashMap<String, String>();
		map.put("a1", "map12i");
		System.out.println(list.toArray());*/
	/*	Set set =new TreeSet();
		for(int i = 0 ; i<10 ; i++){
		
			set.add("a"+i);
		}
		set.add("a3");
		System.out.println(set);
		System.out.println(new TreeSet(set));*/
		/*String a="abc";
		String b=new String("abc");
		System.out.println(b.replace("bc", "ssss"));*/
		
		char[] a = {'a','v','d','v'};
		System.out.println(new String(a));
	}
}
