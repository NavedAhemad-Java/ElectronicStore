package com.bikkadIt.electronic.store;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Profile;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

@SpringBootApplication
@EnableWebMvc

public class ElectronicStoreApplication implements CommandLineRunner  {

	public static void main(String[] args) {

		SpringApplication.run(ElectronicStoreApplication.class, args);
	}


	@Override
	public void run(String... args) throws Exception {

	}
}
