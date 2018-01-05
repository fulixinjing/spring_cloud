package cn.taskSys.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;

public class StringUtil {
	

	/**
	 * 去掉空值
	 * 
	 * @param str
	 *            原值
	 * @return String 处理后值
	 */
	public static String nvlString(String str) {
		String s_temp = str;
		if (s_temp == null || s_temp.toUpperCase().equals("NULL")) {
			s_temp = "";
		}
		return s_temp;
	}

	/**
	 * 去掉空值
	 * 
	 * @param str
	 *            原值
	 * @return String 处理后值
	 */
	public static String nvlString(Object str) {
		String s_temp = "" + str;
		if (s_temp == null || s_temp.toUpperCase().equals("NULL")) {
			s_temp = "";
		}
		return s_temp;
	}
	
	public static String changHTMLNull(String str) {
		if (str == null || "".equals(str.trim()))
			str = "&nbsp;";
		else
			str = str.trim();
		return str;
	}


	/**
	 * 相当于oracle的nvl函数，如果输入不null或者不字符串的各种“null”后者空字符串，则返回输入，否则返回第2个参数
	 * 
	 * @param str
	 *            原值
	 * @param defu
	 *            缺省
	 * @return String 处理后值
	 */
	public static String nvlString(Object str, String defu) {
		String s_temp = str+"";
		if (s_temp == null || s_temp.toUpperCase().equals("NULL") || s_temp.length() == 0) {
			s_temp = defu;
		}
		return s_temp;
	}

	/**
	 * 相当于oracle的nvl函数，如果输入不null或者不字符串的各种“null”后者空字符串，则返回输入，否则返回第2个参数
	 * 
	 * @param str
	 *            原值
	 * @param defu
	 *            缺省
	 * @return String 处理后值
	 */
	public static String nvlString(String str, String defu) {
		String s_temp = str;
		if (s_temp == null || s_temp.toUpperCase().equals("NULL") || s_temp.length() == 0) {
			s_temp = defu;
		}
		return s_temp;
	}

	/**
	 * 将文件名中的汉字转为UTF8编码的串,以便下载时能正确显示另存的文件名. 纵横软件制作中心雨亦奇2003.08.01
	 * 
	 * @param s
	 *            原文件名
	 * @return 重新编码后的文件名
	 */
	public static String toISOString(String s) {
		String temp = s;
		try {
			temp = new String(temp.getBytes(), "ISO-8859-1");
		} catch (Exception e) {
		}
		return temp;
	}
	
	/**
	 * 数字型字符串转整形
	 * 
	 * @param String
	 * @return String
	 */
	public static int StringToInt(String str) {
		int i_temp = 0;
		try{
			i_temp = Integer.parseInt(nvlString(str));
		}catch(Exception e){

		}
		return i_temp;
	}
	/**
	 * 数字型字符串转长整形
	 * 
	 * @param String
	 * @return String
	 */
	public static long StringToLong(String str) {
		long i_temp = 0;
		try{
			i_temp = Long.parseLong(nvlString(str));
		}catch(Exception e){

		}
		return i_temp;
	}
	/**
	 * 数字型字符串转浮点型
	 * 
	 * @param String
	 * @return String
	 */
	public static float StringToFloat(String str) {
		float i_temp = 0;
		try{
			i_temp = Float.parseFloat(nvlString(str));
		}catch(Exception e){

		}
		return i_temp;
	}
	/**
	 * 数字型字符串转双精度浮点型
	 * 
	 * @param String
	 * @return String
	 */
	public static double StringToDouble(String str) {
		double i_temp = 0;
		try{
			i_temp = Double.parseDouble(nvlString(str));
		}catch(Exception e){

		}
		return i_temp;
	}
	/**
	 * 过滤sql注入字符
	 * 
	 * @param Exception
	 * @param Log
	 * @return void
	 */
	public static String sql_inj(String str) {
		if(str!=null && !"".equals(str)){
			String injStr = "and:exec:insert:select:delete:update:count:*:%:chr:mid:master:truncate:char:declare:;:or:-:+:,:'";
			String[] injStra = injStr.split(":");
			for (int i=0 ; i < injStra.length ; i++ ){
				while(str.indexOf(injStra[i])>=0){
					str = str.replace(injStra[i], "");
				}
			}
		}
		return str;
	}
	/**
	 * 功能：获取map中的String
	 * 
	 * @param value
	 *            参数
	 * @param req
	 *            参数集
	 * @return String 结果
	 */
	public static String getMapValue(String value, HttpServletRequest req) {
		if (req.getParameter(value) != null) {
			return "" + req.getParameter(value);
		}
		return "";
	}
	/**
	 * 功能：获取U03表的时间条件
	 * 描述：传入一个时间范围，获得U03表在该时间范围内有效单位的时间选择sql，表的别名是方便在关联查询时对U03表取别名的支持
	 * @param beginDate 有效时间
	 * @param endDate   失效时间
	 * @param tableAs   表别名
	 * @return String sql条件
	 */
	public static String getU03TimeCondition(String beginDate,String endDate,String tableAs) {
		if (beginDate == null || beginDate.equals("") || beginDate.length()<4) {
			beginDate = "299912";
		}
		if (endDate == null || endDate.equals("") || endDate.length()<4) {
			endDate = "200012";
		}
		if(beginDate.length() >= 4 && beginDate.length() < 6){
			beginDate = beginDate.substring(0,4)+"01";
		}
		if(endDate.length() >= 4 && endDate.length() < 6){
			endDate = endDate.substring(0,4)+"12";
		}
		beginDate = beginDate.substring(0,6);
		endDate = endDate.substring(0,6);
		if (tableAs != null && !tableAs.equals("") && tableAs.length()>0) {
			tableAs += ".";
		}
		return " nvl("+tableAs+"begin_date,'200012')<='"+endDate+"' and nvl("+tableAs+"end_date,'999912')>='"+beginDate+"'";
	}
	
