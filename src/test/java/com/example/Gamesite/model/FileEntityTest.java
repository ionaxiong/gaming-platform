package com.example.Gamesite.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Date;

import org.junit.Test;

public class FileEntityTest {

	@Test
	public void test() {
		FileEntity file = new FileEntity();

		file.setContentType("type");
		file.setData(new byte[] {0, 0});
		file.setId("123");
		file.setName("name");
		file.setSize((long) 50);
		
		assertThat(file.getContentType()).isEqualTo("type");
		assertThat(file.getData()).isEqualTo(new byte[] {0, 0});
		assertThat(file.getId()).isEqualTo("123");
		assertThat(file.getName()).isEqualTo("name");
		assertThat(file.getSize()).isEqualTo((long) 50);
	}

}
