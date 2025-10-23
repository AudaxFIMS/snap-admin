/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package dev.semeshin.snapadmin.external.dbmapping.fields;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import jakarta.persistence.EnumType;
import dev.semeshin.snapadmin.external.dto.CompareOperator;
import dev.semeshin.snapadmin.external.exceptions.SnapAdminException;

public class EnumFieldType extends DbFieldType {

	private EnumType type;
	
	private Class<?> klass;
	
	public EnumFieldType(Class<?> klass, EnumType type) {
		this.klass = klass;
		this.type = type;
	}
	
	@Override
	public String getFragmentName() {
		return "select";
	}
	
	@Override
	public List<?> getValues() {
		try {
			Method method = getJavaClass().getMethod("values");
			Object[] invoke = (Object[])method.invoke(null);
			return Arrays.stream(invoke).collect(Collectors.toList());
		} catch (NoSuchMethodException | SecurityException | InvocationTargetException 
				| IllegalAccessException | IllegalArgumentException e) {
			throw new SnapAdminException(e);
		}
	}

	@Override
	public Object parseValue(Object value) {
		if (value == null || value.toString().isBlank()) return null;
		
		try {
			Method valueOf = getJavaClass().getMethod("valueOf", String.class);
			return valueOf.invoke(null, value.toString());
		} catch (InvocationTargetException e) {
			if (e.getCause() instanceof IllegalArgumentException)
				throw new SnapAdminException("Invalid value " + value + " for enum type " + getSimpleClassName());
			else
				throw new SnapAdminException(e);
		} catch (NoSuchMethodException | SecurityException | IllegalAccessException e) {
			throw new SnapAdminException(e);
		}
	}

	public String getSimpleClassName() {
		return klass != null ? klass.getSimpleName() : "Unknown";
	}

	@Override
	public Class<?> getJavaClass() {
		return klass;
	}

	@Override
	public List<CompareOperator> getCompareOperators() {
		return List.of(CompareOperator.EQ);
	}
	
	public EnumType getType() {
		return type;
	}
}