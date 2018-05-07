package com.flx.dubbo;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.dubbo.config.annotation.Reference;
import com.flx.api.DubboService1;

@RestController
@RequestMapping("/dubbo")
public class DubboTest {
	@Reference(version = "1.0.1")
	private DubboService1 dubboService1;
	/**
	 * 报错:interface dubboService is not visible from class loader
	 * 原因：项目采用热部署 注释掉pom热部署
	 */
	
	@RequestMapping("/test")
	public void test(){
		
		 String test = dubboService1.hello("我是客户端。。。model");
		 System.out.println(test);
	}
	
}
