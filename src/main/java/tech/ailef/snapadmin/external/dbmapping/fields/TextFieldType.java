/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package tech.ailef.snapadmin.external.dbmapping.fields;

import java.util.List;

import tech.ailef.snapadmin.external.dto.CompareOperator;

public class TextFieldType extends DbFieldType {
	@Override
	public String getFragmentName() {
		return "textarea";
	}

	@Override
	public Object parseValue(Object value) {
		if (value == null || value.toString().isBlank()) return null;
		return value.toString();
	}

	@Override
	public Class<?> getJavaClass() {
		return String.class;
	}

	@Override
	public List<CompareOperator> getCompareOperators() {
		return List.of(CompareOperator.CONTAINS, CompareOperator.STRING_EQ);
	}
}
