package com.example.Gamesite.repository;

import java.util.List;
import org.springframework.data.repository.CrudRepository;
import com.example.Gamesite.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	User findByUserId(Long userId);
	User findByUsername(String username);
	User findByEmail(String email);
}
