package com.flx.ActiveMQ;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

/**
 * 消费者类
 * @author flx
 *
 */
@Component
public class JMSConsumer2 {

	@JmsListener(destination = "mytest.topic")
	public void receiveQueue(String text){
		System.out.println("收到的消息2："+text);
	}
}
