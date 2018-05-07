package com.flx.model.factory;

public class CCJFactory implements ContractFactory {

	@Override
	public Contract getContract() {
		
		return new CCJContract();
	}

}
