package com.example.Gamesite.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.Gamesite.model.Game;

public interface GameRepository extends CrudRepository <Game, Long>{
	//return game with the given identifier or all
	Game findByGameId(Long gameId);
	Game findByName(String name);
//	List<Game> findAll();
//
//	//delete game with the given identifier
//	void deleteByGameId (String gameId);
}
