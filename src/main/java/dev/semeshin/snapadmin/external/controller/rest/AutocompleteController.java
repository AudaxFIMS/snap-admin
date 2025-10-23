/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package dev.semeshin.snapadmin.external.controller.rest;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import dev.semeshin.snapadmin.external.SnapAdmin;
import dev.semeshin.snapadmin.external.dbmapping.DbObjectSchema;
import dev.semeshin.snapadmin.external.dbmapping.SnapAdminRepository;
import dev.semeshin.snapadmin.external.dto.AutocompleteSearchResult;

/**
 * API controller for autocomplete results
 */
@RestController
@RequestMapping(value= {"/${snapadmin.baseUrl}/api/autocomplete", "/${snapadmin.baseUrl}/api/autocomplete/"})
public class AutocompleteController {
	@Autowired
	private SnapAdmin snapAdmin;
	
	@Autowired
	private SnapAdminRepository repository;
	
	/**
	 * Returns a list of entities from a given table that match an input query.
	 * @param className full qualified class name; only search items for this entity
	 * @param query the query to search for
	 * @return a list of {@link AutocompleteSearchResult}
	 */
	@GetMapping("/{className}")
	public ResponseEntity<?> autocomplete(@PathVariable String className, @RequestParam String query) {
		DbObjectSchema schema = snapAdmin.findSchemaByClassName(className);
		
		List<AutocompleteSearchResult> search = repository.search(schema, query)
					.stream().map(x -> new AutocompleteSearchResult(x))
					.collect(Collectors.toList());
		
		return ResponseEntity.ok(search);
	}
}
