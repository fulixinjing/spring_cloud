package cn.taskSys.utils;

import java.io.UnsupportedEncodingException;

import javax.servlet.http.HttpServletRequest;

import cn.taskSys.entity.User;


public class PublicUtil {
	
		public static User getUserOnline(HttpServletRequest req){
		User user=(User) req.getSession().getAttribute("JX_USERINFO");
		return user;
	}
	 public static String enCodeStr(String str) {  
		         try {  
		          return new String(str.getBytes("iso-8859-1"), "UTF-8");  
		         } catch (UnsupportedEncodingException e) {  
		             e.printStackTrace();  
		             return null;  
		         }  
		     }  

	

}
