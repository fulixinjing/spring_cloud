package com.flx.model.factory;

public class NXJFactory implements ContractFactory{

	@Override
	public Contract getContract() {
		
		return new NXJContract();
	}

}
