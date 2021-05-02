package com.example.Gamesite.repository;

import static org.assertj.core.api.Assertions.assertThat;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import com.example.Gamesite.model.Category;

@ExtendWith(SpringExtension.class) 
@SpringBootTest
public class CategoryRepositoryTest {

    @Autowired
    private CategoryRepository repository;

    @Test
    public void findByNameIgnoreCaseShouldWorkProperly() {
    	Category adventure = repository.findByNameIgnoreCase("Adventure");
        Category adventureWrong = repository.findByNameIgnoreCase("Adveture");
        
        assertThat(adventure).isNotNull();
        assertThat(adventure.getName()).isEqualTo("Adventure");
        assertThat(adventureWrong).isNull();
    }
    
    @Test
    public void createNewCategory() {
    	Category category = new Category("Test");
    	repository.save(category);
    	assertThat(category.getId()).isNotNull();
    	assertThat(category.getName()).isEqualTo("Test");
    }    

}
