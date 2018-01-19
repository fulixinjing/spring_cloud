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
			 page.setCurrentPage(10);
			  page.setPageSize(3);
			 page.setRecordTotal(a.size());
			page.setList(a);
			//Pager [currentPage=1, pageSize=5, pageTotal=3, recordTotal=15, 
			//previousPage=1, nextPage=2, firstPage=1, lastPage=3, 
			//content=[aaa0, aaa1, aaa2, aaa3, aaa4, aaa5, aaa6, aaa7, aaa8, aaa9, aaa10, aaa11, aaa12, aaa13, aaa14]]

			System.out.println(page.getLimitSql());
	}
}
