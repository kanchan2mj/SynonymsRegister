package com.kanchan.synonyms.service;

import java.util.List;
import java.util.Random;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import com.kanchan.synonyms.model.Synonym;
import com.kanchan.synonyms.model.SynonymsDTO;
import com.kanchan.synonyms.repository.SynonymRepository;

@Service
public class SynonymService {

	@Autowired
	SynonymRepository synonymRepository;

	public List<Synonym> getWords() {
		return synonymRepository.findAll();
	}

	public List<String> getSynonymsForWord(String wordq) {

		Synonym word = synonymRepository.findByWord(wordq);

		if (word == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Word does not exists.");
		}

		List<String> synonyms = synonymRepository.findByGroupId(word.getGroupId()).stream().map(s -> s.getWord())
				.collect(Collectors.toList());
		synonyms.remove(wordq);

		return synonyms;
	}

	public void deleteAll() {
		synonymRepository.deleteAll();
	}

	public void deleteByWord(String wordq) {

		Synonym word = synonymRepository.findByWord(wordq);

		if (word == null) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Word does not exists.");
		}

		synonymRepository.deleteByGroupId(word.getGroupId());
	}

	public void updateSynonym(SynonymsDTO synonymsDTO) {
		List<String> allWords = synonymsDTO.getSynonyms();
		allWords.add(synonymsDTO.getWord());

		if (!synonymRepository.existsAllByWordIn(allWords)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Word does not exists.");
		}
		Synonym w = synonymRepository.findByWord(synonymsDTO.getWord());
		synonymRepository.updateGroupIdByWord(w.getGroupId(), synonymsDTO.getSynonyms());
	}

	public void addSynonym(SynonymsDTO synonymsDTO) {

		int groupId;
		List<String> allWords = synonymsDTO.getSynonyms();
		if (synonymRepository.existsAllByWordIn(allWords)) {
			throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Synonym already exists.");
		}

		Synonym w = synonymRepository.findByWord(synonymsDTO.getWord());
		if (w != null) {
			groupId = w.getGroupId();
		} else {
			Random rand = new Random();
			// Generate random integers in range 0 to 999
			groupId = rand.nextInt(1000);
			allWords.add(synonymsDTO.getWord());

		}

		allWords.forEach(s -> {

			Synonym wq = new Synonym();
			wq.setWord(s);
			wq.setGroupId(groupId);
			synonymRepository.save(wq);
		});
	}

}
