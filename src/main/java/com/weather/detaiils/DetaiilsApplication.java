package com.weather.detaiils;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class DetaiilsApplication {


	public static void main(String[] args) {
		SpringApplication.run(DetaiilsApplication.class, args);
	}

}
