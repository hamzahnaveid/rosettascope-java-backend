package com.rosetta_scope.java_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

@Entity
public class Word {
	
	@Id
	private String word;
	private String category;
	
	public Word() {
		
	}
	
	public Word(String word, String category) {
		this.word = word;
		this.category = category;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

}
