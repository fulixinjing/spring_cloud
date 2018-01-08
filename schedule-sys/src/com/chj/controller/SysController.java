package com.chj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 系统管理
 * @author flx
 *
 */
@Controller
@RequestMapping("/sys")
public class SysController {
	
	@RequestMapping("/userList")
	public String userList(){
		
		return "/sys/user_list";
	}
}
