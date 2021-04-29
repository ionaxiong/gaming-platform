package com.example.Gamesite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Gamesite.model.Game;
import com.example.Gamesite.model.User;
import com.example.Gamesite.repository.GameRepository;
import com.example.Gamesite.repository.UserGameScoreRepository;
import com.example.Gamesite.repository.UserRepository;

@SpringBootApplication
@EnableJpaAuditing
public class GamesiteApplication {
	private static final Logger log = LoggerFactory.getLogger(GamesiteApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(GamesiteApplication.class, args);
	}

	@Bean
	public CommandLineRunner gamesiteDemo(GameRepository grepository, UserRepository urepository, UserGameScoreRepository srepository) {
		return(args) -> {
			log.info("save game, user, and score");
			
//			urepository.deleteAll();
			
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			String userPassword = bcrypt.encode("user");
			User user1 = new User("user", userPassword, "user@user.com", null);
			urepository.save(user1);
			
			String adminPassword = bcrypt.encode("admin");
			User admin = new User("admin", adminPassword, "admin@admin.com", null);
			urepository.save(admin);
			
//			ArrayList<Game> savedgame = new ArrayList<>();
			Game game1 = new Game("2048", "http://2048-iframe.s3.eu-central-1.amazonaws.com/index.html", "https://cdn.elgoog.im/2048/2048-game.png", "Puzzle", "2048 is a single-player sliding tile puzzle video game. The objective of the game is to slide numbered tiles on a grid to combine them to create a tile with the number 2048.", urepository.findByUsername("user"));
			grepository.save(game1);
			
			Game game2 = new Game("Clickermania", "http://wsd2018-game.s3.eu-central-1.amazonaws.com/index.html", "https://imgd.androidappsapk.co/EqtyrCp1QZDSj0Sk_-aYSC48Mg8TO1tC7rVI_8NSUkVwNClmALzK_l2LozxqTpxx9w=s500", "Casual", "Click mania allows you to gain as many scores as you can. You just need to click the circle to gain score, buy upgrades to automate clicking. A colorful and addictive game.", urepository.findByUsername("user"));
			grepository.save(game2);
			
			Game game3 = new Game("Flappy Bird", "", "https://services.garmin.com/appsLibraryBusinessServices_v0/rest/apps/baff701c-a71e-4854-bf0e-5a775793a838/icon/97f12c5a-6cd2-4b27-8084-291195737214", "Arcade", "Flappy bird is an arcade-style game. The game is a side-scroller where the player controls a bird, which moves persistently to the right and attempt to fly between green pipes without hitting them.", urepository.findByUsername("user"));
			grepository.save(game3);
			
			Game game4 = new Game("2048", "http://2048-iframe.s3.eu-central-1.amazonaws.com/index.html", "https://cdn.elgoog.im/2048/2048-game.png", "Puzzle", "2048 is a single-player sliding tile puzzle video game. The objective of the game is to slide numbered tiles on a grid to combine them to create a tile with the number 2048.", urepository.findByUsername("user"));
			grepository.save(game4);
			
			Game game5 = new Game("Clickermania", "http://wsd2018-game.s3.eu-central-1.amazonaws.com/index.html", "https://imgd.androidappsapk.co/EqtyrCp1QZDSj0Sk_-aYSC48Mg8TO1tC7rVI_8NSUkVwNClmALzK_l2LozxqTpxx9w=s500", "Casual", "Click mania allows you to gain as many scores as you can. You just need to click the circle to gain score, buy upgrades to automate clicking. A colorful and addictive game.", urepository.findByUsername("user"));
			grepository.save(game5);
			
			Game game6 = new Game("Flappy Bird", "", "https://services.garmin.com/appsLibraryBusinessServices_v0/rest/apps/baff701c-a71e-4854-bf0e-5a775793a838/icon/97f12c5a-6cd2-4b27-8084-291195737214", "Arcade", "Flappy bird is an arcade-style game. The game is a side-scroller where the player controls a bird, which moves persistently to the right and attempt to fly between green pipes without hitting them.", urepository.findByUsername("user"));
			grepository.save(game6);
			
			Game game7 = new Game("2048", "https://2048-iframe.s3.eu-central-1.amazonaws.com/index.html", "https://cdn.elgoog.im/2048/2048-game.png", "Puzzle", "2048 is a single-player sliding tile puzzle video game. The objective of the game is to slide numbered tiles on a grid to combine them to create a tile with the number 2048.", urepository.findByUsername("user"));
			grepository.save(game7);
			
			Game game8 = new Game("Clickermania", "https://wsd2018-game.s3.eu-central-1.amazonaws.com/index.html", "https://imgd.androidappsapk.co/EqtyrCp1QZDSj0Sk_-aYSC48Mg8TO1tC7rVI_8NSUkVwNClmALzK_l2LozxqTpxx9w=s500", "Casual", "Click mania allows you to gain as many scores as you can. You just need to click the circle to gain score, buy upgrades to automate clicking. A colorful and addictive game.", urepository.findByUsername("user"));
			grepository.save(game8);
			
			Game game9 = new Game("Flappy Bird", "", "https://services.garmin.com/appsLibraryBusinessServices_v0/rest/apps/baff701c-a71e-4854-bf0e-5a775793a838/icon/97f12c5a-6cd2-4b27-8084-291195737214", "Arcade", "Flappy bird is an arcade-style game. The game is a side-scroller where the player controls a bird, which moves persistently to the right and attempt to fly between green pipes without hitting them.", urepository.findByUsername("user"));
			grepository.save(game9);
			
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
