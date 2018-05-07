package com.flx.ActiveMQ;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消费者类
 * @author flx
 * 
 */
@Component
public class JMSConsumer {

	@JmsListener(destination = "mytest.topic") //监听器 监听生产者发送的消息 ，destination：消息队列名
	public void receiveQueue(String text){
		System.out.println("收到的消息："+text);
	}
}
