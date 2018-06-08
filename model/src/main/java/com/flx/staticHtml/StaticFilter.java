package com.flx.staticHtml;

import java.io.IOException;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.stereotype.Component;

@Component
@ServletComponentScan
@WebFilter(urlPatterns = "/*",filterName = "StaticFilter")
public class StaticFilter implements Filter{

	@Override
	public void destroy() {
		
	}

	@Override
	public void doFilter(ServletRequest arg0, ServletResponse arg1,
			FilterChain arg2) throws IOException, ServletException {
		HttpServletRequest request =	(HttpServletRequest)arg0 ;
		HttpServletResponse response = (HttpServletResponse)arg1;
		String url =request.getRequestURL().toString(); 
		if(null!=request.getQueryString() && request.getQueryString().length()>0){
			url = url +"?"+request.getQueryString();
		}
		if(url.endsWith("toLol")){
			byte[] b= new StaticHtml().htmlByte.get("toLol");
			if(b!=null && b.length > 0){
			}else{
				new StaticHtml().statusThread();
			}
			b= new StaticHtml().htmlByte.get("toLol");
			response.getOutputStream().write(b);
		}else{
			arg2.doFilter(arg0, arg1);
		}
		System.out.println("vvvvvvvvvvvvvvvvvvvvv");
	}

	@Override
	public void init(FilterConfig arg0) throws ServletException {
		System.out.println("xxxxxxxxxxxxxxxxxxxxxx");
		
	}

}
