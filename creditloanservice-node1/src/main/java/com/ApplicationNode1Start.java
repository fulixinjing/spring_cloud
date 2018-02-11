package com;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.netflix.eureka.EnableEurekaClient;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.web.bind.annotation.RestController;

import tk.mybatis.spring.annotation.MapperScan;

@EnableEurekaClient
@RestController
@SpringBootApplication
@EnableTransactionManagement
@EnableFeignClients
@MapperScan(basePackages = "com.creditloanservice.*.mapper")
public class ApplicationNode1Start {

	public static void main(String[] args) {
		SpringApplication.run(ApplicationNode1Start.class, args);
	}
}
