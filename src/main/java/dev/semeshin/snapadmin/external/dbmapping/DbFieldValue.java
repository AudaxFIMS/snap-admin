/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package dev.semeshin.snapadmin.external.dbmapping;

import java.time.Instant;
import java.time.LocalDateTime;
import java.time.OffsetDateTime;
import java.time.ZoneId;
import java.util.Objects;

import com.fasterxml.jackson.annotation.JsonIgnore;

import dev.semeshin.snapadmin.external.dbmapping.fields.DbField;

/**
 * Wrapper for the value of a field 
 *
 */
public class DbFieldValue {
	private Object value;
	
	private DbField field;

	public DbFieldValue(Object value, DbField field) {
		this.value = value;
		this.field = field;
	}

	public Object getValue() {
		/*
		 * Special handling of OffsetDateTime and Instant to be compatabile
		 * with the HTML datetime-local input field: we "cast"
		 * it to a LocalDateTime so the toString() method will
		 * not produce the ending "Z" which prevents the datepicker
		 * to be autofilled on edit pages.
		 */
		if (value instanceof OffsetDateTime) {
			return LocalDateTime.from((OffsetDateTime)value);
		}
		
		if (value instanceof Instant) {
			Instant i = (Instant)value;
			LocalDateTime result = LocalDateTime.ofInstant(i, ZoneId.of("UTC"));
			return result;
		}
		
		return value;
	}
	
	public String getFormattedValue() {
		if (value == null) return null;
		
		if (field.getFormat() == null) {
			return value.toString();
		} else {
			return String.format(field.getFormat(), value);
		}
	}

	public DbField getField() {
		return field;
	}
	
	@JsonIgnore
	public String getJavaName() {
		return field.getPrimitiveField().getName();
	}

	@Override
	public String toString() {
		return "DbFieldValue [value=" + value + ", field=" + field + "]";
	}

	@Override
	public int hashCode() {
		return Objects.hash(field, value);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		DbFieldValue other = (DbFieldValue) obj;
		return Objects.equals(field, other.field) && Objects.equals(value, other.value);
	}
	
}
