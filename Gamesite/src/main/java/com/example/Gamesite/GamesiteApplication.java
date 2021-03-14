package com.example.Gamesite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class GamesiteApplication {
	private static final Logger log = LoggerFactory.getLogger(GamesiteApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(GamesiteApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner gamesiteDemo() {
		return(args) -> {
			
		};
	}

}
