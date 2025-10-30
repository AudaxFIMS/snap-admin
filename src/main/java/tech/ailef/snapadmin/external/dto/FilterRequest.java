/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package tech.ailef.snapadmin.external.dto;

import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;

/**
 * Describes a request that contains parameters that are used
 * to filter results.  
 *
 */
public interface FilterRequest {
	/**
	 * Converts the request to a MultiValue map that can be 
	 * later converted into a query string
	 * @return
	 */
	public MultiValueMap<String, String> computeParams();
	
	/**
	 * Empty filtering request
	 * @return an empty map
	 */
	public static MultiValueMap<String, String> empty() {
		return new LinkedMultiValueMap<>();
	}
}
