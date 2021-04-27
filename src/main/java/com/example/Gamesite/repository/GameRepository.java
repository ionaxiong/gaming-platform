package com.example.Gamesite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Gamesite.model.Game;

public interface GameRepository extends JpaRepository <Game, Long>{
	Game findByGameId(Long gameId);
	Game findByName(String name);
}
