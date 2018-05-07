package com.flx.model.factory;

public class XYJFactory implements ContractFactory{

	@Override
	public Contract getContract() {
		
		return new XYJContract();
	}

}
