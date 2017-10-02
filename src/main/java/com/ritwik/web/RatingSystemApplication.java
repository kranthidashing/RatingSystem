package com.ritwik.web;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;


@EnableAutoConfiguration
@SpringBootApplication
@ComponentScan("com.ritwik.web")
public class RatingSystemApplication {

	public static void main(String[] args) {
		SpringApplication.run(RatingSystemApplication.class, args);
	}
}
