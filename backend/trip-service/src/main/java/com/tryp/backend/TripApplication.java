package com.tryp.backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.tryp.backend")
public class TripApplication {

	public static void main(String[] args) {
		SpringApplication.run(TripApplication.class, args);
	}

}
