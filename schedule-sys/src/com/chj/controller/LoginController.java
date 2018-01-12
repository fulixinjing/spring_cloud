package com.chj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.chj.model.Login;
import com.chj.service.LoginService;
import com.chj.util.CommonUtil;
import com.chj.util.StringUtil;

@RequestMapping("/login")
@Controller
public class LoginController {
	
	@Autowired
	private LoginService loginService;
	
	/**
	 * 跳转登陆页面
	 * @return
	 */
	@RequestMapping(value ="",method = {RequestMethod.GET})
	public String toLogin(){
		
		return "login";
	}
	/**
	 * 登陆 验证用户名密码
	 * @return
	 */
	@RequestMapping(value ="",method = {RequestMethod.POST})
	public String login(Login login,HttpServletRequest request,HttpServletResponse response,Model model){
		if(StringUtil.isEmpty(login.getUsername()) || StringUtil.isEmpty(login.getPassword())){
			
			model.addAttribute("message", "用户名密码不能为空");
			return "login";
		}
		login.setPassword(StringUtil.MD5String(login.getPassword()));//密码加密
		Login user = loginService.getUser(login);
		if(user == null || (!user.getPassword().equals(login.getPassword()))){
			model.addAttribute("message", "用户名或密码不正确");
			model.addAttribute("login",login);
			return "login";
		}else{
			request.getSession().setAttribute(CommonUtil.LOGIN_TYPE, user);
			return "index";
		}
		
	}
	/**
	 * 用户退出
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping("/quit")
	public String quit(HttpServletRequest request,HttpServletResponse response){
		
		request.getSession().removeAttribute(CommonUtil.LOGIN_TYPE);
		return "login";
	}
}
