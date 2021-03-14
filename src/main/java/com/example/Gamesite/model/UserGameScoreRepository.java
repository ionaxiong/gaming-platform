package com.example.Gamesite.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface UserGameScoreRepository extends CrudRepository<UserGameScore, Long>{
	//return the users with the given identifiers
	List<UserGameScore> findByGameId(String gameId);
	
}
