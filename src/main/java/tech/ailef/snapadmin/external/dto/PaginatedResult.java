/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package tech.ailef.snapadmin.external.dto;

import java.util.List;

/**
 * A wrapper class that holds info about the current pagination and one page
 * of returned result.  
 */
public class PaginatedResult<T> {
	/**
	 * The pagination settings used to produce this output
	 */
	private PaginationInfo pagination;
	
	/**
	 * The list of results in the current page
	 */
	private List<T> results;

	public PaginatedResult(PaginationInfo pagination, List<T> page) {
		this.pagination = pagination;
		this.results = page;
	}

	/**
	 * Returns the pagination settings used to produce this output
	 * @return
	 */
	public PaginationInfo getPagination() {
		return pagination;
	}

	/**
	 * Returns the list of results in the current page
	 * @return
	 */
	public List<T> getResults() {
		return results;
	}
	
	/**
	 * Returns whether the results are empty
	 * @return
	 */
	public boolean isEmpty() {
		return results.isEmpty();
	}
	
	/**
	 * Returns the number of results for the current page
	 * @return
	 */
	public int getNumberOfResults() {
		return getResults().size();
	}
	
	
}
