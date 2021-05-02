package com.example.Gamesite.model;

import static org.assertj.core.api.Assertions.assertThat;

import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.Test;

public class GameTest {

	@Test
	public void test() {
		Game game = new Game();
		User user = new User();
		Category category = new Category();
		FileEntity file = new FileEntity();
		file.setData(new byte[] {0, 0});
		Date date = new Date();
		
		game.setCategory(category);
		game.setDescription("desc");
		game.setGame_url("url");
		game.setGameId((long) 123);
		game.setimage(file);
		game.setName("name");
		game.setPub_date(date);
		game.setPublisher(user);
		
		assertThat(game.getCategory()).isEqualTo(category);
		assertThat(game.getDescription()).isEqualTo("desc");
		assertThat(game.getGame_url()).isEqualTo("url");
		assertThat(game.getGameId()).isEqualTo((long) 123);
		assertThat(game.getimage()).isEqualTo(file);
		assertThat(game.getPub_date()).isEqualTo(date);
		assertThat(game.getPublisher()).isEqualTo(user);
		
		byte[] imageData = game.getimage().getData();
		byte[] encode = java.util.Base64.getEncoder().encode(imageData);
		String encodeToString = new String(encode, StandardCharsets.UTF_8);
		assertThat(game.getPhotosImagePath()).isEqualTo(encodeToString);
		
		game.setimage(null);
		assertThat(game.getPhotosImagePath()).isEqualTo("https://complianz.io/wp-content/uploads/2019/03/placeholder-300x202.jpg");
		
		DateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
		String formattedDate = formatter.format(game.getPub_date());
		assertThat(game.dateToString()).isEqualTo(formattedDate);

		assertThat(game.toString()).isNotNull();
	}

}
