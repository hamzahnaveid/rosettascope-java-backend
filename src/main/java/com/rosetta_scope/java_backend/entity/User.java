package com.rosetta_scope.java_backend.entity;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.FetchType;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.MapKeyColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.JoinColumn;

@Entity
public class User {
	
	@Id
	private String email;
	
	private String password;
	private String proficiency;
	private String targetLanguage;
	private int wordsEncountered;
	private int wordsMastered;
	
	@OneToMany(
			fetch = FetchType.LAZY,
			cascade = CascadeType.ALL,
			mappedBy = "user",
			orphanRemoval = true
			)
	private List<Score> scores;
	
	@ElementCollection
	@MapKeyColumn(name="word")
	@Column(name="confidence")
	@CollectionTable(name="user_confidence_scores", joinColumns= @JoinColumn(name="email"))
	private Map<String, Double> confidenceScores;
	
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
		this.scores = new ArrayList<>();
		this.confidenceScores = new HashMap<String, Double>();
	}

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

	public List<Score> getScores() {
		return scores;
	}

	public void setScores(List<Score> scores) {
		this.scores = scores;
	}
	
	public void addScore(Score score) {
		score.setUser(this);
		this.scores.add(score);
	}

	public Map<String, Double> getConfidenceScores() {
		return confidenceScores;
	}

	public void setConfidenceScores(Map<String, Double> confidenceScores) {
		this.confidenceScores = confidenceScores;
	}

}
