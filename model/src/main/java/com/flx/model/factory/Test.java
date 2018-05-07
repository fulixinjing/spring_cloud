package com.flx.model.factory;

public class Test {
	
	public static void main(String[] args) {
		ContractFactory contractFactory = null;
		contractFactory = new XYJFactory();
		Contract contract = contractFactory.getContract();
		contract.getMake();
		
			 
	}
}
