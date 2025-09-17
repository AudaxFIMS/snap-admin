/*
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package tech.ailef.snapadmin.external.dto;

/**
 * A wrapper class for information about mapping errors, i.e. errors that happen
 * during the initialization phase, when table and fields are mapped to our internal
 * representation.
 */
public class MappingError {
	private String message;

	public MappingError(String message) {
		this.message = message;
	}

	public String getMessage() {
		return message;
	}
	
}
