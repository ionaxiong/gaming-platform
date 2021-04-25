package com.example.Gamesite.model;

import java.util.Date;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Entity;

@Entity
public class Game {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long gameId;
	private String name;
	private String game_url;
	private String image_url;
	private String category;
	private String description;
	private Date pub_date;
	@ManyToOne
	@JoinColumn(name="userId")
	private User userId;
	
	public Game() {
		
	}

	public Game(String name, String game_url, String image_url, String category, String description,
			Date pub_date, User publisher) {
		super();
		this.name = name;
		this.game_url = game_url;
		this.image_url = image_url;
		this.category = category;
		this.description = description;
		this.pub_date = pub_date;
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

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
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

	@Override
	public String toString() {
		return "Game [gameId=" + gameId + ", name=" + name + ", game_url=" + game_url + ", image_url=" + image_url
				+ ", category=" + category + ", description=" + description + ", pub_date=" + pub_date + ", userId="
				+ userId + "]";
	}
}
