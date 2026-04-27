package com.rosetta_scope.java_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rosetta_scope.java_backend.dao.ScoreDao;
import com.rosetta_scope.java_backend.dao.UserDao;
import com.rosetta_scope.java_backend.entity.Score;
import com.rosetta_scope.java_backend.entity.User;

@RestController
public class ScoreController {
	
	@Autowired
	private ScoreDao scoreDao;
	
	@Autowired
	private UserDao userDao;
	
	@GetMapping("/user-scores")
	@ResponseBody
	public List<Score> getUserScores(@RequestParam String email, @RequestParam String word) {
		User user = userDao.findByEmail(email).get();
		String targetLanguage = user.getTargetLanguage();
		
		return scoreDao.findUserScoresByWordAndLanguage(email, word, targetLanguage);
	}

}
