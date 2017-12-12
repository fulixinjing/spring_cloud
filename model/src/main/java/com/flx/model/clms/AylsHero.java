package com.flx.model.clms;

public class AylsHero extends Hero{

	public AylsHero() {
		super.heroName = "暗夜猎手";
	}
	
	@Override
	public void heroPrologue() {
		System.out.println("想攻击我，先试试和影子玩拳击吧");
	}

}
