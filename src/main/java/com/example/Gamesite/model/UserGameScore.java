package com.example.Gamesite.model;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;

@Entity
public class UserGameScore {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userGameScoreId;
	
	@OneToOne(cascade=CascadeType.ALL,mappedBy="Game")
	@JsonIgnore
	private Long gameId;
	
	@OneToOne(cascade=CascadeType.ALL,mappedBy="User")
	@JsonIgnore
	private Long userId;
	
	private Integer score;
	
	public UserGameScore() {
		
	}

	public UserGameScore(Long userGameScoreId, Long gameId, Long userId, Integer score) {
		super();
		this.userGameScoreId = userGameScoreId;
		this.gameId = gameId;
		this.userId = userId;
		this.score = score;
	}

	public Long getUserGameScoreId() {
		return userGameScoreId;
	}

	public void setUserGameScoreId(Long userGameScoreId) {
		this.userGameScoreId = userGameScoreId;
	}

	public Long getGameId() {
		return gameId;
	}

	public void setGameId(Long gameId) {
		this.gameId = gameId;
	}

	public Long getUserId() {
		return userId;
	}

	public void setUserId(Long userId) {
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
