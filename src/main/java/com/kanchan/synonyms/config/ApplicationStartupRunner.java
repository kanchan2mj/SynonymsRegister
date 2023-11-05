package com.kanchan.synonyms.config;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import com.kanchan.synonyms.model.SynonymsDTO;
import com.kanchan.synonyms.service.SynonymService;

@Component
public class ApplicationStartupRunner implements CommandLineRunner {

	@Autowired
	SynonymService synonymService;

	@Override
	public void run(String... args) throws Exception {

		if (synonymService.getWords().isEmpty()) {
			List<String> list = new ArrayList<>();
			list.add("Greeting");
			list.add("Hello");
			SynonymsDTO synonymsDTO = new SynonymsDTO("Hi", list);

			synonymService.addSynonym(synonymsDTO);
		}

	}
}
