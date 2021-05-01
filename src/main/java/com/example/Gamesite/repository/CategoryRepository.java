package com.example.Gamesite.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import com.example.Gamesite.model.Category;

public interface CategoryRepository extends JpaRepository<Category, Long> {
	Category findByNameIgnoreCase(String name);
}