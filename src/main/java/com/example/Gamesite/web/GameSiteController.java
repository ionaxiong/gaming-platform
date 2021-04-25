package com.example.Gamesite.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Gamesite.repository.GameRepository;
import com.example.Gamesite.repository.UserGameScoreRepository;
import com.example.Gamesite.repository.UserRepository;
import com.example.Gamesite.service.SecurityService;

@Controller
public class GameSiteController {
	@Autowired
	private GameRepository grepository;
	
	@Autowired
	private UserRepository urepository;
	
	@Autowired
	private UserGameScoreRepository srepository;
	
	@Autowired
	private SecurityService securityService;
	
	@RequestMapping(value= {"/", "/games"})
	public String games(Model model) {
		model.addAttribute("games", grepository.findAll());
		return "gamelist";
	}
	
	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (securityService.isAuthenticated()) {
			//redirect to gamelist page
			return "redirect:/";
		}
		
		if (error != null)
			model.addAttribute("error","Your username and password is invalid!");
		
		if (logout != null)
			model.addAttribute("message","You have been logged out successfully!");
		
		return "login";
	}
	
	
}
