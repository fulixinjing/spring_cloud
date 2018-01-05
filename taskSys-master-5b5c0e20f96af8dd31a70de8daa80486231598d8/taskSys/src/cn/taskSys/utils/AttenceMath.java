/**
 * AttenceMath
 * @author : ruiy
 * @date   : 2016-6-23下午1:08:47
 * 
 */
package cn.taskSys.utils;

import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import cn.taskSys.entity.PersonAttence;

public class AttenceMath {
	
	
	
	
	//考勤计算  按时间点计算方案
	public static Map<String , Object> modifyAttence(int remarkFalg,Date morning,Date afternoon,PersonAttence personAttence) throws Exception{
		Map<String , Object> maps = new HashMap<String, Object>();
		
		DecimalFormat df   = new DecimalFormat("#.0");
		SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm" );
		
		Date gbgoodmoring9 = sdf.parse("09:00");
		Date gbgoodmoring9h = sdf.parse("09:30");
		Date gbgoodmoring10 = sdf.parse("10:00");
		Date gbafternoon12 = sdf.parse("12:00");
		Date gbafternoon13h = sdf.parse("13:30");
		Date gbafternoon17 = sdf.parse("17:00");
		Date gbafternoon18 = sdf.parse("18:00");
		Date gbafternoon18h = sdf.parse("18:30");
		Date gbafternoon20 = sdf.parse("20:00");
		Date gbafternoon21 = sdf.parse("21:00");
		
		/*if(remarkFalg==1){
			Date gbgoodmoring8 = sdf.parse("08:30");
			Date gbafternoon17h = sdf.parse("17:30");
		}else if(remarkFalg==2){
			gbgoodmoring9 = sdf.parse("09:00");
			gbafternoon18 = sdf.parse("18:00");
		}else{
			gbgoodmoring9h = sdf.parse("09:30");
			gbafternoon18h = sdf.parse("18:30");
		}*/
		//工作日---加班时
		String wo = null;
		if(afternoon.getTime()>gbafternoon18h.getTime()){
			double workTime = afternoon.getTime()-gbafternoon18h.getTime();
			double workTime1 = workTime/1000/60/60;
			double workTime2 = Double.parseDouble(df.format(workTime1));
			String ss = new String(workTime2+"");
			String bb = ss.substring(0, 1);
			String cc= ss.substring(2, 3);
			int dd = Integer.parseInt(cc);
			if(dd>=0&&dd<5){
				 wo = bb+"."+"0";
			}else if(dd>=5&&dd<=9){
				 wo = bb+"."+"5";
			}
		}
		//公休日---加班时
		double weekTime = afternoon.getTime()-morning.getTime();
		double weekTime1 = weekTime/1000/60/60;
		double weekTime2 = Double.parseDouble(df.format(weekTime1));
//修改操作的情况		
		//====================1.早上打卡正常=======================================
		if((morning.getTime()<=gbgoodmoring9.getTime())||(morning.getTime()<=gbgoodmoring9h.getTime())){
		//if((morning.getTime()<=gbgoodmoring9.getTime())||(morning.getTime()<=gbgoodmoring9h.getTime())){
			//加班到21点
			if(afternoon.getTime()>=gbafternoon21.getTime()){
				maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
				maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
				maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
				maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
				maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
				//加班到20点
			}else if(afternoon.getTime()>=gbafternoon20.getTime()){
				maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
				maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
				maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
				maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
				maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+0);
				//未加班，正常打卡18:30
			}else if(afternoon.getTime()>=gbafternoon18h.getTime()){
				maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
				//未加班，18:00打卡
			}else if(afternoon.getTime()>=gbafternoon18.getTime()){
				//如果早上09:00打卡正常出勤
				if(morning.getTime()<=gbgoodmoring9.getTime()){
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
					//如果早上09:30打卡    早退
				}else{
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
					maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
				}
				//大于17:00算一天  早退
			}else if(afternoon.getTime()>=gbafternoon17.getTime()){
				maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
				maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
				//12点 之后打卡
			}else if(afternoon.getTime()>=gbafternoon12.getTime()){
				maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
			}else{
				maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0);
			}
			//=====================2.早上迟到打卡==================	
		}else if(morning.getTime()<=gbgoodmoring10.getTime()){
			//加班到21点
			if(afternoon.getTime()>=gbafternoon21.getTime()){
				maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
				maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
				maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
				maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
				maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
				maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
				//加班到20点
			}else if(afternoon.getTime()>=gbafternoon20.getTime()){
				maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
				maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
				maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
				maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
				maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+0);
				maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
				//未加班，正常打卡17:00
			}else if(afternoon.getTime()>=gbafternoon17.getTime()){
				maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
				maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
				//未加班，12:00之后打卡
			}else if(afternoon.getTime()>=gbafternoon12.getTime()){
				maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
				maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
			}else{
				maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0);
			}
		}else{
			if(morning.getTime()<=gbafternoon13h.getTime()){
				//加班到21点
				if(afternoon.getTime()>=gbafternoon21.getTime()){
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
					maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
					maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
					maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
					//加班到20点
				}else if(afternoon.getTime()>=gbafternoon20.getTime()){
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
					maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
					maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
					maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+0);
					//未加班，正常打卡18:00
				}else if(afternoon.getTime()>=gbafternoon18.getTime()){
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
					//未加班，17:00之后打卡  早退
				}else if(afternoon.getTime()>=gbafternoon17.getTime()){
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
					maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
				}else{
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0);
				}
			}else{
				//第一次打卡时间18:00之前
				if((morning.getTime()<=gbafternoon18.getTime())&&weekTime2>=4){
					//加班到21点
					if(afternoon.getTime()>=gbafternoon21.getTime()){
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
						maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
						maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
						maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
						maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
						//加班到20点
					}else if(afternoon.getTime()>=gbafternoon20.getTime()){
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
						maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
						maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
						maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
						maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+0);
						//18:30点
					}else if(afternoon.getTime()>=gbafternoon18h.getTime()){
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
						//18:00点
					}else if(afternoon.getTime()>=gbafternoon18.getTime()){
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
						maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
					}else{
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0);
					}
				}else{
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0);
				}
			}
		}
		return maps;
	}
	//考勤计算  按时间点计算方案
	public static Map<String , Object> addAttence(int remarkFalg,Date morning,Date afternoon,PersonAttence personAttence) throws Exception{
		Map<String , Object> maps = new HashMap<String, Object>();
		
		DecimalFormat df   = new DecimalFormat("#.0");
		SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm" );
		
		Date gbgoodmoring9 = sdf.parse("09:00");
		Date gbgoodmoring9h = sdf.parse("09:30");
		Date gbgoodmoring10 = sdf.parse("10:00");
		Date gbafternoon12 = sdf.parse("12:00");
		Date gbafternoon13h = sdf.parse("13:30");
		Date gbafternoon17 = sdf.parse("17:00");
		Date gbafternoon18 = sdf.parse("18:00");
		Date gbafternoon18h = sdf.parse("18:30");
		Date gbafternoon20 = sdf.parse("20:00");
		Date gbafternoon21 = sdf.parse("21:00");
		
		/*if(remarkFalg==1){
			Date gbgoodmoring8 = sdf.parse("08:30");
			Date gbafternoon17h = sdf.parse("17:30");
		}else if(remarkFalg==2){
			gbgoodmoring9 = sdf.parse("09:00");
			gbafternoon18 = sdf.parse("18:00");
		}else{
			gbgoodmoring9h = sdf.parse("09:30");
			gbafternoon18h = sdf.parse("18:30");
		}*/
		//工作日---加班时
		String wo = null;
		if(afternoon.getTime()-gbafternoon18h.getTime()>0){
			double workTime = afternoon.getTime()-gbafternoon18h.getTime();
			double workTime1 = workTime/1000/60/60;
			double workTime2 = Double.parseDouble(df.format(workTime1));
			String ss = new String(workTime2+"");
			String bb = ss.substring(0, 1);
			String cc= ss.substring(2, 3);
			int dd = Integer.parseInt(cc);
			if(dd>=0&&dd<5){
				 wo = bb+"."+"0";
			}else if(dd>=5&&dd<=9){
				 wo = bb+"."+"5";
			}
		}
		//公休日---加班时
		double weekTime = afternoon.getTime()-morning.getTime();
		double weekTime1 = weekTime/1000/60/60;
		double weekTime2 = Double.parseDouble(df.format(weekTime1));
//添加操作的情况		
		//====================1.早上打卡正常=======================================
		if((morning.getTime()<=gbgoodmoring9.getTime())||(morning.getTime()<=gbgoodmoring9h.getTime())){
			//加班到21点
			if(afternoon.getTime()>=gbafternoon21.getTime()){
				maps.put("pAttenceDay", 1);
				maps.put("pAttenceTime", Double.parseDouble(wo));
				maps.put("pAttenceWithoutPay", 50);
				maps.put("pMealPay", 25);
				maps.put("pTrafficPay", 25);
				maps.put("pAttenceChangeDay",0);
				maps.put("pAttenceUnpunctualTime",0);
				//加班到20点
			}else if(afternoon.getTime()>=gbafternoon20.getTime()){
				maps.put("pAttenceDay", 1);
				maps.put("pAttenceTime", Double.parseDouble(wo));
				maps.put("pAttenceWithoutPay", 25);
				maps.put("pMealPay", 25);
				maps.put("pTrafficPay", 0);
				maps.put("pAttenceChangeDay",0);
				maps.put("pAttenceUnpunctualTime",0);
				//未加班，正常打卡18:30
			}else if(afternoon.getTime()>=gbafternoon18h.getTime()){
				maps.put("pAttenceDay", 1);
				maps.put("pAttenceTime", 0);
				maps.put("pAttenceWithoutPay", 0);
				maps.put("pMealPay", 0);
				maps.put("pTrafficPay", 0);
				maps.put("pAttenceChangeDay",0);
				maps.put("pAttenceUnpunctualTime",0);
				//未加班，18:00打卡
			}else if(afternoon.getTime()>=gbafternoon18.getTime()){
				//如果早上09:00打卡正常出勤
				if(morning.getTime()<=gbgoodmoring9.getTime()){
					maps.put("pAttenceDay", 1);
					maps.put("pAttenceTime", 0);
					maps.put("pAttenceWithoutPay", 0);
					maps.put("pMealPay", 0);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
					//如果早上09:30打卡    早退
				}else{
					maps.put("pAttenceDay", 1);
					maps.put("pAttenceTime", 0);
					maps.put("pAttenceWithoutPay", 0);
					maps.put("pMealPay", 0);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",1);
				}
				//大于17:00算一天  早退
			}else if(afternoon.getTime()>=gbafternoon17.getTime()){
				maps.put("pAttenceDay", 1);
				maps.put("pAttenceTime", 0);
				maps.put("pAttenceWithoutPay", 0);
				maps.put("pMealPay", 0);
				maps.put("pTrafficPay", 0);
				maps.put("pAttenceChangeDay",0);
				maps.put("pAttenceUnpunctualTime",1);
				//12点 之后打卡
			}else if(afternoon.getTime()>=gbafternoon12.getTime()){
				maps.put("pAttenceDay", 0.5);
				maps.put("pAttenceTime", 0);
				maps.put("pAttenceWithoutPay", 0);
				maps.put("pMealPay", 0);
				maps.put("pTrafficPay", 0);
				maps.put("pAttenceChangeDay",0);
				maps.put("pAttenceUnpunctualTime",0);
			}else{
				maps.put("pAttenceDay", 0);
				maps.put("pAttenceTime", 0);
				maps.put("pAttenceWithoutPay", 0);
				maps.put("pMealPay", 0);
				maps.put("pTrafficPay", 0);
				maps.put("pAttenceChangeDay",0);
				maps.put("pAttenceUnpunctualTime",0);
			}
			//=====================2.早上迟到打卡==================	
		}else if(morning.getTime()<=gbgoodmoring10.getTime()){
			//加班到21点
			if(afternoon.getTime()>=gbafternoon21.getTime()){
				maps.put("pAttenceDay", 1);
				maps.put("pAttenceTime", Double.parseDouble(wo));
				maps.put("pAttenceWithoutPay", 50);
				maps.put("pMealPay", 25);
				maps.put("pTrafficPay", 25);
				maps.put("pAttenceChangeDay",0);
				maps.put("pAttenceUnpunctualTime",1);
				//加班到20点
			}else if(afternoon.getTime()>=gbafternoon20.getTime()){
				maps.put("pAttenceDay", 1);
				maps.put("pAttenceTime", Double.parseDouble(wo));
				maps.put("pAttenceWithoutPay", 25);
				maps.put("pMealPay", 25);
				maps.put("pTrafficPay", 0);
				maps.put("pAttenceChangeDay",0);
				maps.put("pAttenceUnpunctualTime",1);
				//未加班，正常打卡17:30
			}else if(afternoon.getTime()>=gbafternoon17.getTime()){
				maps.put("pAttenceDay", 1);
				maps.put("pAttenceTime", 0);
				maps.put("pAttenceWithoutPay", 0);
				maps.put("pMealPay", 0);
				maps.put("pTrafficPay", 0);
				maps.put("pAttenceChangeDay",0);
				maps.put("pAttenceUnpunctualTime",1);
				//未加班，12:00之后打卡
			}else if(afternoon.getTime()>=gbafternoon12.getTime()){
				maps.put("pAttenceDay", 0.5);
				maps.put("pAttenceTime", 0);
				maps.put("pAttenceWithoutPay", 0);
				maps.put("pMealPay", 0);
				maps.put("pTrafficPay", 0);
				maps.put("pAttenceChangeDay",0);
				maps.put("pAttenceUnpunctualTime",0);
			}else{
				maps.put("pAttenceDay", 0);
				maps.put("pAttenceTime", 0);
				maps.put("pAttenceWithoutPay", 0);
				maps.put("pMealPay", 0);
				maps.put("pTrafficPay", 0);
				maps.put("pAttenceChangeDay",0);
				maps.put("pAttenceUnpunctualTime",0);
			}
		}else{
			if(morning.getTime()<=gbafternoon13h.getTime()){
				//加班到21点
				if(afternoon.getTime()>=gbafternoon21.getTime()){
					maps.put("pAttenceDay", 0.5);
					maps.put("pAttenceTime", Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", 50);
					maps.put("pMealPay", 25);
					maps.put("pTrafficPay", 25);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
					//加班到20点
				}else if(afternoon.getTime()>=gbafternoon20.getTime()){
					maps.put("pAttenceDay", 0.5);
					maps.put("pAttenceTime", Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", 25);
					maps.put("pMealPay", 25);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
					//未加班，正常打卡18:00
				}else if(afternoon.getTime()>=gbafternoon18.getTime()){
					maps.put("pAttenceDay", 0.5);
					maps.put("pAttenceTime", 0);
					maps.put("pAttenceWithoutPay", 0);
					maps.put("pMealPay", 0);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
					//未加班，17:00之后打卡  早退
				}else if(afternoon.getTime()>=gbafternoon17.getTime()){
					maps.put("pAttenceDay", 0.5);
					maps.put("pAttenceTime", 0);
					maps.put("pAttenceWithoutPay", 0);
					maps.put("pMealPay", 0);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",1);
				}else{
					maps.put("pAttenceDay", 0);
					maps.put("pAttenceTime", 0);
					maps.put("pAttenceWithoutPay", 0);
					maps.put("pMealPay", 0);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
				}
			}else{
				//第一次打卡时间18:00之前
				if((morning.getTime()<=gbafternoon18.getTime())&&weekTime2>=5){
					//加班到21点
					if(afternoon.getTime()>=gbafternoon21.getTime()){
						maps.put("pAttenceDay", 0.5);
						maps.put("pAttenceTime", Double.parseDouble(wo));
						maps.put("pAttenceWithoutPay", 50);
						maps.put("pMealPay", 25);
						maps.put("pTrafficPay", 25);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
						//加班到20点
					}else if(afternoon.getTime()>=gbafternoon20.getTime()){
						maps.put("pAttenceDay", 0.5);
						maps.put("pAttenceTime", Double.parseDouble(wo));
						maps.put("pAttenceWithoutPay", 25);
						maps.put("pMealPay", 25);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
					}else if(afternoon.getTime()>=gbafternoon18h.getTime()){
						maps.put("pAttenceDay", 0.5);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
					}else{
						maps.put("pAttenceDay", 0);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
					}
				}else{
					maps.put("pAttenceDay", 0);
					maps.put("pAttenceTime", 0);
					maps.put("pAttenceWithoutPay", 0);
					maps.put("pMealPay", 0);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
				}
			}
		}
		return maps;
	}
	
	//考勤计算  按时间点计算方案  哺乳期
		public static Map<String , Object> modifyAttence1(int remarkFalg,Date morning,Date afternoon,PersonAttence personAttence) throws Exception{
			Map<String , Object> maps = new HashMap<String, Object>();
			
			DecimalFormat df   = new DecimalFormat("#.0");
			SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm" );
			
			Date gbgoodmoring9 = sdf.parse("09:00");
			Date gbgoodmoring9h = sdf.parse("09:30");
			Date gbgoodmoring10 = sdf.parse("10:00");
			Date gbgoodmoring10h = sdf.parse("10:30");
			Date gbafternoon12 = sdf.parse("12:00");
			Date gbafternoon13h = sdf.parse("13:30");
			Date gbafternoon17 = sdf.parse("17:00");
			Date gbafternoon17h = sdf.parse("17:30");
			Date gbafternoon18 = sdf.parse("18:00");
			Date gbafternoon18h = sdf.parse("18:30");
			Date gbafternoon20 = sdf.parse("20:00");
			Date gbafternoon21 = sdf.parse("21:00");
			
			/*if(remarkFalg==1){
				Date gbgoodmoring8 = sdf.parse("08:30");
				gbafternoon17h = sdf.parse("17:30");
			}else if(remarkFalg==2){
				gbgoodmoring9 = sdf.parse("09:00");
				gbafternoon18 = sdf.parse("18:00");
			}else{
				gbgoodmoring9h = sdf.parse("09:30");
				gbafternoon18h = sdf.parse("18:30");
			}*/
			//工作日---加班时
			String wo = null;
			if(afternoon.getTime()>gbafternoon18h.getTime()){
				double workTime = afternoon.getTime()-gbafternoon18h.getTime();
				double workTime1 = workTime/1000/60/60;
				double workTime2 = Double.parseDouble(df.format(workTime1));
				String ss = new String(workTime2+"");
				String bb = ss.substring(0, 1);
				String cc= ss.substring(2, 3);
				int dd = Integer.parseInt(cc);
				if(dd>=0&&dd<5){
					 wo = bb+"."+"0";
				}else if(dd>=5&&dd<=9){
					 wo = bb+"."+"5";
				}
			}
			//公休日---加班时
			double weekTime = afternoon.getTime()-morning.getTime();
			double weekTime1 = weekTime/1000/60/60;
			double weekTime2 = Double.parseDouble(df.format(weekTime1));
	//修改操作的情况		
			//====================1.早上打卡正常=======================================
			if((morning.getTime()<=gbgoodmoring9.getTime())||(morning.getTime()<=gbgoodmoring9h.getTime())){
			//if((morning.getTime()<=gbgoodmoring9.getTime())||(morning.getTime()<=gbgoodmoring9h.getTime())){
				//加班到21点
				if(afternoon.getTime()>=gbafternoon21.getTime()){
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
					maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
					maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
					maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
					//加班到20点
				}else if(afternoon.getTime()>=gbafternoon20.getTime()){
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
					maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
					maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
					//未加班，正常打卡18:30
				}else if(afternoon.getTime()>=gbafternoon17h.getTime()){
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
					//未加班，18:00打卡
				}else if(afternoon.getTime()>=gbafternoon17.getTime()){
					//如果早上09:00打卡正常出勤
					if(morning.getTime()<=gbgoodmoring9.getTime()){
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
						//如果早上09:30打卡    早退
					}else{
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
						maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
					}
					//大于17:00算一天  早退
				}else if(afternoon.getTime()>=gbafternoon12.getTime()){
					if(weekTime2>=5){
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
						maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
					}else{
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
					}
				}else{
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0);
				}
				//=====================2.早上迟到打卡==================	
			}else if(morning.getTime()<=gbgoodmoring10h.getTime()){
				//加班到21点
				if(afternoon.getTime()>=gbafternoon21.getTime()){
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
					maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
					maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
					maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
					maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
					//加班到20点
				}else if(afternoon.getTime()>=gbafternoon20.getTime()){
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
					maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
					maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
					maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
					//未加班，正常打卡17:00
				}else if(afternoon.getTime()>=gbafternoon18h.getTime()){
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
					//未加班，12:00之后打卡
				}else if(afternoon.getTime()>=gbafternoon18.getTime()){
					if(morning.getTime()<=gbgoodmoring10.getTime()){
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
						//如果早上09:30打卡    早退
					}else{
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
						maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
					}
				}else if(afternoon.getTime()>=gbafternoon12.getTime()){
					if(weekTime2>=5){
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+1);
						maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
					}else{
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
					}
				}else{
					maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0);
				}
			}else{
				if(morning.getTime()<=gbafternoon13h.getTime()){
					//加班到21点
					if(afternoon.getTime()>=gbafternoon21.getTime()){
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
						maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
						maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
						maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
						maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
						//加班到20点
					}else if(afternoon.getTime()>=gbafternoon20.getTime()){
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
						maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
						maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
						maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
						//未加班，正常打卡18:00
					}else if(afternoon.getTime()>=gbafternoon17.getTime()){
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
					}else{
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0);
					}
				}else{
					//第一次打卡时间18:00之前
					if((morning.getTime()<=gbafternoon18.getTime())&&weekTime2>=4){
						//加班到21点
						if(afternoon.getTime()>=gbafternoon21.getTime()){
							maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
							maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
							maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+50);
							maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
							maps.put("pTrafficPay", Double.parseDouble(personAttence.getpTrafficPay())+25);
							//加班到20点
						}else if(afternoon.getTime()>=gbafternoon20.getTime()){
							maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
							maps.put("pAttenceTime", Double.parseDouble(personAttence.getpAttenceTime())+Double.parseDouble(wo));
							maps.put("pAttenceWithoutPay", Double.parseDouble(personAttence.getpAttenceWithoutPay())+25);
							maps.put("pMealPay", Double.parseDouble(personAttence.getpMealPay())+25);
							//18:30点
						}else if(afternoon.getTime()>=gbafternoon18h.getTime()){
							maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
							//18:00点
						}else if(afternoon.getTime()>=gbafternoon18.getTime()){
							maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0.5);
							maps.put("pAttenceUnpunctualTime",Double.parseDouble(personAttence.getpAttenceUnpunctualTime())+1);
						}else{
							maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0);
						}
					}else{
						maps.put("pAttenceDay", Double.parseDouble(personAttence.getpAttenceDay())+0);
					}
				}
			}
			return maps;
		}
		//考勤计算  按时间点计算方案 哺乳期
		public static Map<String , Object> addAttence1(int remarkFalg,Date morning,Date afternoon,PersonAttence personAttence) throws Exception{
			Map<String , Object> maps = new HashMap<String, Object>();
			
			DecimalFormat df   = new DecimalFormat("#.0");
			SimpleDateFormat sdf = new SimpleDateFormat( "HH:mm" );
			
			Date gbgoodmoring9 = sdf.parse("09:00");
			Date gbgoodmoring9h = sdf.parse("09:30");
			Date gbgoodmoring10 = sdf.parse("10:00");
			Date gbgoodmoring10h = sdf.parse("10:30");
			Date gbafternoon12 = sdf.parse("12:00");
			Date gbafternoon13h = sdf.parse("13:30");
			Date gbafternoon17 = sdf.parse("17:00");
			Date gbafternoon17h = sdf.parse("17:30");
			Date gbafternoon18 = sdf.parse("18:00");
			Date gbafternoon18h = sdf.parse("18:30");
			Date gbafternoon20 = sdf.parse("20:00");
			Date gbafternoon21 = sdf.parse("21:00");
			
			/*if(remarkFalg==1){
				Date gbgoodmoring8 = sdf.parse("08:30");
				gbafternoon17h = sdf.parse("17:30");
			}else if(remarkFalg==2){
				gbgoodmoring9 = sdf.parse("09:00");
				gbafternoon18 = sdf.parse("18:00");
			}else{
				gbgoodmoring9h = sdf.parse("09:30");
				gbafternoon18h = sdf.parse("18:30");
			}*/
			//工作日---加班时
			String wo = null;
			if(afternoon.getTime()-gbafternoon18h.getTime()>0){
				double workTime = afternoon.getTime()-gbafternoon18h.getTime();
				double workTime1 = workTime/1000/60/60;
				double workTime2 = Double.parseDouble(df.format(workTime1));
				String ss = new String(workTime2+"");
				String bb = ss.substring(0, 1);
				String cc= ss.substring(2, 3);
				int dd = Integer.parseInt(cc);
				if(dd>=0&&dd<5){
					 wo = bb+"."+"0";
				}else if(dd>=5&&dd<=9){
					 wo = bb+"."+"5";
				}
			}
			//公休日---加班时
			double weekTime = afternoon.getTime()-morning.getTime();
			double weekTime1 = weekTime/1000/60/60;
			double weekTime2 = Double.parseDouble(df.format(weekTime1));
	//添加操作的情况		
			//====================1.早上打卡正常=======================================
			if((morning.getTime()<=gbgoodmoring9.getTime())||(morning.getTime()<=gbgoodmoring9h.getTime())){
				//加班到21点
				if(afternoon.getTime()>=gbafternoon21.getTime()){
					maps.put("pAttenceDay", 1);
					maps.put("pAttenceTime", Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", 50);
					maps.put("pMealPay", 25);
					maps.put("pTrafficPay", 25);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
					//加班到20点
				}else if(afternoon.getTime()>=gbafternoon20.getTime()){
					maps.put("pAttenceDay", 1);
					maps.put("pAttenceTime", Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", 25);
					maps.put("pMealPay", 25);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
					//未加班，正常打卡18:30
				}else if(afternoon.getTime()>=gbafternoon17h.getTime()){
					maps.put("pAttenceDay", 1);
					maps.put("pAttenceTime", 0);
					maps.put("pAttenceWithoutPay", 0);
					maps.put("pMealPay", 0);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
					//未加班，18:00打卡
				}else if(afternoon.getTime()>=gbafternoon17.getTime()){
					//如果早上09:00打卡正常出勤
					if(morning.getTime()<=gbgoodmoring9.getTime()){
						maps.put("pAttenceDay", 1);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
						//如果早上09:30打卡    早退
					}else{
						maps.put("pAttenceDay", 1);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",1);
					}
				}else if(afternoon.getTime()>=gbafternoon12.getTime()){
					if(weekTime2>=5){
						maps.put("pAttenceDay", 1);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",1);
					}else{
						maps.put("pAttenceDay", 0.5);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
					}
				}else{
					maps.put("pAttenceDay", 0);
					maps.put("pAttenceTime", 0);
					maps.put("pAttenceWithoutPay", 0);
					maps.put("pMealPay", 0);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
				}
				//=====================2.早上迟到打卡==================	
			}else if(morning.getTime()<=gbgoodmoring10h.getTime()){
				//加班到21点
				if(afternoon.getTime()>=gbafternoon21.getTime()){
					maps.put("pAttenceDay", 1);
					maps.put("pAttenceTime", Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", 50);
					maps.put("pMealPay", 25);
					maps.put("pTrafficPay", 25);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
					//加班到20点
				}else if(afternoon.getTime()>=gbafternoon20.getTime()){
					maps.put("pAttenceDay", 1);
					maps.put("pAttenceTime", Double.parseDouble(wo));
					maps.put("pAttenceWithoutPay", 25);
					maps.put("pMealPay", 25);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
					//未加班，正常打卡18:30
				}else if(afternoon.getTime()>=gbafternoon18h.getTime()){
					maps.put("pAttenceDay", 1);
					maps.put("pAttenceTime", 0);
					maps.put("pAttenceWithoutPay", 0);
					maps.put("pMealPay", 0);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
					//未加班，12:00之后打卡
				}else if(afternoon.getTime()>=gbafternoon18.getTime()){
					if(morning.getTime()<=gbgoodmoring10.getTime()){
						maps.put("pAttenceDay", 1);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
						//如果早上09:30打卡    早退
					}else{
						maps.put("pAttenceDay", 1);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",1);
					}
				}else if(afternoon.getTime()>=gbafternoon12.getTime()){
					if(weekTime2>=5){
						maps.put("pAttenceDay", 1);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",1);
					}else{
						maps.put("pAttenceDay", 0.5);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
					}
				}else{
					maps.put("pAttenceDay", 0);
					maps.put("pAttenceTime", 0);
					maps.put("pAttenceWithoutPay", 0);
					maps.put("pMealPay", 0);
					maps.put("pTrafficPay", 0);
					maps.put("pAttenceChangeDay",0);
					maps.put("pAttenceUnpunctualTime",0);
				}
			}else{
				if(morning.getTime()<=gbafternoon13h.getTime()){
					//加班到21点
					if(afternoon.getTime()>=gbafternoon21.getTime()){
						maps.put("pAttenceDay", 0.5);
						maps.put("pAttenceTime", Double.parseDouble(wo));
						maps.put("pAttenceWithoutPay", 50);
						maps.put("pMealPay", 25);
						maps.put("pTrafficPay", 25);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
						//加班到20点
					}else if(afternoon.getTime()>=gbafternoon20.getTime()){
						maps.put("pAttenceDay", 0.5);
						maps.put("pAttenceTime", Double.parseDouble(wo));
						maps.put("pAttenceWithoutPay", 25);
						maps.put("pMealPay", 25);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
						//未加班，正常打卡18:00
					}else if(afternoon.getTime()>=gbafternoon17.getTime()){
						maps.put("pAttenceDay", 0.5);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
						//未加班，17:00之后打卡  早退
					}else{
						maps.put("pAttenceDay", 0);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
					}
				}else{
					//第一次打卡时间18:00之前
					if((morning.getTime()<=gbafternoon18.getTime())&&weekTime2>=5){
						//加班到21点
						if(afternoon.getTime()>=gbafternoon21.getTime()){
							maps.put("pAttenceDay", 0.5);
							maps.put("pAttenceTime", Double.parseDouble(wo));
							maps.put("pAttenceWithoutPay", 50);
							maps.put("pMealPay", 25);
							maps.put("pTrafficPay", 25);
							maps.put("pAttenceChangeDay",0);
							maps.put("pAttenceUnpunctualTime",0);
							//加班到20点
						}else if(afternoon.getTime()>=gbafternoon20.getTime()){
							maps.put("pAttenceDay", 0.5);
							maps.put("pAttenceTime", Double.parseDouble(wo));
							maps.put("pAttenceWithoutPay", 25);
							maps.put("pMealPay", 25);
							maps.put("pTrafficPay", 0);
							maps.put("pAttenceChangeDay",0);
							maps.put("pAttenceUnpunctualTime",0);
						}else if(afternoon.getTime()>=gbafternoon18h.getTime()){
							maps.put("pAttenceDay", 0.5);
							maps.put("pAttenceTime", 0);
							maps.put("pAttenceWithoutPay", 0);
							maps.put("pMealPay", 0);
							maps.put("pTrafficPay", 0);
							maps.put("pAttenceChangeDay",0);
							maps.put("pAttenceUnpunctualTime",0);
						}else{
							maps.put("pAttenceDay", 0);
							maps.put("pAttenceTime", 0);
							maps.put("pAttenceWithoutPay", 0);
							maps.put("pMealPay", 0);
							maps.put("pTrafficPay", 0);
							maps.put("pAttenceChangeDay",0);
							maps.put("pAttenceUnpunctualTime",0);
						}
					}else{
						maps.put("pAttenceDay", 0);
						maps.put("pAttenceTime", 0);
						maps.put("pAttenceWithoutPay", 0);
						maps.put("pMealPay", 0);
						maps.put("pTrafficPay", 0);
						maps.put("pAttenceChangeDay",0);
						maps.put("pAttenceUnpunctualTime",0);
					}
				}
			}
			return maps;
		}
}
