package com.example.Gamesite.web;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.Gamesite.model.Game;
import com.example.Gamesite.model.User;
import com.example.Gamesite.model.UserGameScore;
import com.example.Gamesite.model.UserGameScore.SortByScore;
import com.example.Gamesite.repository.CategoryRepository;
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
	private CategoryRepository crepository;
	
	@Autowired
	private UserRepository urepository;

	@Autowired
	private UserService userService;

	@Autowired
	private SecurityService securityService;

	@Autowired
	private UserValidator userValidator;
	
	//registration and authentication
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
	
	//login page
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
	
	//displaying games - landing page
	@RequestMapping(value = { "/", "/gamelist" })
	public String games(Model model) {
		model.addAttribute("games", grepository.findAll());
		return "gamelist";
	}
	
	// "Play Now" button direct to game playing page
	@GetMapping(value="/play/{id}")
	public String play(@PathVariable("id") Long gameId, Model model) {
		model.addAttribute("game", grepository.findByGameId(gameId));
		
		Game game = grepository.findByGameId(gameId);
		List<UserGameScore> scores = srepository.findByGameId(game);
		scores.sort(new SortByScore());
		model.addAttribute("scores", scores.subList(0, Math.min(scores.size(), 10)));
		
		return "play";
	}
	
	//listing account details
	@GetMapping(value="/account")
	public String account(Model model, @CurrentSecurityContext(expression="authentication?.name") String username) {
		model.addAttribute("user", urepository.findByUsername(username));
		return "account";
	}
	
	//updating username 
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
	
	//updating password 
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
	
	//public a new game
	@RequestMapping(value="/account/publish")
	public String publish(Model model, Game game) {
		model.addAttribute("game", new Game());
		model.addAttribute("categories", crepository.findAll());
		return "publish";
	}
	
	@RequestMapping(value = "/account/publish", method = RequestMethod.POST)
	public String save(Game game) {
		grepository.save(game);
		return "redirect:/account";
	}
	
	//display games in the user favourite list
	@RequestMapping(value = "/favourites/{id}", method=RequestMethod.POST)
	public String favourites(@PathVariable("id") Long gameId, Model model, @CurrentSecurityContext(expression="authentication?.name") String username) {
//		model.addAttribute("favourites");
//		User user = urepository.findByUsername(username);
//		List<Game> favourites = user.getSavedGames();
//		Game newGame = grepository.findByGameId(gameId);
//		
//		favourites.add(newGame);
//		user.setSavedGames(favourites);
//		System.out.println("test");
		return "redirect:/gamelist";
	}
	

	@RestController
	@RequestMapping("/api/savescore")
	public class RestAPI {

		@PostMapping(value = "/{id}")
		public String postCustomer(@PathVariable("id") Long gameId, @RequestParam(value = "score") int score, @CurrentSecurityContext(expression="authentication?.name") String username) {
			User user = urepository.findByUsername(username);
			Game game = grepository.findById(gameId).orElseThrow();
			Date date = new Date();
			
			List<UserGameScore> scores = srepository.findByUserIdAndGameId(user, game);

			if (scores.size() == 0) {
				UserGameScore gamescore = new UserGameScore(game, user, score, date);
				srepository.save(gamescore);
				return "Created new high score";
			} else {
				UserGameScore gamescore = scores.get(0);
				if (gamescore.getScore() < score) {
					gamescore.setScore(score);
					gamescore.setDate(date);
					srepository.save(gamescore);
					return "Updated high score";
				}
				return "No new high score";
			}
		}

	}
	
}
