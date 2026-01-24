package com.rosetta_scope.java_backend.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class User {
	
//	@Id
//	@GeneratedValue(strategy = GenerationType.IDENTITY)
//	private int id;
	
	@Id
	private String email;
	
	private String password;
	private String proficiency;
	private String targetLanguage;
	private int wordsEncountered;
	private int wordsMastered;
	
	public User() {
		
	}

	public User(String email, String password, String proficiency, String targetLanguage, int wordsEncountered,
			int wordsMastered) {
		this.email = email;
		this.password = password;
		this.proficiency = proficiency;
		this.targetLanguage = targetLanguage;
		this.wordsEncountered = wordsEncountered;
		this.wordsMastered = wordsMastered;
	}

//	public int getId() {
//		return id;
//	}
//
//	public void setId(int id) {
//		this.id = id;
//	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getProficiency() {
		return proficiency;
	}

	public void setProficiency(String proficiency) {
		this.proficiency = proficiency;
	}

	public String getTargetLanguage() {
		return targetLanguage;
	}

	public void setTargetLanguage(String targetLanguage) {
		this.targetLanguage = targetLanguage;
	}

	public int getWordsEncountered() {
		return wordsEncountered;
	}

	public void setWordsEncountered(int wordsEncountered) {
		this.wordsEncountered = wordsEncountered;
	}

	public int getWordsMastered() {
		return wordsMastered;
	}

	public void setWordsMastered(int wordsMastered) {
		this.wordsMastered = wordsMastered;
	}

}
