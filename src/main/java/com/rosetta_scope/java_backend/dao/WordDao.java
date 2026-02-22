package com.rosetta_scope.java_backend.dao;

import java.util.List;
import java.util.Optional; 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.rosetta_scope.java_backend.entity.Word;
import com.rosetta_scope.java_backend.repository.WordRepository;

@Service
public class WordDao {
	
	@Autowired
	private WordRepository repo;
	
	public void save(Word word) {
		repo.save(word);
	}
	
	public List<Word> retrieveWords() {
		return (List<Word>) repo.findAll();
	}

}
