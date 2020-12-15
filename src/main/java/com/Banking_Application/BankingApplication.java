package com.Banking_Application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 *
 */

@SpringBootApplication
@EnableJpaRepositories
public class BankingApplication {
	public static void main(String[] args) {
		SpringApplication.run(BankingApplication.class, args);
		System.out.println("Banking application");
	}
}
