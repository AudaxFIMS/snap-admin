/*
 * Author: Alexey Semeshin aka Audax
 * https://github.com/AudaxFIMS/snap-admin
 */

package tech.ailef.snapadmin.external.dbmapping;

import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Represents a composite primary key for entities that use @EmbeddedId or @IdClass.
 * Stores multiple field names and their corresponding values.
 */
public class CompositeKey {
	/**
	 * Map of field names to their values. LinkedHashMap is used to maintain
	 * insertion order for consistent serialization.
	 */
	private final Map<String, Object> keyFields;

	/**
	 * Creates a new CompositeKey with the specified field values
	 * @param keyFields map of field names to values
	 */
	public CompositeKey(Map<String, Object> keyFields) {
		this.keyFields = new LinkedHashMap<>(keyFields);
	}

	/**
	 * Creates an empty CompositeKey
	 */
	public CompositeKey() {
		this.keyFields = new LinkedHashMap<>();
	}

	/**
	 * Adds or updates a field value in the composite key
	 * @param fieldName the name of the field
	 * @param value the value of the field
	 */
	public void put(String fieldName, Object value) {
		keyFields.put(fieldName, value);
	}

	/**
	 * Gets a field value from the composite key
	 * @param fieldName the name of the field
	 * @return the value of the field, or null if not found
	 */
	public Object get(String fieldName) {
		return keyFields.get(fieldName);
	}

	/**
	 * Returns an unmodifiable map of all key fields
	 * @return map of field names to values
	 */
	public Map<String, Object> getKeyFields() {
		return Collections.unmodifiableMap(keyFields);
	}

	/**
	 * Returns the number of fields in this composite key
	 * @return the number of fields
	 */
	public int size() {
		return keyFields.size();
	}

	/**
	 * Checks if this composite key is empty
	 * @return true if empty, false otherwise
	 */
	public boolean isEmpty() {
		return keyFields.isEmpty();
	}

	/**
	 * Serializes this composite key to a URL-safe string format.
	 * Format: field1:value1,field2:value2,...
	 *
	 * @return URL-safe string representation
	 */
	public String toUrlString() {
		return keyFields.entrySet().stream()
			.map(e -> e.getKey() + ":" + e.getValue())
			.collect(Collectors.joining(","));
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		CompositeKey that = (CompositeKey) o;
		return Objects.equals(keyFields, that.keyFields);
	}

	@Override
	public int hashCode() {
		return Objects.hash(keyFields);
	}

	@Override
	public String toString() {
		return "CompositeKey{" + keyFields + "}";
	}
}
