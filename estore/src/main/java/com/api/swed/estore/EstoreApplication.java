package com.api.swed.estore;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration;

@SpringBootApplication(scanBasePackages = {"com.api.swed.estore"},exclude = HibernateJpaAutoConfiguration.class)
public class EstoreApplication {

	public static void main(String[] args) {
		SpringApplication.run(EstoreApplication.class, args);
	}
	

}
