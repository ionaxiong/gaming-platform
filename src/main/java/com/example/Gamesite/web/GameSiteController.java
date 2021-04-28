package com.example.Gamesite.web;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.example.Gamesite.model.User;
import com.example.Gamesite.repository.GameRepository;
import com.example.Gamesite.repository.UserGameScoreRepository;
import com.example.Gamesite.repository.UserRepository;
import com.example.Gamesite.service.SecurityService;
import com.example.Gamesite.service.UserService;

@Controller
public class GameSiteController {
	@Autowired
	private GameRepository grepository;

	@Autowired
	private UserGameScoreRepository srepository;
	
	@Autowired
	private UserRepository urepository;

	@Autowired
	private UserService userService;

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

		userService.save(userForm);

		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

		return "redirect:/gamelist";
	}

	@GetMapping("/login")
	public String login(Model model, String error, String logout) {
		if (securityService.isAuthenticated()) {
			return "redirect:/";
		}

		if (error != null)
			model.addAttribute("error", "Your username and password is invalid!");

		if (logout != null)
			model.addAttribute("message", "You have been logged out successfully!");

		return "login";
	}

	@RequestMapping(value = { "/", "/gamelist" })
	public String games(Model model) {
		model.addAttribute("games", grepository.findAll());
		return "gamelist";
	}
	
	@RequestMapping(value="/play/{id}", method=RequestMethod.GET)
	public String play(@PathVariable("id") Long gameId, Model model) {
		model.addAttribute("game", grepository.findByGameId(gameId));
		return "play";
	}
	
	@RequestMapping(value="/profile", method=RequestMethod.GET)
	public String profile(Model model, @CurrentSecurityContext(expression="authentication?.name") String username) {
		model.addAttribute("user", urepository.findByUsername(username));
		return "profile";
	}
	
	@RequestMapping(value="/save", method=RequestMethod.POST)
	public String save(User user) {
		urepository.save(user);
		return "redirect:/";
	}
}
