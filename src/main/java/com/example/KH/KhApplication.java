package com.example.KH;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;

@SpringBootApplication
@EnableJpaAuditing
public class KhApplication {

	public static void main(String[] args) {
		SpringApplication.run(KhApplication.class, args);
	}

}
