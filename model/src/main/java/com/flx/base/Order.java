package com.flx.base;

import java.util.Arrays;

public class Order {
	
	public static void main(String[] args) {
		int[] a = {32,43,12,32,28,54,99,34,56,0,35};
		//maopao(a);
		//xuanze(a);
		charu(a);
	}
	/**
	 * 插入排序
	 * 默认当前数组是有顺序的，新过来的数据，找到对应位置插入
	 * @param a
	 */
	private static void charu(int[] a) {
		  int i, j, insertNote;// 要插入的数据
	        for (i = 1; i < a.length; i++) {// 从数组的第二个元素开始循环将数组中的元素插入
	            insertNote = a[i];// 设置数组中的第2个元素为第一次循环要插入的数据
	            j = i - 1;
	            while (j >= 0 && insertNote < a[j]) {
	                a[j + 1] = a[j];// 如果要插入的元素小于第j个元素,就将第j个元素向后移动
	                j--;
	            }
	            a[j + 1] = insertNote;// 直到要插入的元素不小于第j个元素,将insertNote插入到数组中
	        }
	        System.out.println(Arrays.toString(a));
	}
	/**
	 * 选择排序
	 * 从第一个开始 循环判断后边的数据 如果比他小就交换
	 * @param a
	 */
	private static void xuanze(int[] a) {
		
		for(int i = 0; i<a.length-1;i++){
			for(int y =i;y<a.length;y++){
				if(a[i]>a[y]){
					int b=a[i];
					a[i]=a[y];
					a[y]=b;
				}
			}
		}
		System.out.println(Arrays.toString(a));
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
