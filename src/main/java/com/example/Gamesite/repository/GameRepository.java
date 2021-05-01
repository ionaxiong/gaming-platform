package com.example.Gamesite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Gamesite.model.Category;
import com.example.Gamesite.model.Game;
import com.example.Gamesite.model.User;

public interface GameRepository extends JpaRepository <Game, Long>{
	Game findByGameId(Long gameId);
	Game findByName(String name);
	List<Game> findByUserId(User user);
	List<Game> findByCategory(Category name);
}
