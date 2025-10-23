/*
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package dev.semeshin.snapadmin.external.dto;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jakarta.validation.ConstraintViolation;
import jakarta.validation.ConstraintViolationException;

/**
 * Holds information about JPA validation errors occurring during
 * creation or editing of items.
 */
public class ValidationErrorsContainer {
	private Map<String, List<ConstraintViolation<?>>> errors = new HashMap<>();
	
	public ValidationErrorsContainer(ConstraintViolationException e) {
		e.getConstraintViolations().forEach(c -> {
			errors.putIfAbsent(c.getPropertyPath().toString(), new ArrayList<>());
			errors.get(c.getPropertyPath().toString()).add(c);
		});
	}
	
	public List<ConstraintViolation<?>> forField(String name) {
		return errors.getOrDefault(name, new ArrayList<>());
	}
	
	public boolean hasErrors(String name) {
		return forField(name).size() > 0;
	}
	
	public boolean isEmpty() {
		return errors.isEmpty();
	}

	@Override
	public String toString() {
		return "ValidationErrorsContainer [errors=" + errors + "]";
	}
	
	
}
