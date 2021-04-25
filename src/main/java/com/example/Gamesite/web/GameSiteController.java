package com.example.Gamesite.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Gamesite.model.GameRepository;
import com.example.Gamesite.model.UserGameScoreRepository;
import com.example.Gamesite.model.UserRepository;

@Controller
public class GameSiteController {
	@Autowired
	private GameRepository grepository;
	
	@Autowired
	private UserRepository urepository;
	
	@Autowired
	private UserGameScoreRepository srepository;
	
	@RequestMapping(value= {"/", "/games"})
	public String games(Model model) {
		model.addAttribute("games", grepository.findAll());
		return "gamelist";
	}
	
	
}
