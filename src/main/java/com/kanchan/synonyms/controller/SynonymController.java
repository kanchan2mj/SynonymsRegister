package com.kanchan.synonyms.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.kanchan.synonyms.model.Constants;
import com.kanchan.synonyms.model.SynonymsDTO;
import com.kanchan.synonyms.service.SynonymService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;

@RestController
@RequestMapping("/synonym")
@SecurityRequirement(name = "Authorization")
public class SynonymController {

	@Autowired
	private SynonymService synonymService;

	@Operation(summary = "Get all words", description = "Returns all the products")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request is successful"),
			@ApiResponse(responseCode = "404", description = "Database is empty") ,})
	@GetMapping
	public ResponseEntity<Object> getWords() {
		if (synonymService.getWords().isEmpty())
			return new ResponseEntity<>(Constants.not_success, HttpStatus.NOT_FOUND);
		else
			return ResponseEntity.ok(synonymService.getWords());
	}

	@Operation(summary = "Get synonyms for a word", description = "Searches for synonyms")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request is successful"),
			@ApiResponse(responseCode = "404", description = "No Synonym Found") })
	@GetMapping(path="/search")
	public ResponseEntity<Object> getSynonymsForWord(@RequestParam String wordq) {

		List<String> synonyms = synonymService.getSynonymsForWord(wordq);
		if (synonyms.isEmpty())
			return new ResponseEntity<>(Constants.not_success, HttpStatus.NOT_FOUND);
		else
			return ResponseEntity.ok(synonyms);
	}

	@Operation(summary = "Delete all words", description = "Delete all the words")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request is successful")})
	@DeleteMapping
	@PreAuthorize("hasAuthority('SCOPE_WRITE_AUTHORITY')")
	public ResponseEntity<String> deleteAll() {
		synonymService.deleteAll();
		return ResponseEntity.ok(Constants.success);
	}

	@Operation(summary = "Delete all syonyms by word", description = "Delete by word")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request is successful")})
	@DeleteMapping("/byWord")
	@PreAuthorize("hasAuthority('SCOPE_WRITE_AUTHORITY')")
	public ResponseEntity<String> deleteByWord(@RequestParam String wordq) {
		synonymService.deleteByWord(wordq);
		return ResponseEntity.ok(Constants.success);
	}

	@Operation(summary = "Update synonyms(all words should be existing in the database)", description = "Update Synonyms")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request is successful"),
			@ApiResponse(responseCode = "400", description = "If words does exists")})
	@PutMapping(consumes ="application/json")
	@PreAuthorize("hasAuthority('SCOPE_WRITE_AUTHORITY')")
	public ResponseEntity<String> updateSynonym(@RequestBody SynonymsDTO synonymsDTO) {

		// Adding words if not exists

		synonymService.updateSynonym(synonymsDTO);

		return ResponseEntity.ok(Constants.success);
	}

	@Operation(summary = "add new synoyms", description = "add Synonyms")
	@ApiResponses(value = { @ApiResponse(responseCode = "200", description = "Request is successful"),
			@ApiResponse(responseCode = "400", description = "If words exists already")})
	@PostMapping(consumes ="application/json")
	@PreAuthorize("hasAuthority('SCOPE_WRITE_AUTHORITY')")
	public ResponseEntity<String> addSynonym(@RequestBody SynonymsDTO synonymsDTO) {
		synonymService.addSynonym(synonymsDTO);

		return new ResponseEntity<String>(Constants.success, HttpStatus.CREATED);
	}

}
