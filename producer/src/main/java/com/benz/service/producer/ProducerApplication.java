package com.benz.service.producer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * @author RAJ
 */

@EnableDiscoveryClient
@SpringBootApplication(exclude = { SecurityAutoConfiguration.class})
public class ProducerApplication {

	public static void main(String[] args) {
		System.out.println("************producer started ****************");
		SpringApplication.run(ProducerApplication.class, args);
	}

}
