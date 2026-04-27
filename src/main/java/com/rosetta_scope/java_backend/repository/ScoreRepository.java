package com.rosetta_scope.java_backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.rosetta_scope.java_backend.entity.Score;

@Repository
public interface ScoreRepository extends JpaRepository<Score, Integer> {
	
	@Query("select s from Score s where s.user.email = ?1 and s.engWord = ?2 and s.language = ?3")
	List<Score> findByEmailAndWordAndLanguage(String email, String engWord, String targetLanguage);
	
	@Query("select s from Score s where s.user.email = ?1 and s.score < 90 and s.language = ?2")
	List<Score> findLowestScoresByEmailAndLanguage(String email, String targetLanguage);

}
