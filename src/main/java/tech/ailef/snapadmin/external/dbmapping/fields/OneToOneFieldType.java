/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package tech.ailef.snapadmin.external.dbmapping.fields;

import java.util.List;

import jakarta.persistence.OneToOne;
import tech.ailef.snapadmin.external.dto.CompareOperator;
import tech.ailef.snapadmin.external.exceptions.SnapAdminException;

public class OneToOneFieldType extends DbFieldType {
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
		return OneToOne.class;
	}
	
	@Override
	public boolean isRelationship() {
		return true;
	}
	
	@Override
	public String toString() {
		return "One to One";
	}
	
	@Override
	public List<CompareOperator> getCompareOperators() {
		throw new SnapAdminException();
	}
}
