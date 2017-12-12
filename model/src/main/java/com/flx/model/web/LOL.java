package com.flx.model.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flx.model.clms.Hero;
import com.flx.model.clms.HeroUtil;
import com.flx.model.clms.Iskill;
import com.flx.model.clms.SkillUtil;

@RequestMapping("/lol")
@Controller
public class LOL {

	
	@RequestMapping("/toLol")
	public String toLol(Model model){
		
		model.addAttribute("hero",HeroUtil.nameMap);
		model.addAttribute("skill",SkillUtil.nameMap);
		
		return "toLol";
	}
	@RequestMapping("/yxsx")
	public String yxsx(Model model,String yx,String jn) throws Exception{
		
		Hero hero = (Hero) HeroUtil.nameMap.get(yx).getClassName().newInstance();
		hero.setIskill((Iskill)SkillUtil.nameMap.get(jn).getClassName().newInstance());
		System.out.println(hero.getHeroName());
		hero.heroPrologue();
		hero.heroSkill();
		return "yxsx";
	}
}
