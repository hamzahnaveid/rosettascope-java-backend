package com.rosetta_scope.java_backend.controller;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Random;

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

import com.rosetta_scope.java_backend.dao.ScoreDao;
import com.rosetta_scope.java_backend.dao.UserDao;
import com.rosetta_scope.java_backend.entity.Score;
import com.rosetta_scope.java_backend.entity.User;

import dto.ScoreRequest;

@RestController
public class UserController {
	
	@Autowired
	private UserDao userDao;
	
	@Autowired
	private ScoreDao scoreDao;
	
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
			// Sending user target language to put into app SharedPreferences for faster and easier access for workflows
			response.put("target_language", user.getTargetLanguage());
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
		response.put("target_language", user.getTargetLanguage());
		return response;
	}
	
	@GetMapping("/user/{email}")
	@ResponseBody
	public User getUserByEmail(@PathVariable String email) {
		User user = userDao.findByEmail(email).get();
		
		// Force loading of scores
		user.getScores().size();
		
		return user;
	}
	
	@GetMapping("/user-exists/{email}")
	@ResponseBody
	public boolean userExists(@PathVariable String email) {
		return userDao.userExists(email);
	}
	
	@PostMapping("/save-progress")
	@ResponseBody
	public User saveUserProgress(@RequestBody User user) {
		userDao.save(user);
		// Client-side throws error in Volley if the response does not return a value, even if it is successful
		return user;
	}
	
	@DeleteMapping("/user-delete")
	public HashMap<String, String> deleteUser(@RequestBody User user) {
		HashMap<String, String> response = new HashMap<String, String>();
		String status = "success";
		try {
			userDao.delete(user);

		} catch(Exception e) {
			status = "fail";
		}
		response.put("response", status);
		return response;
	}
	
	@DeleteMapping("/user-delete/{email}")
	public HashMap<String, String> deleteUser(@PathVariable String email) {
		HashMap<String, String> response = new HashMap<String, String>();
		String status = "success";
		try {
			userDao.deleteByEmail(email);

		} catch(Exception e) {
			status = "fail";
		}
		response.put("response", status);
		return response;
	}
	
	@PostMapping("/add-score")
	public User saveScore(@RequestBody ScoreRequest request) {
		User user = userDao.findByEmail(request.getEmail()).get();
		
		Score score = new Score(
				request.getWord(),
				request.getLanguage(),
				request.getScore(),
				request.getEngWord(),
				request.getTimestamp(),
				request.getFeedback()
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
	
	@GetMapping("/knowledgeTest/{email}")
	@ResponseBody
	public List<Score> getQuestionsForKnowledgeTest(@PathVariable String email) {		
		User user = getUserByEmail(email);
		Map<String, Double> scoreMap = user.getConfidenceScores();
		List<Score> knowledgeTestQuestions = new ArrayList<Score>();
		
		while (!scoreMap.isEmpty()) {
			
			if (knowledgeTestQuestions.size() >= 20) {
				break;
			}
			
			String proficientWord = Collections.max(scoreMap.entrySet(), Map.Entry.comparingByValue()).getKey();
			List<Score> scoreList = scoreDao.findUserScoresByWord(email, proficientWord);
			
			knowledgeTestQuestions.add(scoreList.get(scoreList.size()-1));
			// Get random entry in list of Scores excluding the last entry
			if (scoreList.size() > 1) {
				Random random = new Random();
				int index = random.nextInt(0, scoreList.size()-1);
				
				// Ensure no duplicate questions
				String word = scoreList.get(index).getWord();
				
				if (!word.equalsIgnoreCase(knowledgeTestQuestions.get(knowledgeTestQuestions.size()-1).getWord())) {
					knowledgeTestQuestions.add(scoreList.get(index));
				}
				
				else {
					while (word.equalsIgnoreCase(knowledgeTestQuestions.get(knowledgeTestQuestions.size()-1).getWord()) && scoreList.size() != 1) {
						index = random.nextInt(0, scoreList.size()-1);
						word = scoreList.get(index).getWord();
						if (!word.equalsIgnoreCase(knowledgeTestQuestions.get(knowledgeTestQuestions.size()-1).getWord())) {
							knowledgeTestQuestions.add(scoreList.get(index));
							break;
						}
						scoreList.remove(index);
					}
				}
			}
			
			// For getting subsequent most proficient words on next iterations of loop
			scoreMap.remove(proficientWord);
		}
		return knowledgeTestQuestions;
	}
	
	@GetMapping("/pain-points/{email}")
	@ResponseBody
	public List<Score> getPainPoints(@PathVariable String email) {
		User user = getUserByEmail(email);
		Map<String, Double> scoreMap = user.getConfidenceScores();
		List<Score> painPointsDrills = new ArrayList<Score>();
		
		List<Score> scoreList = scoreDao.findLowestUserScores(email);
		
		return scoreList;
	}
	
	@GetMapping("/trainer/{email}/{auto}")
	@ResponseBody
	public Map<String, Double> getTrainingWords(@PathVariable String email, @PathVariable String auto) {
		User user = getUserByEmail(email);
		Map<String, Double> userMap = user.getConfidenceScores();
		
		if (auto.equals("true")) {
			while (!userMap.isEmpty()) {
				if (userMap.size() <= 5) {
					break;
				}
				
				userMap.remove(Collections.max(userMap.entrySet(), Map.Entry.comparingByValue()).getKey());
			}
		}
		return userMap;

	}

}
