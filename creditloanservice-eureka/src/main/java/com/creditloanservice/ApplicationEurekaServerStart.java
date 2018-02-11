package com.creditloanservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;

@EnableEurekaServer         //开启eureka服务
@SpringBootApplication      
public class ApplicationEurekaServerStart {

	public static void main(String[] args) {
		 SpringApplication.run(ApplicationEurekaServerStart.class, args);
	}
}
