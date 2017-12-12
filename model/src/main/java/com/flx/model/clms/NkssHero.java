package com.flx.model.clms;

public class NkssHero extends Hero {

	public NkssHero() {
		super.heroName= "诺克萨斯之手";
	}
	@Override
	public void heroPrologue() {
		System.out.println("不要用背朝着我，否则就当心你的脑袋！");
	}

}
