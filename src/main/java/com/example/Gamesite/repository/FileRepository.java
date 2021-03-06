package com.example.Gamesite.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.Gamesite.model.FileEntity;

// File repository to help saving files
@Repository
public interface FileRepository extends JpaRepository<FileEntity, String>{
}
