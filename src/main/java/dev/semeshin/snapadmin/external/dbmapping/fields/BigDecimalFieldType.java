/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package dev.semeshin.snapadmin.external.dbmapping.fields;

import java.math.BigDecimal;
import java.util.List;

import dev.semeshin.snapadmin.external.dto.CompareOperator;

public class BigDecimalFieldType extends DbFieldType {
	@Override
	public String getFragmentName() {
		return "number";
	}

	@Override
	public Object parseValue(Object value) {
		if (value == null || value.toString().isBlank()) return null;
		return new BigDecimal(value.toString());
	}

	@Override
	public Class<?> getJavaClass() {
		return BigDecimal.class;
	}
	
	@Override
	public List<CompareOperator> getCompareOperators() {
		return List.of(CompareOperator.GT, CompareOperator.EQ, CompareOperator.LT);
	}
}
