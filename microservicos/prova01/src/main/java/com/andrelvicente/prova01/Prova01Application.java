package com.andrelvicente.prova01;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class Prova01Application {

	public static void main(String[] args) {
		SpringApplication.run(Prova01Application.class, args);
	}

}
