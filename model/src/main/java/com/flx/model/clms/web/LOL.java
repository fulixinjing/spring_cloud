package com.flx.model.clms.web;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.flx.model.clms.Hero;
import com.flx.model.clms.HeroUtil;
import com.flx.model.clms.Iskill;
import com.flx.model.clms.SkillUtil;
import com.flx.staticHtml.StaticHtml;

/**策略模式
 * 策略模式的决定权在用户，系统本身提供不同算法的实现，新增或者删除算法，对各种算法做封装。
 * 因此，策略模式多用在算法决策系统中，外部用户只需要决定用哪个算法即可
 * @author flx
 */
@RequestMapping("/lol")
@Controller
public class LOL {

	
	@RequestMapping("/toLol")
	public String toLol(Model model){
		
		model.addAttribute("hero",HeroUtil.nameMap);
		model.addAttribute("skill",SkillUtil.nameMap);
		
		return "toLol";
	}
	@RequestMapping("/toLol1")
	public void toLol1(Model model){
		
		new StaticHtml().statusThread();
		
		
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
