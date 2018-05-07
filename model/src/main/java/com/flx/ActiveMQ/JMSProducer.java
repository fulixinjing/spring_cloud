package com.flx.ActiveMQ;

import javax.jms.Destination;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.network.jms.JmsMesageConvertor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.stereotype.Service;

/**
 * JMS:Java消息服务（Java Message Service）应用程序接口，是一个Java平台中关于面向消息中间件（MOM）的API，
 *     用于在两个应用程序之间，或分布式系统中发送消息，进行异步通信
 * 生产者类
 * @author flx
 *
 */
@Service
public class JMSProducer {
	
	@Autowired
	private JmsMessagingTemplate template;
	
	/**
	 * 
	 * @param destination mq对象
	 * @param message 消息内容
	 */
	public void sendMessage(Destination destination,String message){
		template.convertAndSend(destination, message);
	}
	
	
}
