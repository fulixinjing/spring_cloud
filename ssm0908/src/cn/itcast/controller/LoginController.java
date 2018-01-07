package cn.itcast.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import cn.itcast.model.Login;
import cn.itcast.util.StringUtil;

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
	public String login(Login login,HttpServletRequest request,HttpServletResponse response,Model model){
		if(StringUtil.isEmpty(login.getUsername()) || StringUtil.isEmpty(login.getPassword())){
			
			model.addAttribute("message", "用户名密码不能为空");
			return "login";
		}
		return "index";
	}
}
