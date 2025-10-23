/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package dev.semeshin.snapadmin.external.dbmapping.fields;

import java.sql.Date;
import java.time.LocalDate;
import java.time.format.DateTimeParseException;
import java.util.List;

import dev.semeshin.snapadmin.external.dto.CompareOperator;
import dev.semeshin.snapadmin.external.exceptions.SnapAdminException;

public class DateFieldType extends DbFieldType {
	@Override
	public String getFragmentName() {
		return "date";
	}

	@Override
	public Object parseValue(Object value) {
		if (value == null || value.toString().isBlank()) return null;
		try {
			LocalDate localDate = LocalDate.parse(value.toString());
			return Date.valueOf(localDate);
		} catch (DateTimeParseException e) {
			throw new SnapAdminException("Invalid date " + value, e);
		}
	}

	@Override
	public Class<?> getJavaClass() {
		return Date.class;
	}
	
	@Override
	public List<CompareOperator> getCompareOperators() {
		return List.of(CompareOperator.AFTER, CompareOperator.STRING_EQ, CompareOperator.BEFORE);
	}
}