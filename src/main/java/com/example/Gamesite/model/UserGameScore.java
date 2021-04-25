package com.example.Gamesite.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

@Entity
public class UserGameScore {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userGameScoreId;
	
	@ManyToOne(cascade = {CascadeType.MERGE})
	@MapsId("gameId")
	@JoinColumn(name="gameId")
	private Game gameId;

	@ManyToOne(cascade = {CascadeType.MERGE})
	@MapsId("userId")
	@JoinColumn(name="userId")
	private User userId;
	
	private Integer score;
	
	public UserGameScore() {
		
	}

	public UserGameScore(Game gameId, User userId, Integer score) {
		super();
		this.gameId = gameId;
		this.userId = userId;
		this.score = score;
	}

	public Long getUserGameScoreId() {
		return userGameScoreId;
	}

	public Game getGameId() {
		return gameId;
	}

	public void setGameId(Game gameId) {
		this.gameId = gameId;
	}

	public User getUserId() {
		return userId;
	}

	public void setUserId(User userId) {
		this.userId = userId;
	}

	public Integer getScore() {
		return score;
	}

	public void setScore(Integer score) {
		this.score = score;
	}

	@Override
	public String toString() {
		return "UserGameScore [userGameScoreId=" + userGameScoreId + ", gameId=" + gameId + ", userId=" + userId
				+ ", score=" + score + "]";
	}
}
