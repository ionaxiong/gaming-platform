package com.example.Gamesite.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

public class CategoryTest {

	@Test
	public void test() {
		Category category = new Category();

		category.setId((long) 123);
		category.setName("Test category");
		
		assertThat(category.getId()).isEqualTo((long) 123);
		assertThat(category.getName()).isEqualTo("Test category");
	}

}
