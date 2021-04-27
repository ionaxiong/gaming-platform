package com.example.Gamesite.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.example.Gamesite.model.User;
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
	
	@Autowired
	private UserValidator userValidator;
	
	@GetMapping("/registration")
	public String registration(Model model) {
		if (securityService.isAuthenticated()) {
			return "redirect:/";
		}
		model.addAttribute("userForm", new User());
		
		return "registration";
	}
	
	@PostMapping("/registration")
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		userValidator.validate(userForm, bindingResult);
		
		if (bindingResult.hasErrors()) {
			return "registration";
		}
		
		urepository.save(userForm);
		
		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());
		
		return "redirect:/games";
	}
	
	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (securityService.isAuthenticated()) {
			return "redirect:/games";
		}
		
		if (error != null)
			model.addAttribute("error","Your username and password is invalid!");
		
		if (logout != null)
			model.addAttribute("message","You have been logged out successfully!");
		
		return "login";
	}
	
	@RequestMapping(value= {"/", "/games"})
	public String games(Model model) {
		model.addAttribute("games", grepository.findAll());
		return "games";
	}
}
