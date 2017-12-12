package com.flx.model.clms;

import java.util.HashMap;
import java.util.Map;

public enum SkillUtil {

	DIANRAN("点燃",DrSkill.class),
	
	SHANXIAN("闪现",SxSkill.class),
	
	ZHILIAO("治疗",ZlSkill.class);
	
	private String skillName;
	
	private Class className;
	
	public static Map<String,SkillUtil> nameMap = new HashMap<String,SkillUtil>();
	
	public static Map<Class,SkillUtil> classMap = new HashMap<Class, SkillUtil>();
	
	static{
		SkillUtil[] allHero = SkillUtil.values();
		for (SkillUtil hero :allHero) {
			nameMap.put(hero.getSkillName(), hero);
			classMap.put(hero.getClassName(), hero);
		}
	}
	
	
	private SkillUtil(String skillName,Class className) {
		this.skillName = skillName;
		this.className = className;
	}

	public String getSkillName() {
		return skillName;
	}

	public void setSkillName(String skillName) {
		this.skillName = skillName;
	}

	public Class getClassName() {
		return className;
	}

	public void setClassName(Class className) {
		this.className = className;
	}

	
	
}
