package com.example.Gamesite.model;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith(SpringExtension.class) 
@SpringBootTest
public class UserTest {
    
    @Test
    public void userSettersAndGetters() {
    	User user = new User("Test user", "password", "email@test.com", "role");
    	
    	assertThat(user.getUsername()).isEqualTo("Test user");
    	assertThat(user.getPassword()).isEqualTo("password");
    	assertThat(user.getEmail()).isEqualTo("email@test.com");
    	assertThat(user.getRole()).isEqualTo("role");
    	
    	user.setUsername("New name");
    	user.setPassword("New password");
    	user.setEmail("new@email.com");
    	user.setRole("new role");
    	
    	assertThat(user.getUsername()).isEqualTo("New name");
    	assertThat(user.getPassword()).isEqualTo("New password");
    	assertThat(user.getEmail()).isEqualTo("new@email.com");
    	assertThat(user.getRole()).isEqualTo("new role");
    }    

}
