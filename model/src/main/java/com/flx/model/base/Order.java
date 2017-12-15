package com.flx.model.base;

import java.util.Arrays;

public class Order {
	
	public static void main(String[] args) {
		int[] a = {32,43,12,32,28,54,99,34,56,0,35};
		maopao(a);
	}
	
	/**
	 * 冒泡排序
	 * @param a
	 */
	private static void maopao(int[] a) {
		int b = 0;
		for(int j = 0 ;j < a.length-1; j++){
			
			for(int x = 0; x < a.length - 1 ; x++){
				
				if(a[x] > a[x+1]){
					b = a[x];
					a[x] = a[x+1];
					a[x+1] = b;
				}
			}
		}
		System.out.println(Arrays.toString(a));
	}
}
