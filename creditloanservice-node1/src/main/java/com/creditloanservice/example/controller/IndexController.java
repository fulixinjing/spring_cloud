package com.creditloanservice.example.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.creditloanservice.example.model.ContractTemp;
import com.creditloanservice.example.service.ContractTempService;
import com.github.pagehelper.PageInfo;

@RestController
@RequestMapping
public class IndexController {
	@Autowired
	private ContractTempService service;
	
	@Value("${server.port}")
	String port;

	@RequestMapping("/hi")
	public String home(@RequestParam String name) {
		return "hi " + name + ",i am from port:" + port;
	}
	
	/**
	 * 调用node2服务的hi
	 * @param name
	 * @return
	 */
	@RequestMapping(value = "/testhi")
	public String hi(@RequestParam String name) {
		return service.hiService(name);
	}
	
    @RequestMapping
    public String index(Model model) {
        //return "index";
    	return "welcome";
    }
    
    @ResponseBody
    @RequestMapping(value = "/test",method = {RequestMethod.GET})
    public ContractTemp test(Model model){
    	ContractTemp temp = service.getInfo("00100301740-2");
        return temp;
    }
    
    
    @ResponseBody
    @RequestMapping(value = "/testPage",method = {RequestMethod.GET})
    public PageInfo<ContractTemp> testPage(Model model){
    	ContractTemp temp =  new ContractTemp();
        return service.getAll(temp);
    }
    

    @RequestMapping(value = "/testTransactionManagement",method = {RequestMethod.GET})
    public void testTransactionManagement(Model model){
    	service.testTransactionManagement();
    }
}
