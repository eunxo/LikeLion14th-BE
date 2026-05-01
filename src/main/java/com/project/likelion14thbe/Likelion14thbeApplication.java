package com.project.likelion14thbe;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@EnableJpaAuditing
@SpringBootApplication
public class Likelion14thbeApplication {

	public static void main(String[] args) {
		SpringApplication.run(Likelion14thbeApplication.class, args);
	}

}
