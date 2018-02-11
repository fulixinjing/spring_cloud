package com.creditloanservice.example.mapper;

import java.util.List;

import com.creditloanservice.common.MyMapper;
import com.creditloanservice.example.model.ContractTemp;


public interface ContractTempDao extends MyMapper<ContractTemp> {
	
    public ContractTemp selectByContractCode(String contractCode);
    
    public List<ContractTemp> selectAllInfo();
    
    public int updateByPrimaryKey(ContractTemp temp);
}