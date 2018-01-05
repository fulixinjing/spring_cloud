package cn.itcast.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.itcast.model.Login;

@RequestMapping("/login")
@Controller
public class LoginController {
	
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
	public String login(Login login){
		
		return "login";
	}
}
