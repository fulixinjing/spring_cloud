package com.flx.model.annotation;

import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@RequestMapping("/annotation")
@Controller
public class AnnotationController {

	@Autowired
	AnnotationService annotationService;
	
	/**
	 * 切面 自定义注解
	 */
	@RequestMapping("/aspectTest")
	public void aspectTest(){
		annotationService.test();
	}
	/**
	 * 自定义注解 验证属性值
	 */
	@RequestMapping("/validateUser")
	@ResponseBody
	public void validateUser(User user){
		user.setAge(53);
		Map<String, Object> validate = ValidateUtil.validate(user);
		System.out.println(validate.get("message"));
	}
}
