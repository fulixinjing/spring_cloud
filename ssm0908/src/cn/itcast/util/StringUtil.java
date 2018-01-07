package cn.itcast.util;

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
}