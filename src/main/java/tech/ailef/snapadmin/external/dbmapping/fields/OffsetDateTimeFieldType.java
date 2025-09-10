/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package tech.ailef.snapadmin.external.dbmapping.fields;

import java.time.OffsetDateTime;
import java.util.List;

import tech.ailef.snapadmin.external.dto.CompareOperator;

public class OffsetDateTimeFieldType extends DbFieldType {
	@Override
	public String getFragmentName() {
		return "datetime";
	}

	@Override
	public Object parseValue(Object value) {
		if (value == null || value.toString().isBlank()) return null;
		return OffsetDateTime.parse(value.toString());
	}

	@Override
	public Class<?> getJavaClass() {
		return OffsetDateTime.class;
	}

	@Override
	public List<CompareOperator> getCompareOperators() {
		return List.of(CompareOperator.AFTER, CompareOperator.STRING_EQ, CompareOperator.BEFORE);
	}
}
