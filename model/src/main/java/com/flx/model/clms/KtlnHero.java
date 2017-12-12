package com.flx.model.clms;

public class KtlnHero extends Hero {

	public KtlnHero() {
		super.heroName = HeroUtil.classMap.get(getClass()).getHeroName();
	}
	
	@Override
	public void heroPrologue() {
		System.out.println("以敌人之血，祭我大诺克萨斯!");
	}

}
