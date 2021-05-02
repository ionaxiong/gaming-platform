package com.example.Gamesite.web;

import java.io.IOException;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.annotation.CurrentSecurityContext;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.example.Gamesite.model.FileEntity;
import com.example.Gamesite.model.Game;
import com.example.Gamesite.model.Game.SortByDate;
import com.example.Gamesite.model.Game.SortByName;
import com.example.Gamesite.model.User;
import com.example.Gamesite.model.UserGameScore;
import com.example.Gamesite.model.UserGameScore.SortByScore;
import com.example.Gamesite.repository.CategoryRepository;
import com.example.Gamesite.repository.FileRepository;
import com.example.Gamesite.repository.GameRepository;
import com.example.Gamesite.repository.UserGameScoreRepository;
import com.example.Gamesite.repository.UserRepository;
import com.example.Gamesite.service.FileService;
import com.example.Gamesite.service.SecurityService;
import com.example.Gamesite.service.UserService;

// Main controller for gamesite
@Controller
public class GameSiteController {
	@Autowired
	private FileService fileService;
	
	@Autowired
	private FileValidator fileValidator;
	
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
	
	// Registration
	@GetMapping("/registration")
	public String registration(Model model) {
		// If user is already logged in, move them to gamelist
		if (securityService.isAuthenticated()) {
			return "redirect:/";
		}
		// Add user model to form
		model.addAttribute("userForm", new User());
		
		return "registration";
	}
	@PostMapping("/registration")
	public String registration(@ModelAttribute("userForm") User userForm, BindingResult bindingResult) {
		// Validate that user is valid
		userValidator.validate(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "registration";
		}

		// Add USER role to new users
		userForm.setRole("USER");
		// Save user
		userService.save(userForm);
		// Login with new user
		securityService.autoLogin(userForm.getUsername(), userForm.getPasswordConfirm());

		return "redirect:/gamelist";
	}

	// Login
	@GetMapping("/login")
	public String login(Model model, String error) {
		// If login is successful, move user to gamelist
		if (securityService.isAuthenticated()) {
			return "redirect:/";
		}

		// If login failed, show error
		if (error != null)
			model.addAttribute("error", "Your username and password is invalid!");

		return "login";
	}

	// Gamelist, main page of application
	// Can be accessed by users that are not logged in
	// Uses URL search parameters to sort and filter list of games
	@RequestMapping(value = { "/", "/gamelist" })
	public String games(Model model, @RequestParam(required = false, name = "sortby") String sortBy, @RequestParam(required = false, name = "category") String category) {
		// Get list of games
		List<Game> game = grepository.findAll();
		
		// Filter by category, if url parameter "category" is not null
		if (category != null) {
			// Add attribute selectedCategory to highlight it in HTML
			model.addAttribute("selectedCategory", category);
			// Set list of games as games that have the selected category
			game = grepository.findByCategory(crepository.findByNameIgnoreCase(category.toLowerCase()));
		} 
		
		// Sort by parameter, if url parameter "sortBy" is not null
		if (sortBy != null) {
			// If sortBy is "name", sort the list with SortByName 
			if (sortBy.equals("name")) {
				game.sort(new SortByName());
			}
			// If sortBy is "date", sort the list with SortByDate
			if (sortBy.equals("date")) {
				game.sort(new SortByDate());
			}
		}
		
		model.addAttribute("games", game);
		model.addAttribute("categories", crepository.findAll());
		
		return "gamelist";
	}

	// Play game page, "Play Now" button directs here from gamelist
	// Chooses which game to play based on path variable "id" in URL
	@GetMapping(value = "/play/{id}")
	public String play(@PathVariable("id") Long gameId, Model model) {
		// Get game that will be played
		Game game = grepository.findByGameId(gameId);
		model.addAttribute("game", game);
		
		// Get scores for high score list below the game
		List<UserGameScore> scores = srepository.findByGameId(game);
		scores.sort(new SortByScore());
		// Show only the top 10 scores
		model.addAttribute("scores", scores.subList(0, Math.min(scores.size(), 10)));
		
		return "play";
	}

