package com.flx.model;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("layui")
public class LayUIController {
	
	@RequestMapping("index")
	public String layui(){
		
		return "layui/index";
	}
}
