package com.rosetta_scope.java_backend.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class Score {
	
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private int id;
	
	private String word;
	private String language;
	private int score;
	private String engWord;
	private long timestamp;
	
	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "email")
	@JsonIgnore
	private User user;
	
	public Score() {
		
	}

	public Score(String word, String language, int score, String engWord, long timestamp) {
		super();
		this.word = word;
		this.language = language;
		this.score = score;
		this.engWord = engWord;
		this.timestamp = timestamp;
	}

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}
	

	public String getLanguage() {
		return language;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public int getScore() {
		return score;
	}

	public void setScore(int score) {
		this.score = score;
	}

	public String getEngWord() {
		return engWord;
	}

	public void setEngWord(String engWord) {
		this.engWord = engWord;
	}

	public long getTimestamp() {
		return timestamp;
	}

	public void setTimestamp(long timestamp) {
		this.timestamp = timestamp;
	}

	public User getUser() {
		return user;
	}

	public void setUser(User user) {
		this.user = user;
	}

}
