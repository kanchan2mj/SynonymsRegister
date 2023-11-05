package com.kanchan.synonyms.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

@Entity
public class Synonym {
	
	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Long id ;
	
	@Column(unique=true)
	private String word;
	
	private Integer groupId;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getWord() {
		return word;
	}
	public void setWord(String word) {
		this.word = word;
	}
	
	
	public Integer getGroupId() {
		return groupId;
	}
	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}
	@Override
	public String toString() {
		return "Word [id=" + id + ", word=" + word + ", groupId=" + groupId + "]";
	}
	
	
	

}
