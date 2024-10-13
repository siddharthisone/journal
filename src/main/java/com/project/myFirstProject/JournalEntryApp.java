package com.project.myFirstProject;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class JournalEntryApp
{

	public static void main(String[] args) {

		SpringApplication.run(JournalEntryApp.class, args);
	}

	@Bean
	public PlatformTransactionManager xyz(MongoDatabaseFactory dbFactory)
	{
		return new MongoTransactionManager(dbFactory);
	}


}
