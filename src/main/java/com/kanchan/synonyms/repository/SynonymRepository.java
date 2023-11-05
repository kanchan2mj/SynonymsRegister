package com.kanchan.synonyms.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.kanchan.synonyms.model.Synonym;

import jakarta.transaction.Transactional;

@Repository
public interface SynonymRepository extends JpaRepository<Synonym, Long> {

	Boolean existsByWord(String word);

	Synonym getByWord(String word);

	Synonym findByWord(String word);

	List<Synonym> findByGroupId(Integer groupId);

	Boolean existsAllByWordIn(List<String> words);

	@Transactional
	Integer deleteByGroupId(Integer groupId);

	@Modifying(flushAutomatically = true)
	@Transactional
	@Query("update Synonym w set w.groupId = ?1 where w.word in ?2")
	public void updateGroupIdByWord(Integer groupId, List<String> words);

}
