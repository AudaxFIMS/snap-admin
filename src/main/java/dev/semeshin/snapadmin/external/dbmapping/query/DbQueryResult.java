/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps

 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package dev.semeshin.snapadmin.external.dbmapping.query;

import java.util.ArrayList;
import java.util.List;

/**
 * A wrapper for results returned by user-provided SQL queries run via
 * the SQL console.
 */
public class DbQueryResult {
	private List<DbQueryResultRow> rows;

	public DbQueryResult(List<DbQueryResultRow> rows) {
		this.rows = rows;
	}
	
	public List<DbQueryResultRow> getRows() {
		return rows;
	}
	
	public boolean isEmpty() {
		return rows.isEmpty();
	}
	
	public List<DbQueryOutputField> getSortedFields() {
		if (isEmpty()) {
			return new ArrayList<>();
		} else {
			return rows.get(0).getSortedFields();
		}
	}
	
	public int size() {
		return rows.size();
	}
	
	public void crop(int startOffset, int endOffset) {
		rows = rows.subList(startOffset, endOffset);
	}
}
