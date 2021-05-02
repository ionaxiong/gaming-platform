package com.example.Gamesite.web;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
public class GameSiteControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Test
	public void testLoadingPages() throws Exception {
        this.mockMvc.perform(get("/")).andDo(print()).andExpect(status().is2xxSuccessful());
        
        this.mockMvc.perform(get("/login")).andDo(print()).andExpect(status().is2xxSuccessful());
        
        this.mockMvc.perform(get("/registration")).andDo(print()).andExpect(status().is2xxSuccessful());
        
        this.mockMvc.perform(get("/gamelist?sortby=name&category=test")).andDo(print()).andExpect(status().is2xxSuccessful());
        this.mockMvc.perform(get("/gamelist?sortby=date&category=test")).andDo(print()).andExpect(status().is2xxSuccessful());
        
        this.mockMvc.perform(get("/account")).andDo(print()).andExpect(status().is3xxRedirection());        
  	}

}
