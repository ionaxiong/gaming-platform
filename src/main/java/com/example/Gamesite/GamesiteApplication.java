package com.example.Gamesite;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.jpa.repository.config.EnableJpaAuditing;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.example.Gamesite.model.Category;
import com.example.Gamesite.model.Game;
import com.example.Gamesite.model.User;
import com.example.Gamesite.repository.CategoryRepository;
import com.example.Gamesite.repository.GameRepository;
import com.example.Gamesite.repository.UserGameScoreRepository;
import com.example.Gamesite.repository.UserRepository;

@SpringBootApplication
@EnableJpaAuditing
public class GamesiteApplication {
	private static final Logger log = LoggerFactory.getLogger(GamesiteApplication.class);

	// Main application function
	public static void main(String[] args) {
		SpringApplication.run(GamesiteApplication.class, args);
	}

	@Bean
	public CommandLineRunner gamesiteDemo(GameRepository grepository, UserRepository urepository, CategoryRepository crepository, UserGameScoreRepository srepository) {
		return(args) -> {
			// Create categories 
			crepository.save(new Category("Puzzle"));
			crepository.save(new Category("Arcade"));
			crepository.save(new Category("Casual"));
			crepository.save(new Category("Action"));
			crepository.save(new Category("Adventure"));
			crepository.save(new Category("Art"));
			crepository.save(new Category("Fantasy"));
			crepository.save(new Category("Fighting"));
			crepository.save(new Category("Party"));
			crepository.save(new Category("Shooter"));
			crepository.save(new Category("Simulation"));
			crepository.save(new Category("Sports"));
			crepository.save(new Category("Stealth"));
			crepository.save(new Category("Strategy"));
			crepository.save(new Category("Survival"));
			crepository.save(new Category("Text-based"));
			crepository.save(new Category("Trivia"));
			crepository.save(new Category("Others"));
			
			// Create test user with encrypted password
			BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
			String userPassword = bcrypt.encode("user");
			User user1 = new User("user", userPassword, "user@user.com", "USER");
			urepository.save(user1);
			
			// Create test admin with encrypted password
			String adminPassword = bcrypt.encode("admin");
			User admin = new User("admin", adminPassword, "admin@admin.com", "ADMIN");
			urepository.save(admin);
			
			// Create test games
			grepository.save(new Game("2048", "https://2048-iframe.s3.eu-central-1.amazonaws.com/index.html", crepository.findByNameIgnoreCase("Puzzle"), "2048 is a single-player sliding tile puzzle video game. The objective of the game is to slide numbered tiles on a grid to combine them to create a tile with the number 2048.", urepository.findByUsername("user")));
			grepository.save(new Game("Clickermania", "https://clickermania-game.s3.eu-central-1.amazonaws.com/index.html", crepository.findByNameIgnoreCase("Casual"), "Click mania allows you to gain as many scores as you can. You just need to click the circle to gain score, buy upgrades to automate clicking. A colorful and addictive game.", urepository.findByUsername("user")));
			grepository.save(new Game("Flappy Bird", "", crepository.findByNameIgnoreCase("Arcade"), "Flappy bird is an arcade-style game. The game is a side-scroller where the player controls a bird, which moves persistently to the right and attempt to fly between green pipes without hitting them.", urepository.findByUsername("admin")));
			grepository.save(new Game("2048", "http://2048-iframe.s3.eu-central-1.amazonaws.com/index.html", crepository.findByNameIgnoreCase("Puzzle"), "2048 is a single-player sliding tile puzzle video game. The objective of the game is to slide numbered tiles on a grid to combine them to create a tile with the number 2048.", urepository.findByUsername("admin")));
			grepository.save(new Game("Clickermania", "http://clickermania-game.s3.eu-central-1.amazonaws.com/index.html", crepository.findByNameIgnoreCase("Casual"), "Click mania allows you to gain as many scores as you can. You just need to click the circle to gain score, buy upgrades to automate clicking. A colorful and addictive game.", urepository.findByUsername("admin")));
			grepository.save(new Game("Flappy Bird", "", crepository.findByNameIgnoreCase("Arcade"), "Flappy bird is an arcade-style game. The game is a side-scroller where the player controls a bird, which moves persistently to the right and attempt to fly between green pipes without hitting them.", urepository.findByUsername("admin")));
			grepository.save(new Game("2048", "https://2048-iframe.s3.eu-central-1.amazonaws.com/index.html", crepository.findByNameIgnoreCase("Puzzle"), "2048 is a single-player sliding tile puzzle video game. The objective of the game is to slide numbered tiles on a grid to combine them to create a tile with the number 2048.", urepository.findByUsername("user")));
			grepository.save(new Game("Clickermania", "https://clickermania-game.s3.eu-central-1.amazonaws.com/index.html", crepository.findByNameIgnoreCase("Casual"), "Click mania allows you to gain as many scores as you can. You just need to click the circle to gain score, buy upgrades to automate clicking. A colorful and addictive game.", urepository.findByUsername("admin")));
			grepository.save(new Game("Flappy Bird", "", crepository.findByNameIgnoreCase("Arcade"), "Flappy bird is an arcade-style game. The game is a side-scroller where the player controls a bird, which moves persistently to the right and attempt to fly between green pipes without hitting them.", urepository.findByUsername("user")));
			
		};
	}

}
