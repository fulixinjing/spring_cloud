package com.flx.model.clms;

/**
 * 英雄父类
 * @author flx
 *
 */
public abstract class Hero {
	
	//英雄名字
	public String heroName;
	//召唤师技能
	public Iskill iskill;
	//英雄开场白 
	public abstract void heroPrologue();
	//召唤师技能
	public void heroSkill(){
		iskill.skillValue();
	}
	

	public String getHeroName() {
		return heroName;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public Iskill getIskill() {
		return iskill;
	}

	public void setIskill(Iskill iskill) {
		this.iskill = iskill;
	}

}
