package com.example.Gamesite.service;

//provide current logged-in user and auto-login user after registration
public interface SecurityService {
	boolean isAuthenticated();

	void autoLogin(String username, String password);
}
