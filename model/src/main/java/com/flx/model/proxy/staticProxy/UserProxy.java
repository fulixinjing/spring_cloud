package com.flx.model.proxy.staticProxy;


/**
 * 代理类
 * @author flx
 *
 */
public class UserProxy implements UserInfo{
	
	private UserInfo info;
	
	public UserProxy(UserInfo info) {
		this.info = info;
	}

	@Override
	public void add() {
		System.out.println("代理类。。。。。。add");
		info.add();
	}

	@Override
	public void update() {
		
	}
}
