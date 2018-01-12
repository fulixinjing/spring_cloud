package com.chj.pageUtil;

import java.util.ArrayList;
import java.util.List;

public class test {
	
	public static void main(String[] args) {
		Page<String> page = new Page<String>();
		
			List<String> a = new ArrayList<String>();
			for(int i=0;i<15;i++){
				a.add("aaa"+i);
			}
			 page.setCurrentPage(1);
			  page.setPageSize(5);
			 page.setRecordTotal(a.size());
			page.setContent(a);
			System.out.println(page);
	}
}
