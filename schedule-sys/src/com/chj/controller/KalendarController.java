package com.chj.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * 日历
 * @author flx
 *
 */
@Controller
@RequestMapping("/kalendar")
public class KalendarController {

	
	@RequestMapping("/toKalendar")
	public String toKalendar(){
		
		return "kalendar";
	}
}
