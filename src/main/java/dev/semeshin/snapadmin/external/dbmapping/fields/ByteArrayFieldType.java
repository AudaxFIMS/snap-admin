/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package dev.semeshin.snapadmin.external.dbmapping.fields;

import java.io.IOException;
import java.util.List;

import org.springframework.web.multipart.MultipartFile;

import dev.semeshin.snapadmin.external.dto.CompareOperator;
import dev.semeshin.snapadmin.external.exceptions.SnapAdminException;

public class ByteArrayFieldType extends DbFieldType {
	@Override
	public String getFragmentName() {
		return "file";
	}

	@Override
	public Object parseValue(Object value) {
		if (value == null || value.toString().isBlank()) return null;
		try {
			return ((MultipartFile)value).getBytes();
		} catch (IOException e) {
			throw new SnapAdminException(e);
		}
	}

	@Override
	public Class<?> getJavaClass() {
		return byte[].class;
	}
	
	@Override
	public List<CompareOperator> getCompareOperators() {
		throw new SnapAdminException("Binary fields are not comparable");
	}
}
