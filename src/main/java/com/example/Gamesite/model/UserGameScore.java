package com.example.Gamesite.model;

import java.util.Comparator;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

// UserGameScore is used to save score in games for users
// There can only be one score per game per user
// If a better score is achieved, overwrite the old score 
@Entity
public class UserGameScore {
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long userGameScoreId;
	
	// One game can have many scores
	@ManyToOne
	@JoinColumn(name="gameId")
	private Game gameId;

	// One user can have many scores
	@ManyToOne
	@JoinColumn(name="userId")
	private User userId;
	
	private Integer score;
	
	private Date date;
	
	public UserGameScore() {
		
	}

	public UserGameScore(Game gameId, User userId, Integer score, Date date) {
		super();
		this.gameId = gameId;
		this.userId = userId;
		this.score = score;
		this.date = date;
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
	
	public Date getDate() {
		return date;
	}

	public void setDate(Date date) {
		this.date = date;
	}

	// Sort used in leader board, sorts biggest to smallest
	static public class SortByScore implements Comparator<UserGameScore> {
		@Override
		public int compare(UserGameScore a, UserGameScore b)
		{
			return b.getScore() - a.getScore();
		}
	}

	@Override
	public String toString() {
		return "UserGameScore [\n\tuserGameScoreId=" + userGameScoreId + 
				", \n\tgameid=" + gameId + 
				", \n\tusername=" + userId +
				", \n\tscore=" + score + "]";
	}
}
