package com.example.Gamesite.model;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Comparator;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EntityListeners;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Transient;

import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

// Game is used to store data about a game, such as name and the URL of the game
@Entity
@EntityListeners(AuditingEntityListener.class)
public class Game {
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	@Column(name = "gameId", nullable = false, updatable = false)
	private Long gameId;

	@Column(name = "name", nullable = false)
	private String name;

	// The URL the game is hosted at
	@Column(name = "game_url", nullable = false)
	private String game_url;

	// The image of the game is in the database as a FileEntity
	@OneToOne(cascade = CascadeType.ALL)
	@JoinColumn(name = "image", referencedColumnName = "id")
	private FileEntity image;

	// Many games can be used for one category
	@ManyToOne
	@JoinColumn(name = "category", nullable = false)
	private Category category;
	private String description;

	@CreatedDate
	private Date pub_date;

	// Publisher of the game, user can have many games published
	@ManyToOne
	@JoinColumn(name = "userId")
	private User userId;
	
	public Game() {

	}

	public Game(String name, String game_url, Category category, String description, User publisher) {
		super();
		this.name = name;
		this.game_url = game_url;
		this.category = category;
		this.description = description;
		this.userId = publisher;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
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

	public FileEntity getimage() {
		return image;
	}

	public void setimage(FileEntity image) {
		this.image = image;
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

	// This is needed to show the images in HTML
	@Transient
	public String getPhotosImagePath() {
		// If the game has no image in database, return a placeholder image URL
		if (image == null || gameId == null) {
			return "https://complianz.io/wp-content/uploads/2019/03/placeholder-300x202.jpg";
		}

		// If the game has a image, load the data from the database
		byte[] imageData = this.getimage().getData();
		
		// To show the data in HTML, need to encode it to UTF_8
		byte[] encode = java.util.Base64.getEncoder().encode(imageData);
		String encodeToString = new String(encode, StandardCharsets.UTF_8);
		
		// Then return the encoded string
		return encodeToString;
	}
	
	// This is used in HTML to show formatted date
	@Transient
	public String dateToString() {
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = formatter.format(this.getPub_date());
		return formattedDate;
	}

	// Sort by name in gamelist
	static public class SortByName implements Comparator<Game> {
		@Override
		public int compare(Game a, Game b) {
			return a.name.compareTo(b.name);
		}
	}

	// Sort by date in gamelist
	static public class SortByDate implements Comparator<Game> {
		@Override
		public int compare(Game a, Game b) {
			return b.pub_date.compareTo(a.pub_date);
		}
	}

	@Override
	public String toString() {
		return "Game [gameId=" + gameId + ", name=" + name + ", game_url=" + game_url + ", image=" + image
				+ ", category=" + category + ", description=" + description + ", pub_date=" + pub_date + ", publisher="
				+ userId + "]";
	}
}
