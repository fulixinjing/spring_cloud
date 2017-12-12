package com.flx.model.clms;

import java.util.HashMap;
import java.util.Map;

public enum HeroUtil {

	DMXY("德玛西亚之力",DmxyHero.class),
	
	NKSS("诺克萨斯之手",NkssHero.class),
	
	AYLS("暗夜猎手",AylsHero.class),
	
	KTLN("卡特琳娜",KtlnHero.class);
	
	private String heroName;
	
	private Class className;
	
	public static Map<String,HeroUtil> nameMap = new HashMap<String,HeroUtil>();
	
	public static Map<Class,HeroUtil> classMap = new HashMap<Class, HeroUtil>();
	
	static{
		HeroUtil[] allHero = HeroUtil.values();
		for (HeroUtil hero :allHero) {
			nameMap.put(hero.getHeroName(), hero);
			classMap.put(hero.getClassName(), hero);
		}
	}
	
	
	private HeroUtil(String heroName,Class className) {
		this.heroName = heroName;
		this.className = className;
	}

	public String getHeroName() {
		return heroName;
	}

	public void setHeroName(String heroName) {
		this.heroName = heroName;
	}

	public Class getClassName() {
		return className;
	}

	public void setClassName(Class className) {
		this.className = className;
	}

	
	
}
