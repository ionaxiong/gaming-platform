package com.example.Gamesite.model;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.springframework.data.annotation.CreatedDate;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class Game {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	@Column(name = "gameId", nullable = false, updatable = false)
	private Long gameId;
	
	@Column(name = "name", nullable = false)
	private String name;
	
	@Column(name = "game_url", nullable = false)
	private String game_url;
	
	private String image_url;
	
	@ManyToOne
	@JoinColumn(name="category", nullable = false)
	private Category category;
	private String description;
	
	@CreatedDate
	private Date pub_date;
	
	@ManyToOne
	@JoinColumn(name="userId")
	private User userId;
	
	public Game() {
		
	}

	public Game(String name, String game_url, String image_url, Category category, String description, User publisher) {
		super();
		this.name = name;
		this.game_url = game_url;
		this.image_url = image_url;
		this.category = category;
		this.description = description;
		this.userId = publisher;
	}

	public Long getGameId() {
		return gameId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getGame_url() {
		return game_url;
	}

	public void setGame_url(String game_url) {
		this.game_url = game_url;
	}

	public String getImage_url() {
		return image_url;
	}

	public void setImage_url(String image_url) {
		this.image_url = image_url;
	}

	public Category getCategory() {
		return category;
	}

	public void setCategory(Category category) {
		this.category = category;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Date getPub_date() {
		return pub_date;
	}

	public void setPub_date(Date pub_date) {
		this.pub_date = pub_date;
	}

	public User getPublisher() {
		return userId;
	}

	public void setPublisher(User publisher) {
		this.userId = publisher;
	}
	
	static public class SortByName implements Comparator<Game> {
		@Override
		public int compare(Game a, Game b)
		{
			return a.name.compareTo(b.name);
		}
	}

	@Override
	public String toString() {
		return "Game [gameId=" + gameId + ", name=" + name + ", game_url=" + game_url + ", image_url=" + image_url
				+ ", category=" + category + ", description=" + description + ", pub_date=" + pub_date 
				+ ", publisher=" + userId + "]";
	}
}
