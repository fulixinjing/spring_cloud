package com.chj.util;

import java.security.MessageDigest;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 字符串工具类
 * @author flx
 */
public class StringUtil {
	
	public static boolean isEmpty(String str) {
		if (null == str)
			return true;
		if ("".equals(str))
			return true;
		if ("".equals(str.trim()))
			return true;
		if ("null".equals(str))
			return true;
		if ("NULL".equals(str))
			return true;
		return false;
	}
	
	public static boolean notEmpty(String str) {
		if (null == str)
			return false;
		if ("".equals(str))
			return false;
		if ("".equals(str.trim()))
			return false;
		if ("null".equals(str))
			return false;
		if ("NULL".equals(str))
			return false;
		return true;
	}
	public static String MD5String(String inStr){

		MessageDigest md5 = null;
		try{
			md5 = MessageDigest.getInstance("MD5");
		}catch (Exception e){
			System.out.println(e.toString());
			e.printStackTrace();
			return "";
		}
		char[] charArray = inStr.toCharArray();
		byte[] byteArray = new byte[charArray.length];

		for (int i = 0; i < charArray.length; i++)
			byteArray[i] = (byte) charArray[i];
		byte[] md5Bytes = md5.digest(byteArray);
		StringBuffer hexValue = new StringBuffer();
		for (int i = 0; i < md5Bytes.length; i++){
			int val = ((int) md5Bytes[i]) & 0xff;
			if (val < 16)
				hexValue.append("0");
			hexValue.append(Integer.toHexString(val));
		}
		return hexValue.toString();

	}
	public static void main(String[] args) {
		System.out.println(MD5String("123"));
	}
}