	// Account details page, only logged in users can access this
	@GetMapping(value = "/account")
	public String account(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) {
		User user = urepository.findByUsername(username);
		String role = user.getRole();
		List<Game> gamelist;
		
		// ADMIN is authorized to see a full list of games, USER can only see their own published games
		if (role.equals("ADMIN")) {
			gamelist = grepository.findAll();
		} else {
			gamelist = grepository.findByUserId(user);
		}
		
		// Sort games based on their name
		gamelist.sort(new SortByName());
		
		model.addAttribute("user", urepository.findByUsername(username));
		model.addAttribute("games", gamelist);
		
		return "account";
	}

	// Update username
	@GetMapping(value = "/account/username")
	public String username(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) {
		model.addAttribute("userForm", urepository.findByUsername(username));
		return "username";
	}
	@PostMapping(value = "/account/username")
	public String saveUsername(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
			@CurrentSecurityContext(expression = "authentication?.name") String username) {

		// Get username from the form and validate based on new username
		String newUsername = userForm.getUsername();
		userValidator.validateUsername(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "username";
		}
		
		// Save new username information
		User U = urepository.findByUsername(username);
		U.setUsername(newUsername);
		urepository.save(U);

		// Logout when username changes
		return "redirect:/logout";
	}

	// Update password
	@GetMapping(value = "/account/password")
	public String password(Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) {
		model.addAttribute("userForm", new User());
		return "password";
	}
	@PostMapping(value = "/account/password")
	public String savePassword(@ModelAttribute("userForm") User userForm, BindingResult bindingResult,
			@CurrentSecurityContext(expression = "authentication?.name") String username) {
		
		// Validate password
		userValidator.validatePassword(userForm, bindingResult);
		if (bindingResult.hasErrors()) {
			return "password";
		}
		
		// Encrypt new password
		BCryptPasswordEncoder bcrypt = new BCryptPasswordEncoder();
		String newPassword = bcrypt.encode(userForm.getPassword());

		// Save new password to user
		User U = urepository.findByUsername(username);
		U.setPassword(newPassword);
		urepository.save(U);

		// Logout when password changes
		return "redirect:/logout";
	}

	// Publish a new game
	@RequestMapping(value = "/account/publish")
	public String publish(Model model, Game game, @CurrentSecurityContext(expression = "authentication?.name") String username) {
		model.addAttribute("game", new Game());
		// Add categories to dropdown  
		model.addAttribute("categories", crepository.findAll());
		return "publish";
	}
	@RequestMapping(value = "/account/publish", method = RequestMethod.POST)
	public String save(Game game, @CurrentSecurityContext(expression = "authentication?.name") String username, BindingResult bindingResult, 
			Model model, @RequestParam("multipartfile") MultipartFile multipartFile) throws IOException {
		
		// Validate uploaded file
		fileValidator.validate(multipartFile, bindingResult);
		if (bindingResult.hasErrors()) {
			model.addAttribute("categories", crepository.findAll());
			return "publish";
		}
		
		// If file is not empty, save it and set it to game
		if (multipartFile != null && !multipartFile.isEmpty()) {
			FileEntity file = fileService.save(multipartFile);
			game.setimage(file);
		}
		
		// Set publisher as current logged in user
		User publisher = urepository.findByUsername(username);
		game.setPublisher(publisher);
		
		// Save new game and redirect to account information page
		grepository.save(game);
		return "redirect:/account";
	}

