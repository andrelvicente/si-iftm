package com.andrelvicente.mongodb.relacionamentos;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@EnableMongoRepositories
@SpringBootApplication
public class MongodbRelacionamentos {

	public static void main(String[] args) {
		SpringApplication.run(MongodbRelacionamentos.class, args);
	}

}
