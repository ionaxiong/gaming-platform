package com.example.Gamesite.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.Gamesite.model.Category;
import com.example.Gamesite.model.Game;
import com.example.Gamesite.model.User;

// Basic repository for Games
public interface GameRepository extends JpaRepository <Game, Long>{
	Game findByGameId(Long gameId);
	List<Game> findByName(String name);
	
	// For showing games in edit list
	// Only show games that are published by user
	List<Game> findByUserId(User user);
	
	// For filtering gamelist by category
	List<Game> findByCategory(Category name);
}
