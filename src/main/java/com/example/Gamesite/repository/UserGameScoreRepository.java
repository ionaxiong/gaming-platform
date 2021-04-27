package com.example.Gamesite.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Gamesite.model.UserGameScore;

public interface UserGameScoreRepository extends JpaRepository<UserGameScore, Integer>{
	List<UserGameScore> findByGameId(Long gameId);
}
