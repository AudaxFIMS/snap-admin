/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package dev.semeshin.snapadmin.external.dto;

import java.util.Objects;

import dev.semeshin.snapadmin.external.dbmapping.fields.DbField;
import dev.semeshin.snapadmin.external.exceptions.SnapAdminException;

/**
 * A single filter in a FacetedSearchRequest. This describes a 
 * single boolean condition on the value of a specific field.
 */
public class QueryFilter {
	private DbField field;
	
	private CompareOperator op;
	
	private String value;
	
	public QueryFilter(DbField field, CompareOperator op, String value) {
		if (field == null)
			throw new SnapAdminException("Trying to build QueryFilter with null `field`");
		this.field = field;
		this.op = op;
		this.value = value;
	}

	/**
	 * Returns the field of the boolean condition
	 * @return
	 */
	public DbField getField() {
		return field;
	}

	/**
	 * Returns the operator of the boolean condition
	 * @return
	 */
	public CompareOperator getOp() {
		return op;
	}

	/**
	 * Returns the value of the boolean condition
	 * @return
	 */
	public String getValue() {
		return value;
	}

	/**
	 * Provides a readable version of this query filter, customized
	 * based on field type and/or operator.
	 */
	@Override
	public String toString() {
		if (value != null && !value.toString().isBlank()) {
			String displayValue = value;
			if (value.length() > 18) {
				displayValue = value.substring(0, 4) + "..." + value.substring(value.length() - 4);
			}
			return "'" + field.getName() + "' " + op.getDisplayName() + " '" + displayValue + "'";
		} else {
			if (op != CompareOperator.STRING_EQ && op != CompareOperator.EQ) {
				return "'" + field.getName() + "' " + op.getDisplayName() + " NULL";
			} else {
				return "'" + field.getName() + "' IS NULL";
			}
		}
	}

	@Override
	public int hashCode() {
		return Objects.hash(field, op, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		QueryFilter other = (QueryFilter) obj;
		return Objects.equals(field, other.field) && op == other.op && Objects.equals(value, other.value);
	}
	

}
