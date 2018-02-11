package com.creditloanservice.example.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.creditloanservice.example.feigninterface.ITestFeign;
import com.creditloanservice.example.mapper.ContractTempDao;
import com.creditloanservice.example.model.ContractTemp;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;

@Service
public class ContractTempService  {

	@Autowired
	private ContractTempDao contractTempDao;
	
	@Autowired
	ITestFeign it;

	public String hiService(String name) {
		return it.sayHiFromClientOne(name);
	}

	public ContractTemp getInfo(String contractCode) {
		
		ContractTemp temp = contractTempDao.selectByContractCode(contractCode);
		System.out.print(temp.toString());
		return temp;
	}
	
	 public PageInfo<ContractTemp> getAll(ContractTemp temp) {
	        if (temp.getPage() != null && temp.getRows() != null) {
	            PageHelper.startPage(temp.getPage(), temp.getRows());
	        }
	        List<ContractTemp> list = contractTempDao.selectAllInfo();
	        return new PageInfo<ContractTemp>(list);
	 }
	 
	 @Transactional
	 public void testTransactionManagement(){
		 ContractTemp temp = new ContractTemp();
		 temp.setId("f806468835764973b64b8e837e654081");
		 temp.setPushNumber(11);
		 contractTempDao.updateByPrimaryKey(temp);
		 int i = 1/0;
	 }

	
}
