package com.flx.model.proxy.moveProxy;

import com.flx.model.proxy.moveProxy.UserInfo;

/**
 * 目标类
 * @author flx
 *
 */
public class UserImpl implements UserInfo {

	@Override
	public void add() {
		System.out.println("非代理。。。。。add");
	}

	@Override
	public void update() {

	}

}
