package com.Jahan.Task_Management;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.boot.web.support.SpringBootServletInitializer;;

@Configuration
@ComponentScan
@EnableAutoConfiguration
@SpringBootApplication
public class TaskManagementApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(TaskManagementApplication.class, args);
	}
	@Override
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
	        return application.sources(TaskManagementApplication.class);
	}

}
