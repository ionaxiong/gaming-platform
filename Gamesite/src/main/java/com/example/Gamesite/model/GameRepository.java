package com.example.Gamesite.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

public interface GameRepository extends CrudRepository <Game, Long>{
	//return game with the given identifier or all
	Game findById(String gameId);
	Game findByName(String name);
	List<Game> findAll();
	
	//delete game with the given identifier
	void deleteById (String gameId);
}
