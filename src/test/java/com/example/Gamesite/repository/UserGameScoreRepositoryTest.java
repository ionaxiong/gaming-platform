package com.example.Gamesite.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.Gamesite.model.Game;
import com.example.Gamesite.model.User;
import com.example.Gamesite.model.UserGameScore;

@ExtendWith(SpringExtension.class) 
@SpringBootTest
public class UserGameScoreRepositoryTest {

    @Autowired
    private UserGameScoreRepository repository;

    @Autowired
    private GameRepository grepository;
    
    @Autowired
    private UserRepository urepository;
    
    @Test
    public void findByGameAndUser() {
    	User user = urepository.findByUsername("user");
    	Game game = grepository.findByName("2048").get(0);
    	List<UserGameScore> score = repository.findByUserIdAndGameId(user, game);
        
        assertThat(score).isNotNull();
        assertThat(score.size()).isEqualTo(0);
    }
    
    @Test
    public void createNewCategory() {
    	User user = urepository.findByUsername("user");
    	Game game = grepository.findByName("2048").get(0);
    	UserGameScore score = new UserGameScore(game, user, 100, new Date());
    	repository.save(score);
    	assertThat(score.getGameId()).isEqualTo(game);
    	assertThat(score.getUserId()).isEqualTo(user);
    	assertThat(score.getScore()).isEqualTo(100);
    }    

}