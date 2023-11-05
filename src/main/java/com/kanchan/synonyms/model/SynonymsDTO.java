package com.kanchan.synonyms.model;

import java.util.List;

public class SynonymsDTO {
	
	
	private String word ;
	
	private List<String> synonyms ;

	public String getWord() {
		return word;
	}

	public void setWord(String word) {
		this.word = word;
	}

	public SynonymsDTO(String word, List<String> synonyms) {
		super();
		this.word = word;
		this.synonyms = synonyms;
	}

	public List<String> getSynonyms() {
		return synonyms;
	}

	public void setSynonyms(List<String> synonyms) {
		this.synonyms = synonyms;
	}
	
	
	

}
