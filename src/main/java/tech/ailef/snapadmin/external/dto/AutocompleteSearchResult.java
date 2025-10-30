/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package tech.ailef.snapadmin.external.dto;

import tech.ailef.snapadmin.external.controller.rest.AutocompleteController;
import tech.ailef.snapadmin.external.dbmapping.DbObject;

/**
 * An object to hold autocomplete results returned from the {@linkplain AutocompleteController}. 
 *
 */
public class AutocompleteSearchResult {
	
	private Object id;
	
	private String value;

	public AutocompleteSearchResult() {
	}
	
	public AutocompleteSearchResult(DbObject o) {
		this.id = o.getPrimaryKeyValue();
		this.value = o.getDisplayName();
	}
	
	/**
	 * Returns the primary key for the object
	 * @return
	 */
	public Object getId() {
		return id;
	}

	/**
	 * Returns the readable name for the object
	 * @return
	 */
	public String getValue() {
		return value;
	}
}
