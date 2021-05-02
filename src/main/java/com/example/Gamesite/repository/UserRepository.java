package com.example.Gamesite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Gamesite.model.User;

// Repository for users
public interface UserRepository extends JpaRepository<User, Long>{
	User findByUserId(Long userId);
	User findByUsername(String username);
	User findByEmail(String email);
}
