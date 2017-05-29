package com;


import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

//@Configuration
//@ComponentScan
//@EnableAutoConfiguration
@SpringBootApplication(scanBasePackages = { "com.dao", "com.entity", "com.service", "com.controllers", "com.config" })
public class WelcomePageApplication {

	public static void main(String[] args) {
		
		SpringApplication.run(WelcomePageApplication.class, args);
	}
}
