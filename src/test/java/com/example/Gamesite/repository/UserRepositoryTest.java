package com.example.Gamesite.repository;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import java.util.Date;
import java.util.List;

import org.junit.Before;
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
public class UserRepositoryTest {

    @Autowired
    private UserRepository repository;
    
    @Test
    public void findByName() {
    	User user = repository.findByUsername("user");
        
        assertThat(user).isNotNull();
        assertThat(user.getUsername()).isEqualTo("user");
    }
    
    @Test
    public void createNewUser() {
    	User user = new User("Test user", "password", "email@test.com", "role");
    	repository.save(user);
    	
    	assertThat(user.getUserId()).isNotNull();
    	assertThat(user.getUsername()).isEqualTo("Test user");
    }    

}
