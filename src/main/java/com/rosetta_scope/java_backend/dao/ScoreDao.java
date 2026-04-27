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
	
	public List<Score> findUserScoresByWordAndLanguage(String email, String engWord, String targetLanguage) {
		return repo.findByEmailAndWordAndLanguage(email, engWord, targetLanguage);
	}
	
	public List<Score> findLowestUserScoresByLanguage(String email, String targetLanguage) {
		return repo.findLowestScoresByEmailAndLanguage(email, targetLanguage);
	}

}
