package cn.taskSys.utils;

import java.io.InputStream;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Properties;

/**
 * @author admin
 * 根据传入日期判断改日的具体详情
 * 需手动更新holiday.properties文件
 */
public class HolidayUtils {
	/**
	 * 属性说明：
	 * holiday.properties格式:日期=阴历#阳历#假日名称#是否周六#是否周日#是否法定假日#是否工作日#星期
	 * lunar：如五月十五日
	 * solar：阳历 yyyy-MM-dd
	 * vacationName：假期名称，如端午节或非假期
	 * isSaturday：是1|否0
	 * isSunday：是1|否0
	 * isVacation：是1|否0
	 * isWorkFlag：是1|否0
	 * week：1=周一...7周日
	 */
	 private String paramDate;		//参数日期字符串 yyyy-MM-dd
	 private String lunar;			//阴历
	 private String solar;			//阳历 yyyy-MM-dd
	 private String vacationName;	//假期名称
	 private boolean isSaturday;		//是否周六
	 private boolean isSunday;		//是否周日
	 private boolean isVacation;		//是否法定假期
	 private boolean isWorkFlag;		//是否工作日
	 private String week;			//星期
	 
	 public HolidayUtils(){
		 
	 }
	 
	 public HolidayUtils(String paramDate){
		 this.paramDate = paramDate;
	 }
	 
	 public void setDay(String dateStr){
		 try {
			if(dateStr==null || dateStr.equals("")){
				dateStr = DateUtil.DateToString(new Date(), "yyyy-MM-dd");
			}
	        Properties prop = new Properties();   
	        InputStream in = HolidayUtils.class.getResourceAsStream("/holiday.properties"); 
			prop.load(in);
			String str = prop.getProperty(dateStr);
			if(str!=null){//法定假日或因法定假日调休的工作日
				lunar = str.split("#")[0];
				solar = str.split("#")[1];
				vacationName = str.split("#")[2];
				isSaturday = str.split("#")[3].equals("1") ? true : false;
				isSunday = str.split("#")[4].equals("1") ? true : false;
				isVacation = str.split("#")[5].equals("1") ? true : false;
				isWorkFlag = str.split("#")[6].equals("1") ? true : false;
				week= str.split("#")[7];
				
			}else{//正常周六日、工作日
				lunar = "";
				solar = dateStr;
				Calendar calendar = Calendar.getInstance();
				calendar.setTime(DateUtil.StringToDate(dateStr));
				// 周日为1，周六为7
				int DAY_OF_WEEK = calendar.get(Calendar.DAY_OF_WEEK);
				switch (DAY_OF_WEEK) {
				case 1:
					vacationName = "非假期";
					isSaturday = false;
					isSunday = true;
					isWorkFlag = false;
					week= "周日";
					break;
				case 2:
					vacationName = "非假期";
					isSaturday = false;
					isSunday = false;
					isWorkFlag = true;
					week= "周一";
					break;
				case 3:
					vacationName = "非假期";
					isSaturday = false;
					isSunday = false;
					isWorkFlag = true;
					week= "周二";
					break;
				case 4:
					vacationName = "非假期";
					isSaturday = false;
					isSunday = false;
					isWorkFlag = true;
					week= "周三";
					break;
				case 5:
					vacationName = "非假期";
					isSaturday = false;
					isSunday = false;
					isWorkFlag = true;
					week= "周四";
					break;
				case 6:
					vacationName = "非假期";
					isSaturday = false;
					isSunday = false;
					isWorkFlag = true;
					week= "周五";
					break;
				case 7:
					vacationName = "非假期";
					isSaturday = true;
					isSunday = false;
					isWorkFlag = false;
					week= "周六";
					break;

				default:
					break;
				}
					
				isVacation = false;
			}
				
		} catch (Exception e) {
			e.printStackTrace();
		}
	 }
	 
	 /**
	 * 计算工作日:根据strStartDate, strEndDate两个日期，
	 * 计算这段时间内工作时间天数(不包含周六日和法定假日)
	 * @param strStartDate
	 * @param strEndDate
	 * @return
	 * 
	 */
	public int getDutyDays(String strStartDate,String strEndDate) {  
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");  
		Date startDate=null;  
		Date endDate = null;  
		
		try {  
			startDate=df.parse(strStartDate);  
			endDate = df.parse(strEndDate);
			int result = 0;
			while (startDate.compareTo(endDate) <= 0) {  
				Calendar begCalendar = Calendar.getInstance();
				begCalendar.setTime(startDate);
				int DAY_OF_MONTH = begCalendar.get(Calendar.DAY_OF_MONTH);
				// 周日为1，周六为7
				//int DAY_OF_WEEK = begCalendar.get(Calendar.DAY_OF_WEEK);
//				if (DAY_OF_WEEK != 7 && DAY_OF_WEEK != 1){  
//					result++; 
//				}
				setDay(DateUtil.DateToString(startDate, "yyyy-MM-dd"));
				if(this.isWorkFlag){
					result++;
				}
				begCalendar.set(Calendar.DAY_OF_MONTH, DAY_OF_MONTH+1);
				startDate = begCalendar.getTime();
			}
			return result;  
		} catch (ParseException e) {  
			System.out.println("非法的日期格式,无法进行转换");  
			e.printStackTrace();  
		}  
			  
		return 0;  
	}  
	 
	public String getParamDate() {
		return paramDate;
	}

	public void setParamDate(String paramDate) {
		this.paramDate = paramDate;
	}

	public String getLunar() {
		return lunar;
	}

	public void setLunar(String lunar) {
		this.lunar = lunar;
	}

	public String getSolar() {
		return solar;
	}

	public void setSolar(String solar) {
		this.solar = solar;
	}

	public String getVacationName() {
		return vacationName;
	}

	public void setVacationName(String vacationName) {
		this.vacationName = vacationName;
	}

	public boolean isSaturday() {
		return isSaturday;
	}

	public void setSaturday(boolean isSaturday) {
		this.isSaturday = isSaturday;
	}

	public boolean isSunday() {
		return isSunday;
	}

	public void setSunday(boolean isSunday) {
		this.isSunday = isSunday;
	}

	public boolean isVacation() {
		return isVacation;
	}

	public void setVacation(boolean isVacation) {
		this.isVacation = isVacation;
	}

	public boolean isWorkFlag() {
		return isWorkFlag;
	}

	public void setWorkFlag(boolean isWorkFlag) {
		this.isWorkFlag = isWorkFlag;
	}

	public String getWeek() {
		return week;
	}

	public void setWeek(String week) {
		this.week = week;
	}

	public static void main(String[] args) {
		
		HolidayUtils holiday = new HolidayUtils();
		holiday.setDay("2016-04-10");
		Date date = DateUtil.StringToDate("2016-10");
		System.out.println(DateUtil.DateToString(date, "yyyy-MM-dd"));
		System.out.println("------------------------------------------------------------------------");
		System.out.println("今日详情：");
		System.out.println("日期：" + DateUtil.StringToDate(holiday.getSolar()));
		System.out.println("农历：" + holiday.getLunar());
		System.out.println("公历：" + holiday.getSolar());
		System.out.println("假期名：" + holiday.getVacationName());
		System.out.println("是否周六：" + holiday.isSaturday());
		System.out.println("是否周日：" + holiday.isSunday());
		System.out.println("是否休假(法定)：" + holiday.isVacation());
		System.out.println("是否工作日：" + holiday.isWorkFlag());
		System.out.println("星期：" + holiday.getWeek());
		
		System.out.println(holiday.getDutyDays("2016-10-31", "2016-10-31"));
	}
	
}
