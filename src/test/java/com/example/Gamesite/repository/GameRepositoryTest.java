package com.example.Gamesite.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.Gamesite.model.Category;
import com.example.Gamesite.model.Game;
import com.example.Gamesite.model.User;

@ExtendWith(SpringExtension.class) 
@SpringBootTest
public class GameRepositoryTest {

    @Autowired
    private GameRepository repository;
    
    @Autowired
    private CategoryRepository crepository;
    
    @Autowired
    private UserRepository urepository;

    @Test
    public void findByName() {
    	List<Game> game = repository.findByName("2048");
        List<Game> gameWrong = repository.findByName("Test");
        
        assertThat(game).isNotNull();
        assertThat(game.size()).isGreaterThan(0);
        assertThat(game.get(0).getName()).isEqualTo("2048");
        assertThat(gameWrong.size()).isEqualTo(0);
    }
    
    @Test
    public void createNewGame() {
    	Category category = new Category("Test Category");
    	crepository.save(category);
    	
    	User user = new User("Publisher", "password", "email@email.com", "role");
    	urepository.save(user);
    	
    	Game game = new Game("Test", "url", crepository.findByNameIgnoreCase("test category"), "description", urepository.findByUsername("Publisher"));
    	repository.save(game);
    	
    	assertThat(game.getGameId()).isNotNull();
    	assertThat(game.getName()).isEqualTo("Test");
    	assertThat(game.getCategory().getName()).isEqualTo("Test Category");
    }    

}
