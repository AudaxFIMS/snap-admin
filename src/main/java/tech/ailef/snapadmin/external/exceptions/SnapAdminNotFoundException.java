/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps

 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package tech.ailef.snapadmin.external.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.server.ResponseStatusException;

public class SnapAdminNotFoundException extends ResponseStatusException {
	private static final long serialVersionUID = 4090093290330473479L;

	public SnapAdminNotFoundException(String message) {
		super(HttpStatus.NOT_FOUND, message);
	}
	
	@Override
	public String getMessage() {
		return getReason();
	}

}
