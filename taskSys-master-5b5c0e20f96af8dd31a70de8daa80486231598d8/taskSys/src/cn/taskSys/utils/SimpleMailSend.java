package cn.taskSys.utils;

import java.util.Properties;

import org.apache.log4j.Logger;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSenderImpl;

public class SimpleMailSend {
	private static Logger logger = Logger.getLogger(SimpleMailSend.class);
	
	/**
	 * 发送邮件
	 * @param to_email 接受人邮件地址
	 * @param title 邮件标题
	 * @param context 邮件内容
	 * @param filePaths 邮件附件
	 * recipients 抄送邮箱
	 */
	public static void sendMail(String to_email,String title,String context,String recipients){
		try {
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
			// 设定mail server  mail.creditharmony.cn
			senderImpl.setHost("10.150.32.12");
			senderImpl.setPort(25);
			// 建立邮件消息  
			SimpleMailMessage mailMessage = new SimpleMailMessage();  
			mailMessage.setTo(to_email);//发送收件人// 设置收件人，寄件人 用数组发送多个邮件  
			mailMessage.setFrom("PMS@creditharmony.cn");  
			mailMessage.setSubject(title);  
			mailMessage.setText(context);  
			if(recipients.length()>1){
				mailMessage.setCc(recipients);//发送抄送人
			}
			//mailMessage.setBcc(bcc)//密送
			senderImpl.setUsername("PMS"); // 根据自己的情况,设置username,username设置时应注意不要加邮箱后缀  
			senderImpl.setPassword("pmo1pmo2pmo3*"); // 根据自己的情况, 设置password  
			Properties prop = new Properties();  
			prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确  
			prop.put("mail.smtp.timeout", "25000"); 
			prop.put("mail.transport.protocol", "smtp");
			prop.put("mail.smtp.starttls.enable", "true");
			senderImpl.setJavaMailProperties(prop);  
			// 发送邮件  
			senderImpl.send(mailMessage);  
			System.out.println("邮件发送成功-收件人"+to_email);
		} catch (MailException e) {
			System.out.println("发送邮件出现问题-----");
			logger.debug(e);
		} 
			
	}
	
	/**
	 * 发送邮件-重写方法
	 * @param to_email 接收人邮件地址-多个收件人
	 * @param title 邮件标题
	 * @param context 邮件内容
	 * @param filePaths 邮件附件
	 * @param recipients 抄送邮箱-多个抄送人
	 */
	public static void sendMail(String[] to_email,String title,String context,String[] recipients){
		try {
			JavaMailSenderImpl senderImpl = new JavaMailSenderImpl();  
			// 设定mail server  mail.creditharmony.cn
			senderImpl.setHost("10.150.32.12");
			senderImpl.setPort(25);
			// 建立邮件消息  
			SimpleMailMessage mailMessage = new SimpleMailMessage();  
			mailMessage.setTo(to_email);//发送收件人// 设置收件人，寄件人 用数组发送多个邮件  
			mailMessage.setFrom("PMS@creditharmony.cn");  
			mailMessage.setSubject(title);  
			mailMessage.setText(context);  
			mailMessage.setCc(recipients);//发送抄送人
			//mailMessage.setBcc(bcc)//密送
			senderImpl.setUsername("PMS"); // 根据自己的情况,设置username,username设置时应注意不要加邮箱后缀  
			senderImpl.setPassword("pmopmopmo"); // 根据自己的情况, 设置password  
			Properties prop = new Properties();  
			prop.put("mail.smtp.auth", "true"); // 将这个参数设为true，让服务器进行认证,认证用户名和密码是否正确  
			prop.put("mail.smtp.timeout", "25000"); 
			prop.put("mail.transport.protocol", "smtp");
			prop.put("mail.smtp.starttls.enable", "true");
			senderImpl.setJavaMailProperties(prop);  
			// 发送邮件  
			senderImpl.send(mailMessage);  
		} catch (MailException e) {
			logger.debug(e);
		} 
	}
	
	public static void main(String args[]) {  
		SimpleMailSend simpleMailSend = new SimpleMailSend();
		String ad="赛尔，同方 访问地址：http://10.150.80.217:8080/taskSys/";

		String ad1="其它办公区 访问地址：http://210.51.169.228:8217/taskSys/";
		String[] to_email = new String[] {"fanleisun@creditharmony.cn"}; 
		String[] recipients = new String[] {"junminzhao@creditharmony.cn"}; 
		String title="任务管理系统-任务延期提醒";
		String context="您好：\n    您创建的一项任务：，\n     负责人为：张三，计划完成时间为：2015-12-09，目前该任务已经延期，请及时跟踪。\n      链接地址："+ad+"\n     "+ad1;
		simpleMailSend.sendMail(to_email,title,context,recipients);
		   
		System.out.println(" 邮件发送成功.. ");
    }  

}
