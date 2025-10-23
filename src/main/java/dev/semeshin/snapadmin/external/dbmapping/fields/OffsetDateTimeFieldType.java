/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */

package dev.semeshin.snapadmin.external.dbmapping.fields;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.time.temporal.ChronoField;
import java.util.List;

import dev.semeshin.snapadmin.external.dto.CompareOperator;

public class OffsetDateTimeFieldType extends DbFieldType {
	private static final DateTimeFormatter formatter = new DateTimeFormatterBuilder()
			// Base pattern
			.appendPattern("yyyy-MM-dd'T'HH:mm")
			// Optional seconds part
			.optionalStart().appendPattern(":ss").optionalEnd()
			// Optional fractional seconds
			.optionalStart().appendFraction(ChronoField.NANO_OF_SECOND, 1, 9, true).optionalEnd()
			// Optional timezone offset (like +02:00 or Z)
			.optionalStart().appendOffset("+HH:MM", "Z").optionalEnd()
			// Default to UTC if no offset provided
			.parseDefaulting(ChronoField.OFFSET_SECONDS, 0)
			.toFormatter();

	@Override
	public String getFragmentName() {
		return "datetime";
	}

	@Override
	public Object parseValue(Object value) {
		if (value == null || value.toString().isBlank()) return null;
		return OffsetDateTime.parse(value.toString(), formatter);
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