	/**
	 * 显示 获取系统时间
	 * 
	 * @param value
	 * @return String
	 */
	public static String getSystemDate() {
		Date date = new Date();
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		String sysdate = sdf.format(date);
		return sysdate;
	}

	/**
	 * 显示 根据传入格式获取系统时间
	 * 
	 * @param value
	 * @return String
	 */
	public static String getSystemDate(String format) {
		Date date = new Date();
		String sysdate = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(format);
			sysdate = sdf.format(date);
		} catch (Exception e) {
			sysdate = "时间格式错误";
		}
		return sysdate;
	}
	/**
	 * 显示 传入时间增加制定月份
	 * 
	 * @param value
	 * @return String
	 */
	public static String addMonths(String inputFormat,String outputFormat,String inputTime,int addMonth) {
		Calendar calendar = Calendar.getInstance();
		String sysdate = "";
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(inputFormat);
			calendar.setTime(sdf.parse(inputTime));
			calendar.add(Calendar.MONTH, addMonth);
			sdf.applyPattern(outputFormat);
			sysdate = sdf.format(calendar.getTime());
		} catch (Exception e) {
			sysdate = "时间格式错误";
		}
		return sysdate;
	}
	
	/**
	 * 将数字转换为相应的中文
	 * 
	 * @return
	 */
	public static String changeNumToCnLing(String num) {
		StringBuffer cn = new StringBuffer();
		int numLength = num.length();
		char temp = '0';
		for (int i = 0; i < numLength; i++) {
			temp = num.charAt(i);
			switch (temp) {
			case '0':
				cn.append("零");
				break;
			case '1':
				cn.append("一");
				break;
			case '2':
				cn.append("二");
				break;
			case '3':
				cn.append("三");
				break;
			case '4':
				cn.append("四");
				break;
			case '5':
				cn.append("五");
				break;
			case '6':
				cn.append("六");
				break;
			case '7':
				cn.append("七");
				break;
			case '8':
				cn.append("八");
				break;
			case '9':
				cn.append("九");
				break;
			default:
				break;
			}
		}
		return cn.toString();
	}
	/**
	 * 地道一个字符串的长度，显示的长度，一个汉字或日韩文长度为2，英文字符长度为1
	 * 
	 * @return
	 */
	public static int length(String s){
		if(s == null){
			return 0 ;
		}
		char[] c = s.toCharArray();
		int len = 0;
		int length = c.length;
		for(int i = 0; i < length; i++){
			len++;
			if(!isLetter(c[i])){
				len++;
			}
		}
		return len;
	}
	/**
	 * 判断一个字符是Ascill字符还是其他字符（如：汉，日，韩文字符）
	 * 
	 * @return
	 */
	public static boolean isLetter(char c){
		int k = 0x80;
		return c / k == 0 ? true : false ;
	}
	
	/**
	 * 
	 */
	public static boolean isNUll(String str){
		if (str==null || str.equals("")) {
			return true;
		}
		return false;
	}
}
