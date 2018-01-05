package cn.taskSys.filter;

import java.io.IOException;
import java.util.HashMap;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class SessionFilter implements Filter{
	 
		public HashMap<String,Object> tablemap=new HashMap<String,Object>();
	
	
		public SessionFilter() {
			// TODO 自动生成的构造函数存根
		}
	
	    public void doFilter(ServletRequest req, ServletResponse res, FilterChain chain) throws IOException, ServletException {  
	           HttpServletRequest request=(HttpServletRequest)req;     
	           HttpServletResponse response  =(HttpServletResponse) res;      
	           HttpSession session = request.getSession(true);       
	            
	           if (request.getRequestURI().startsWith(
	        		   request.getContextPath() + "/login2.jsp")
	                || request.getRequestURI().startsWith(
	                		request.getContextPath() + "/login.do")
	                || request.getRequestURI().startsWith(
	                		request.getContextPath() + "/ssoLogin.do")
	                || request.getRequestURI().equals(request.getContextPath() + "/")
	                    ) {
	        	   chain.doFilter(request, response);
	           }else{
	        	   String url=request.getRequestURI();  
	        	   if(session!=null && session.getAttribute("JX_USERINFO")!=null) { 
	        		   chain.doFilter(req, res); 
	        	   }     
	           		else
	           	   {
		           	   String path = request.getContextPath();
		           	   response.sendRedirect(request.getContextPath() + "/login2.jsp");	           	   
	           	   }
	           }
	    }  
	    public void init(FilterConfig arg0) throws ServletException { 
	    	
	    } 
	    public void destroy() {  

	    } 
	    
}
