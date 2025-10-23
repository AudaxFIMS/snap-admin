/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package dev.semeshin.snapadmin.external.dbmapping.fields;

import java.util.List;

import dev.semeshin.snapadmin.external.dto.CompareOperator;
import dev.semeshin.snapadmin.external.exceptions.SnapAdminException;

public class ComputedFieldType extends DbFieldType {
	@Override
	public String getFragmentName() {
		throw new UnsupportedOperationException();
	}

	@Override
	public Object parseValue(Object value) {
		throw new UnsupportedOperationException();
	}

	@Override
	public Class<?> getJavaClass() {
		throw new UnsupportedOperationException();
	}
	
	@Override
	public List<CompareOperator> getCompareOperators() {
		throw new SnapAdminException();
	}
}