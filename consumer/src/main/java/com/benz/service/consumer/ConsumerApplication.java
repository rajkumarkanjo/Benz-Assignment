package com.benz.service.consumer;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.ComponentScan;

@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class ConsumerApplication {

	public static void main(String[] args) {
        System.out.println("*********** Consumer Started ! ************");
		SpringApplication.run(ConsumerApplication.class, args);
	}

}
