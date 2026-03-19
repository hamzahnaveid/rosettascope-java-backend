package com.rosetta_scope.java_backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.rosetta_scope.java_backend.dao.ScoreDao;
import com.rosetta_scope.java_backend.entity.Score;

@RestController
public class ScoreController {
	
	@Autowired
	private ScoreDao scoreDao;
	
	@GetMapping("/user-scores")
	@ResponseBody
	public List<Score> getUserScores(@RequestParam String email, @RequestParam String word) {
		return scoreDao.findUserScoresByWord(email, word);
	}

}
