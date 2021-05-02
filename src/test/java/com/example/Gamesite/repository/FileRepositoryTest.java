package com.example.Gamesite.repository;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.Gamesite.model.FileEntity;

@ExtendWith(SpringExtension.class) 
@SpringBootTest
public class FileRepositoryTest {
	
	@Autowired
    private FileRepository repository;

    @Test
    public void findAllIsNotNull() {
    	List<FileEntity> files = repository.findAll();
        
        assertThat(files).isNotNull();
    }
    
}
