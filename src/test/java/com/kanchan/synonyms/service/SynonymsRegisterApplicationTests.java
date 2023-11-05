package com.kanchan.synonyms.service;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.kanchan.synonyms.model.Synonym;
import com.kanchan.synonyms.repository.SynonymRepository;

@ExtendWith(MockitoExtension.class)
class SynonymsRegisterApplicationTests {

	@InjectMocks
	private SynonymService service;

	@Mock
	private SynonymRepository repository;

	@Test
	void getAllSynonym() {
		
		when(repository.findAll()).thenReturn(List.of(new Synonym(), new Synonym()));
		
		System.out.print("getAllSynonym ");
		assertThat(service.getWords()).hasSize(2);
		verify(repository, times(1)).findAll();
		verifyNoMoreInteractions(repository);
		
		
	}

}