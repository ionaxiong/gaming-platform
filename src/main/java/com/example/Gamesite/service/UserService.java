package com.example.Gamesite.service;

import com.example.Gamesite.model.User;

public interface UserService {
	void save(User user);
	
	User findByUsername(String username);
}
