package com.chj.sys;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.CommonAnnotationBeanPostProcessor;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import com.chj.util.CommonUtil;

/**
 * 登陆拦截器 验证用户是否登陆
 * @author flx
 *
 */
public class loginInterceptor implements HandlerInterceptor{


	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj) throws Exception {
			Object user = request.getSession().getAttribute(CommonUtil.LOGIN_TYPE);
			if(request.getRequestURL().indexOf("schedule-sys/login") !=-1){
				return true;
			}
			if(user == null){
				System.out.println(request.getContextPath());
				response.sendRedirect(request.getContextPath()+"/login");
				return false;
			}
		
			return true;
	}

	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object obj, Exception exp)
					throws Exception {
		
	}
	
	@Override
	public void postHandle(HttpServletRequest request, HttpServletResponse response,
			Object obj, ModelAndView mav) throws Exception {
		
	}
}
