package com.example.Gamesite.repository;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

import com.example.Gamesite.model.User;

public interface UserRepository extends CrudRepository<User, Long>{
	//find user with the given identifier
	User findByUserId(Long userId);
	User findByUsername(String username);
	User findByEmail(String email);
//	List<User> findAll();
//	
//	//delete user with the given identifier
//	void deleteByUserId(String userId);
//	void deleteByUsername(String username);
}
