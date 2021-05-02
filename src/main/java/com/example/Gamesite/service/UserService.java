package com.example.Gamesite.service;

import com.example.Gamesite.model.User;

// Service for handling User entity
public interface UserService {
	void save(User user);
	User findByUsername(String username);
	User findByEmail (String email);
}
