/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package dev.semeshin.snapadmin.external.dbmapping.fields;

import java.util.List;

import dev.semeshin.snapadmin.external.dto.CompareOperator;

public class CharFieldType extends DbFieldType {
	@Override
	public String getFragmentName() {
		return "char";
	}

	@Override
	public Object parseValue(Object value) {
		if (value == null || value.toString().isBlank()) return null;
		if (value.toString().isBlank()) return null;
		return value.toString().charAt(0);
	}

	@Override
	public Class<?> getJavaClass() {
		return char.class;
	}
	
	@Override
	public List<CompareOperator> getCompareOperators() {
		return List.of(CompareOperator.STRING_EQ);
	}
}