package com.example.Gamesite.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Gamesite.model.Category;

// Basic repository for Categories
public interface CategoryRepository extends JpaRepository<Category, Long> {
	// Ignore case to help searching from HTML and JavaScript
	Category findByNameIgnoreCase(String name);
}