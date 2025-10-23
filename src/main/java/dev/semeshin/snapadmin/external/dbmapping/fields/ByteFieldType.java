/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package dev.semeshin.snapadmin.external.dbmapping.fields;

import java.util.List;

import dev.semeshin.snapadmin.external.dto.CompareOperator;
import dev.semeshin.snapadmin.external.exceptions.SnapAdminException;

public class ByteFieldType extends DbFieldType {
	@Override
	public String getFragmentName() {
		return "number";
	}

	@Override
	public Object parseValue(Object value) {
		if (value == null || value.toString().isBlank()) return null;
		return value.toString().getBytes()[0];
	}

	@Override
	public Class<?> getJavaClass() {
		return byte.class;
	}
	
	@Override
	public List<CompareOperator> getCompareOperators() {
		throw new SnapAdminException("Binary fields are not comparable");
	}
}