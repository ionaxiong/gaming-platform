package com.example.Gamesite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Gamesite.model.Game;
import com.example.Gamesite.model.User;
import com.example.Gamesite.model.UserGameScore;

// Repository for saving scores for games
public interface UserGameScoreRepository extends JpaRepository<UserGameScore, Integer>{
	// Find best scores for scoreboard when playing game
	List<UserGameScore> findByGameId(Game gameId);
	
	// When a user saves score, this is used to find the current score
	List<UserGameScore> findByUserIdAndGameId(User userId, Game gameId);
}