	// Edit game information
	@GetMapping(value = "/account/edit/{id}")
	public String editGame(@PathVariable("id") Long gameId, Model model, @CurrentSecurityContext(expression = "authentication?.name") String username) {
		// Check if game is published by current logged in user, or that current logged in user is admin
		User user = urepository.findByUsername(username);
		String role = user.getRole();
		Game game = grepository.findByGameId(gameId);
		if (role.equals("ADMIN") || game.getPublisher() == user) {
			model.addAttribute("game", game);
			model.addAttribute("categories", crepository.findAll());
			return "editgame";
		} else {
			// If user is not ADMIN or publisher of the game, redirect to login
			return "login";
		}
	}
	@PostMapping(value = "/account/edit")
	public String editGame(Model model, @RequestParam(name = "gameId") Long gameId, Game game,
			@CurrentSecurityContext(expression = "authentication?.name") String username,
			@RequestParam("multipartFile") MultipartFile multipartFile, BindingResult bindingResult) throws IOException {
	
		// Validate uploaded file
		fileValidator.validate(multipartFile, bindingResult);
		if(bindingResult.hasErrors()) {
			// If there are errors, add required attributes to model and show errors
			game.setGameId(gameId);
			model.addAttribute("game", game);
			model.addAttribute("categories", crepository.findAll());
			return "editgame";
		}
		
		// Check if game is published by current logged in user, or that current logged in user is admin
		Game g = grepository.findByGameId(gameId);
		User user = urepository.findByUsername(username);
		String role = user.getRole();
		if (role.equals("ADMIN") || g.getPublisher() == user) {
			
			// Save the file if it is not empty
			if (multipartFile != null && !multipartFile.isEmpty()) {
				FileEntity file = fileService.save(multipartFile);
				g.setimage(file);
			}
			
			// Update game information
			g.setPublisher(user);
			g.setCategory(game.getCategory());
			g.setDescription(game.getDescription());
			g.setGame_url(game.getGame_url());
			g.setName(game.getName());
			grepository.save(g);

			return "redirect:/account";
		} else {
			// If user is not ADMIN or publisher of the game, redirect to login
			return "login";
		}
	}

	// Delete a game
	@GetMapping(value = "/account/delete/{id}")
	public String deleteGame(@PathVariable("id") Long gameId, Model model,
			@CurrentSecurityContext(expression = "authentication?.name") String username) {
		// Check if game is published by current logged in user, or that current logged in user is admin
		User user = urepository.findByUsername(username);
		String role = user.getRole();
		Game g = grepository.findByGameId(gameId);
		if (role.equals("ADMIN") || g.getPublisher() == user) {
			
			// Show confirmation page for deleting a game
			model.addAttribute("game", grepository.findByGameId(gameId));
			model.addAttribute("categories", crepository.findAll());
			return "deletegame";
		} else {
			// If user is not ADMIN or publisher of the game, redirect to login
			return "login";
		}
	}
	@PostMapping(value = "/account/delete")
	public String deleteGame(@RequestParam(name = "gameId") Long gameId, Game game,
			@CurrentSecurityContext(expression = "authentication?.name") String username) {
		// Check if game is published by current logged in user, or that current logged in user is admin
		User user = urepository.findByUsername(username);
		String role = user.getRole();
		Game g = grepository.findByGameId(gameId);
		if (role.equals("ADMIN") || g.getPublisher() == user) {
			// Press delete button on confirmation page
			// Delete all scores for the game
			List<UserGameScore> scores = srepository.findByGameId(game);
			// Set user of score to null to not delete user
			scores.forEach(x -> x.setUserId(null));
			scores.forEach(x -> srepository.delete(x));
			// Delete game
			grepository.delete(g);
			return "redirect:/account";
		} else {
			// If user is not ADMIN or publisher of the game, redirect to login
			return "login";
		}
	}

	// API for saving score
	@RestController
	@RequestMapping("/api/savescore")
	public class RestAPI {

		// POST request to /api/savescore/{gameId}
		// Returns a string based on if score was saved or not
		@PostMapping(value = "/{id}")
		public String postCustomer(@PathVariable("id") Long gameId, @RequestParam(value = "score") int score,
				@CurrentSecurityContext(expression = "authentication?.name") String username) {
			
			// Get logged in user and played game information
			User user = urepository.findByUsername(username);
			Game game = grepository.findById(gameId).get();
			Date date = new Date();

			// Get current scores for this user and game
			List<UserGameScore> scores = srepository.findByUserIdAndGameId(user, game);

			// If score does not exist, save new score
			if (scores.size() == 0) {
				UserGameScore gamescore = new UserGameScore(game, user, score, date);
				srepository.save(gamescore);
				return "Created new high score";
			
			// If score exists, check if new score is better
			} else {
				UserGameScore gamescore = scores.get(0);
				
				// If new score is better, update database
				if (gamescore.getScore() < score) {
					gamescore.setScore(score);
					gamescore.setDate(date);
					srepository.save(gamescore);
					return "Updated high score";
					
				// If new score is not better, do nothing
				} else {
					return "No new high score";
				}
			}
		}
	}

}
