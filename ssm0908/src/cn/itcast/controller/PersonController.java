package cn.itcast.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.ServletRequestDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;

import cn.itcast.model.Person;
import cn.itcast.service.PersonService;

@Controller
@RequestMapping("/person")
public class PersonController {
	
	@Autowired
	private PersonService personService;
	
	/**
	 * 保存person
	 * @author:任亮
	 * @email:renliang@itcast.com
	 * @company：cn.itcast
	 */
	@RequestMapping("/save.do")
	public String save(Person person){
		personService.savePerson(person);
		return "redirect:selectPersonByCondition.do";
	}
	
	/**
	 * 预添加
	 * @author:任亮
	 * @email:renliang@itcast.com
	 * @company：cn.itcast
	 */
	@RequestMapping("/preSave.do")
	public String preSave(){
		return "save";
	}
	/**
	 * 根据Id查询person
	 * 
	 * @author:任亮
	 * @email:renliang@itcast.com
	 * @company：cn.itcast
	 */
	@RequestMapping("/selectPersonById.do")
	public String selectPersonById(Integer personId, Model model){
		Person person = personService.selectPersonById(personId);
		model.addAttribute("person", person);
		return "update";
	}
	
	@RequestMapping("/update.do")
	public String update(Person person){
		personService.updatePerson(person);
		return "redirect:selectPersonByCondition.do";
	}
	
	/**
	 * 批量的删除
	 * @author:任亮
	 * @email:renliang@itcast.com
	 * @company：cn.itcast
	 */
	@RequestMapping("/deleteBatch.do")
	public String deleteBatch(String ids){
		Map<String, Object> map = new HashMap<String, Object>();
		String [] idsArr = {};
		if(ids != null && !"".equals(ids)){
			idsArr = ids.split(",");
		}
		map.put("ids", idsArr);
		personService.deleteBatch(map);
		return "redirect:selectPersonByCondition.do";
	}
	
	/**
	 * 按条件组合查询
	 * @author:任亮
	 * @email:renliang@itcast.com
	 * @company：cn.itcast
	 */
	@RequestMapping("/selectPersonByCondition.do")
	public String selectPersonByCondition(String name, Date birthday, String personAddr, String gender, Model model){
		if("".equals(name)){
			name = null;
		}
		if("".equals(personAddr)){
			personAddr = null;
		}
		if("".equals(gender)){
			gender = null;
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("name", name);
		map.put("birthday", birthday);
		map.put("personAddr", personAddr);
		map.put("gender", gender);
		List<Person> pList = personService.selectPersonByCondition(map);
		model.addAttribute("pList", pList);
		model.addAttribute("name", name);
		model.addAttribute("birthday", birthday);
		model.addAttribute("personAddr", personAddr);
		model.addAttribute("gender", gender);
		return "list";
		
	}
	
	@InitBinder
	public void initBinder(ServletRequestDataBinder binder){
		binder.registerCustomEditor(Date.class, new CustomDateEditor(new SimpleDateFormat("yyyy-MM-dd"), true));
	}

}
