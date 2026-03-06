package com.rosetta_scope.java_backend.controller;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rosetta_scope.java_backend.dao.UserDao;
import com.rosetta_scope.java_backend.entity.Score;
import com.rosetta_scope.java_backend.entity.User;

import dto.ScoreRequest;

@RestController
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("/login")
	@ResponseBody
	public HashMap<String, String> login(@RequestParam String email, @RequestParam String password) {
		HashMap<String, String> response = new HashMap<String, String>();
		
		if (!userExists(email)) {
			response.put("response", "fail");
			return response;
		}
		
		User user = userDao.findByEmail(email).get();
		if (user.getPassword().equals(password)) {
			response.put("response", "success");
			return response;
		}
		else {
			response.put("response", "fail");
			return response;
		}
	}
	
	@PostMapping("/signup")
	@ResponseBody
	public HashMap<String, String> signup(@RequestBody User user) {
		HashMap<String, String> response = new HashMap<String, String>();
		
		if (userExists(user.getEmail())) {
			response.put("response", "fail");
			return response;
		}
		
		userDao.save(user);
		response.put("response", "success");
		return response;
	}
	
	@GetMapping("/user/{email}")
	@ResponseBody
	public User getUserByEmail(@PathVariable String email) {
		return userDao.findByEmail(email).get();
	}
	
	@GetMapping("/user-exists/{email}")
	@ResponseBody
	public boolean userExists(@PathVariable String email) {
		return userDao.userExists(email);
	}
	
//	@PostMapping("/user-save")
//	@ResponseBody
//	public User saveUser(@RequestBody User user) {
//		if (user.getScores() != null) {
//			for (Score score : user.getScores()) {
//				score.setUser(user);
//			}
//		}
//		
//		userDao.save(user);
//		// Client-side throws error in Volley if the response does not return a value, even if it is successful
//		return user;
//	}
	
	@DeleteMapping("/user-delete")
	public void deleteUser(@RequestBody User user) {
		userDao.delete(user);
	}
	
	@DeleteMapping("/user-delete/{email}")
	public void deleteUser(@PathVariable String email) {
		userDao.deleteByEmail(email);
	}
	
	@PostMapping("/add-score")
	public User saveScore(@RequestBody ScoreRequest request) {
		User user = userDao.findByEmail(request.getEmail()).get();
		
		Score score = new Score(
				request.getWord(),
				request.getLanguage(),
				request.getScore(),
				request.getEngWord(),
				request.getTimestamp()
		);
		
		score.setUser(user);
		user.addScore(score);
		
		if (!user.getConfidenceScores().containsKey(request.getEngWord())) {
			user.setWordsEncountered(user.getWordsEncountered() + 1);
		}
		
		if (request.getConfidenceScore() > 0.95) {
			user.setWordsMastered(user.getWordsMastered() + 1);
		}
		
		user.getConfidenceScores().put(request.getEngWord(), request.getConfidenceScore());
		
		userDao.save(user);
		
		// Client-side throws error in Volley if the response does not return a value, even if it is successful
		return user;
	}

}
