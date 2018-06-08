package com.flx.ActiveMQ;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ActiveMQQueue 生产/消费模式，点对点，特点：1.所有消费者消息总和=生产者消息数量。2.可以生产者先发送消息，消费者后进行消费
 * ActiveMQTopic 发布/订阅模式  1对多，特点：1.每个消费者收到的数量都 =生产者数量。2.必须消费者先订阅消息，生产者发送之后才能收到消息 
 * @author flx
 */
@RestController
@RequestMapping("/mq")
public class ActiveMQTest {

	@Autowired
	private JMSProducer producer;
	
	@RequestMapping("/test")
	public void mqTest(){
	    //ActiveMQQueue activeMQQueue = new ActiveMQQueue("mytest.queue");//生产/消费模式 1:1
	    ActiveMQTopic activeMQTopic = new ActiveMQTopic("mytest.topic");//发布/订阅模式 1：多  需要添加：spring.jms.pub-sub-domain配置
	    for(int i = 0; i<10;i++){
	    	producer.sendMessage(activeMQTopic, "发送消息："+i);
	    }
	}
}
