/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package dev.semeshin.snapadmin.external.dbmapping.fields;

import java.util.List;

import dev.semeshin.snapadmin.external.dto.CompareOperator;

public class BooleanFieldType extends DbFieldType {
	@Override
	public String getFragmentName() {
		return "text";
	}

	@Override
	public Object parseValue(Object value) {
		if (value == null || value.toString().isBlank()) return null;
		return Boolean.parseBoolean(value.toString());
	}

	@Override
	public Class<?> getJavaClass() {
		return Boolean.class;
	}
	
	@Override
	public List<CompareOperator> getCompareOperators() {
		return List.of(CompareOperator.EQ);
	}
}