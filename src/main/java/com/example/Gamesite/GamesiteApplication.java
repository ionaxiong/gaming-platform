package com.example.Gamesite;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Gamesite.model.Game;
import com.example.Gamesite.model.User;
import com.example.Gamesite.model.UserGameScore;
import com.example.Gamesite.repository.GameRepository;
import com.example.Gamesite.repository.UserGameScoreRepository;
import com.example.Gamesite.repository.UserRepository;

@SpringBootApplication
public class GamesiteApplication {
	private static final Logger log = LoggerFactory.getLogger(GamesiteApplication.class);
	
	public static void main(String[] args) {
		SpringApplication.run(GamesiteApplication.class, args);
	}
	
	@Bean
	public CommandLineRunner gamesiteDemo(GameRepository grepository, UserRepository urepository, UserGameScoreRepository srepository) {
		return(args) -> {
			log.info("save game, user, and score");
			
			java.util.Date date = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse("2011-05-18 16:29:31");
			java.sql.Timestamp timestamp = new java.sql.Timestamp(date.getTime());
			
			urepository.deleteAll();
			
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			String userPassword = bcrypt.encode("user");
			User user1 = new User("user", userPassword, "user@user.com", null);
			urepository.save(user1);
			
			ArrayList<Game> savedgame = new ArrayList<>();
			Game game = new Game("game1", "game1_url", "game1_image", "fantasy", "game is fantastic", timestamp, urepository.findByUsername("user"));
			grepository.save(game);
			savedgame.add(game);
			
//			String user2Password = bcrypt.encode("user2");
//			User user2 = new User("user2", user2Password, "user2@user.com", savedgame);
//			urepository.save(user2);

//			UserGameScore score = new UserGameScore(grepository.findByName("game1"), urepository.findByUsername("user"), 100000);
//			srepository.save(score);
			
//			user1.setSavedGames(savedgame);
//			urepository.save(user1);
//			
			
//			log.info("fetch game details");
//			for (Game game: grepository.findAll()) {
//				log.info(game.toString());
//			}
		};
	}

}
