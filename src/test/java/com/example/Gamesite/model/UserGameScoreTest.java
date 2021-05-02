package com.example.Gamesite.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) 
@SpringBootTest
public class UserGameScoreTest {

	@Test
	public void testSettersAndGetters() {
		UserGameScore score = new UserGameScore();
		User user = new User();
		Game game = new Game();
		Date date = new Date();
		
		score.setUserId(user);
		score.setGameId(game);
		score.setScore(1);
		score.setDate(date);
		
		assertThat(score.getUserId()).isEqualTo(user);
		assertThat(score.getGameId()).isEqualTo(game);
		assertThat(score.getScore()).isEqualTo(1);
		assertThat(score.getDate()).isEqualTo(date);
		assertThat(score.toString()).isNotNull();
	}

}
