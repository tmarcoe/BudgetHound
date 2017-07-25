package com.config;

import org.springframework.boot.ExitCodeGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ApplicationConfig {

	@Bean
	public ExitCodeGenerator exitCodeGenerator() {
		return () -> 42;
	}


}
