package com.rosetta_scope.java_backend.dao;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosetta_scope.java_backend.entity.Score;
import com.rosetta_scope.java_backend.repository.ScoreRepository;

@Service
public class ScoreDao {
	
	@Autowired
	private ScoreRepository repo;
	
	public List<Score> findUserScoresByWord(String email, String engWord) {
		return repo.findByEmailAndWord(email, engWord);
	}
	
	public List<Score> findLowestUserScores(String email) {
		return repo.findLowestScoresByEmail(email);
	}

}
