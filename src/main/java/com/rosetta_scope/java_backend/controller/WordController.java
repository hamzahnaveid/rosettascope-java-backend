package com.rosetta_scope.java_backend.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

import com.rosetta_scope.java_backend.dao.WordDao;
import com.rosetta_scope.java_backend.entity.User;
import com.rosetta_scope.java_backend.entity.Word;

@RestController
public class WordController {
	
	@Autowired
	private WordDao wordDao;
	
	@PostMapping("/add-word-bank")
	public void addWordBank() {
		JSONParser parser = new JSONParser();
		
		try {
			Object obj = parser.parse(new FileReader("/Users/hamzahnaveid/Downloads/java-backend/src/main/resources/words.json"));
			JSONObject jsonWordBank = (JSONObject) obj;
			
			for (int i = 0; i < 80; i++) {
				JSONObject jsonWord = (JSONObject) jsonWordBank.get(String.valueOf(i));
				Word word = new Word(
						(String) jsonWord.get("word"),
						(String) jsonWord.get("category")
						);
				wordDao.save(word);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@GetMapping("/get-word-bank")
	@ResponseBody
	public List<String> getWordBank() {
		ArrayList<String> wordBank = new ArrayList<String>();
		
		List<Word> wordList = wordDao.retrieveWords();
		
		for (Word w : wordList) {
			wordBank.add(w.getWord());
		}
		
		return wordBank;
	}
	
	@GetMapping("/get-challenge-word-bank/{category}")
	@ResponseBody
	public List<String> getChallengeWordBank(@PathVariable String category) {
		Random random = new Random();
		
		ArrayList<String> wordBank = new ArrayList<String>();
		ArrayList<String> challengeWordBank = new ArrayList<String>();

		
		List<Word> wordList = wordDao.retrieveWords();
		
		for (Word w : wordList) {
			if (w.getCategory().equals(category) || w.getCategory().equals("all")) {
				wordBank.add(w.getWord());
			}
		}
		
		for (int i = 0; i < 5; i++) {
			int index = random.nextInt(0, wordBank.size());
			challengeWordBank.add(wordBank.get(index));
			wordBank.remove(index);
		}
		
		return challengeWordBank;
	}

}
