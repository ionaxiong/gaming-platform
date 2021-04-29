package com.example.Gamesite.web;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
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
	
	@GetMapping(value="/play/{id}")
	public String play(@PathVariable("id") Long gameId, Model model) {
		model.addAttribute("game", grepository.findByGameId(gameId));
		return "play";
	}
	
	@GetMapping(value="/account")
	public String account(Model model, @CurrentSecurityContext(expression="authentication?.name") String username) {
		model.addAttribute("user", urepository.findByUsername(username));
		return "account";
	}
	
	@GetMapping(value="/account/username")
	public String username(Model model, @CurrentSecurityContext(expression="authentication?.name") String username) {
		model.addAttribute("user", urepository.findByUsername(username));
		return "username";
	}
	@PostMapping(value="/account/username")
	public String saveUsername(User user, @CurrentSecurityContext(expression="authentication?.name") String username) {
		String newUsername = user.getUsername();
		User U = urepository.findByUsername(username);
		U.setUsername(newUsername);
		urepository.save(U);
		
		return "redirect:/logout";
	}
	
	@GetMapping(value="/account/password")
	public String password(Model model, @CurrentSecurityContext(expression="authentication?.name") String username) {
		model.addAttribute("user", new User());
		return "password";
	}
	@PostMapping(value="/account/password")
	public String savePassword(User user, @CurrentSecurityContext(expression="authentication?.name") String username) {
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String newPassword = bcrypt.encode(user.getPassword());
		User U = urepository.findByUsername(username);
		U.setPassword(newPassword);
		urepository.save(U);

		return "redirect:/logout";
	}
}
