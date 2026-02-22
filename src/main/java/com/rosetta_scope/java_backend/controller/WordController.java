package com.rosetta_scope.java_backend.controller;

import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;

import com.rosetta_scope.java_backend.dao.WordDao;
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
				String wordStr = (String) jsonWordBank.get(String.valueOf(i));
				Word word = new Word(wordStr);
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

}
