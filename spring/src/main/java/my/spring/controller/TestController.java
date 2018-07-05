package my.spring.controller;

import my.spring.annotation.Autowired;
import my.spring.annotation.Controller;
import my.spring.annotation.RequestMapping;
import my.spring.service.TestService;

@Controller
@RequestMapping("/test")
public class TestController {

	@Autowired
	private TestService service;
	
	@RequestMapping("/get")
	public void get(){
		
		String s = service.get();
		System.out.println(s);
	}

}
