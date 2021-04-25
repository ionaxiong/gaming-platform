package com.example.Gamesite.model;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name="User")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String passwordHash;
    
    @Column(name = "email", nullable = false)
    private String email;
    
    @OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "userId")
    @JsonIgnore
    @Column(name = "savedGames", nullable = true)
    private List<Game> savedGames = new ArrayList<>();
    
    public User() {
    }
    
	public User(String username, String passwordHash, String email, List<Game> savedGames) {
		super();
		this.username = username;
		this.passwordHash = passwordHash;
		this.email = email;
		this.savedGames = savedGames;
	}

	public Long getUserId() {
		return userId;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPasswordHash() {
		return passwordHash;
	}

	public void setPasswordHash(String passwordHash) {
		this.passwordHash = passwordHash;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<Game> getSavedGames() {
		return savedGames;
	}

	public void setSavedGames(List<Game> savedGames) {
		this.savedGames = savedGames;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", username=" + username + ", passwordHash=" + passwordHash + ", email="
				+ email + ", savedGames=" + savedGames + "]";
	}
    
}