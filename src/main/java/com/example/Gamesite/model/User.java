package com.example.Gamesite.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Table(name="profile")
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "userId", nullable = false, updatable = false)
    private Long userId;

    @Column(name = "username", nullable = false, unique = true)
    private String username;

    @Column(name = "password", nullable = false)
    private String password;
    
    @Column(name = "email", nullable = false)
    @NotEmpty(message = "{email.notempty}")
    @Email(message = "Email should be valid")
    private String email;
    
    @Transient
    private String passwordConfirm;
    
    @ManyToMany
    private Set<Role> roles;
    
    public Set<Role> getRoles() {
		return roles;
	}

	public void setRoles(Set<Role> roles) {
		this.roles = roles;
	}

	@OneToMany(fetch=FetchType.EAGER, cascade = CascadeType.ALL, mappedBy = "userId")
    @JsonIgnore
    @Column(name = "savedGames", nullable = true)
    private List<Game> savedGames = new ArrayList<>();
    
    public User() {
    }
    
	public User(String username, String password, String email, List<Game> savedGames) {
		super();
		this.username = username;
		this.password = password;
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

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	
	public String getPasswordConfirm() {
		return passwordConfirm;
	}

	public void setPasswordConfirm(String passwordConfirm) {
		this.passwordConfirm = passwordConfirm;
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
		return "User [userId=" + userId + ", username=" + username + ", password=" + password + ", email=" + email
				+ ", passwordConfirm=" + passwordConfirm + ", roles=" + roles + ", savedGames=" + savedGames + "]";
	}
}