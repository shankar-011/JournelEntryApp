package com.example.journelApp;

import com.mongodb.client.MongoDatabase;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournelAppApplication {

	public static void main(String[] args) {

		SpringApplication.run(JournelAppApplication.class, args);
	}
	@Bean
	public PlatformTransactionManager abc(MongoDatabaseFactory dbFactory){
		return new MongoTransactionManager(dbFactory);
	}
}
