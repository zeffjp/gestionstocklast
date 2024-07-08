package com.gestionstock3.gestionstock;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;

@SpringBootApplication(exclude = { SecurityAutoConfiguration.class })
public class GestionstockApplication {

	public static void main(String[] args) {
		SpringApplication.run(GestionstockApplication.class, args);
	}

}
