package com.flx.model.mail;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Properties;

import javax.activation.DataHandler;
import javax.activation.DataSource;
import javax.activation.FileDataSource;
import javax.mail.BodyPart;
import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Multipart;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;
import javax.mail.internet.MimeUtility;

public class SendMail {
	
	public static void main(String[] args) {
		try{
			Properties props = new Properties();
			// 开启debug调试
			props.setProperty("mail.debug", "true");
			// 发送服务器需要身份验证
			props.setProperty("mail.smtp.auth", "true");
			// 设置邮件服务器主机名
			props.setProperty("mail.smtp.host", "smtp.qq.com");
			//端口
			props.put("mail.smtp.port", 587);
			// 发送邮件协议名称
			props.setProperty("mail.transport.protocol", "smtp");
			//使用JavaMail发送邮件的5个步骤
			//1、创建session
			Session session = Session.getInstance(props);
			//开启Session的debug模式，这样就可以查看到程序发送Email的运行状态
			session.setDebug(true);
			//2、通过session得到transport对象
			Transport ts = session.getTransport();
			//3、连上邮件服务器
			ts.connect("smtp.qq.com", "fulixinjing@qq.com", "ykrqelcgsscsbfgd");
			//4、创建邮件
			Message message = createAttachMail(session);
			//5、发送邮件
			ts.sendMessage(message, message.getAllRecipients());
			ts.close();
		}catch(Exception e){
			e.printStackTrace();
		}
	}

	private static Message createAttachMail(Session session) throws Exception {
		MimeMessage message = new MimeMessage(session);
		//设置邮件的基本信息
		//发件人
		message.setFrom(new InternetAddress("fulixinjing@qq.com"));
		//收件人
		message.setRecipient(Message.RecipientType.TO, new InternetAddress("lixinfu@creditharmony.cn"));
		//邮件标题
		message.setSubject("JavaMail邮件发送测试");
		
		MimeMultipart msgMultipart = new MimeMultipart("mixed");//混合的组合关系
		//设置邮件的MINE消息体
		message.setContent(msgMultipart);

		//附件1
		MimeBodyPart attch1 = new MimeBodyPart();
		//附件2
		MimeBodyPart attch2 = new MimeBodyPart();
		//正文内容
		MimeBodyPart content = new MimeBodyPart();

		//把内容，附件1，附件2加入到 MINE消息体中
		msgMultipart.addBodyPart(attch1);
		msgMultipart.addBodyPart(attch2);
		msgMultipart.addBodyPart(content);

		//把文件，添加到附件1中
		//数据源
		DataSource ds1 = new FileDataSource(new File("C:/Users/flx/Desktop/20170412153238_62493.jpg"));
		//数据处理器
		DataHandler dh1 = new DataHandler(ds1 );
		//设置第一个附件的数据
		attch1.setDataHandler(dh1);
		//设置第一个附件的文件名
		attch1.setFileName(MimeUtility.encodeText(ds1.getName()));

		//把文件，添加到附件2中
		DataSource ds2 = new FileDataSource(new File("C:/Users/flx/Desktop/timg.jpg"));
		DataHandler dh2 = new DataHandler(ds2 );
		attch2.setDataHandler(dh2);
		attch2.setFileName(MimeUtility.encodeText( "文件2.jpg"));

		//正文（图片和文字部分）
		MimeMultipart bodyMultipart  = new MimeMultipart("related");
		//设置内容为正文
		content.setContent(bodyMultipart);

		//html代码部分
		MimeBodyPart htmlPart = new MimeBodyPart();
		//html中嵌套的图片部分
		MimeBodyPart imgPart = new MimeBodyPart();

		//正文添加图片和html代码
		bodyMultipart.addBodyPart(htmlPart);
		bodyMultipart.addBodyPart(imgPart);

		//把文件，添加到图片中
		DataSource imgds = new FileDataSource(new File("C:/Users/flx/Desktop/333.png"));
		DataHandler imgdh = new DataHandler(imgds );
		imgPart.setDataHandler(imgdh);
		//说明html中的img标签的src，引用的是此图片
		imgPart.setHeader("Content-Location", "http://sunteam.cc/logo.jsg");

		//html代码
		htmlPart.setContent("<span style='color:red'>中文呵呵</span><img src=\"http://sunteam.cc/logo.jsg\">","text/html;charset=utf-8");

		//生成文件邮件
		message.saveChanges();

		
		
		//将创建的Email写入到E盘存储
		message.writeTo(new FileOutputStream("D:\\attachMail.eml"));
		//返回生成的邮件
		return message;
	}
}
