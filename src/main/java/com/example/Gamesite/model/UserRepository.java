package com.example.Gamesite.model;

import java.util.List;

import org.springframework.data.repository.CrudRepository;

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
