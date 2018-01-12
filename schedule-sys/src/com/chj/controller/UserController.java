package com.chj.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chj.model.Login;
import com.chj.service.UserService;
import com.chj.util.CommonUtil;
import com.chj.util.StringUtil;

@Controller
@RequestMapping("/user")
public class UserController {
	
	@Autowired
	private UserService userService;

	/**
	 * 校验修改  密码
	 * @param password
	 * @return
	 */
	@ResponseBody
	@RequestMapping("checkPassword")
	public String checkPassword(HttpServletRequest request,HttpServletResponse response,String password,String newPassword){
		
		Login login = (Login) request.getSession().getAttribute(CommonUtil.LOGIN_TYPE);
		if(StringUtil.MD5String(password).equals(login.getPassword())){
			
			Login user =new Login();
			user.setId(login.getId());
			user.setPassword(StringUtil.MD5String(newPassword));//新密码
			userService.updateUser(user);//修改密码
			request.getSession().removeAttribute(CommonUtil.LOGIN_TYPE);//删除session登陆用户
			return "true";
		}
		return "false";
	}
}
