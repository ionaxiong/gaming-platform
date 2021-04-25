package com.example.Gamesite.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserGameScoreRepository extends CrudRepository<UserGameScore, Integer>{
	//return the users with the given identifiers
	List<UserGameScore> findByGameId(Long gameId);

	//save score for a user for a game
//	UserGameScore save(String gameId, String userId, Integer score);
//	void save(String gameId, String userId, int score);
	
}
