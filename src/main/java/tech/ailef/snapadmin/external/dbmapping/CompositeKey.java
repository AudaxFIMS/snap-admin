/*
 * Author: Alexey Semeshin aka Audax
 * https://github.com/AudaxFIMS/snap-admin
 */

package tech.ailef.snapadmin.external.dbmapping;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.Base64;
import java.util.Collections;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Objects;

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
	 * The key is encoded as base64-encoded JSON to safely handle any special characters.
	 *
	 * Note: This method uses Java field names as keys. If you need database names,
	 * use toUrlString(DbObjectSchema schema) instead.
	 *
	 * @return URL-safe base64-encoded JSON string with Java field names
	 */
	public String toUrlString() {
		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(keyFields);
			return Base64.getUrlEncoder().withoutPadding().encodeToString(json.getBytes());
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to serialize composite key to JSON", e);
		}
	}

	/**
	 * Serializes this composite key to a URL-safe string format using database field names.
	 * The key is encoded as base64-encoded JSON to safely handle any special characters.
	 *
	 * @param schema the schema to map Java names to database names
	 * @return URL-safe base64-encoded JSON string with database field names
	 */
	public String toUrlString(DbObjectSchema schema) {
		// Convert Java field names to database field names
		Map<String, Object> dbFieldMap = new LinkedHashMap<>();
		for (Map.Entry<String, Object> entry : keyFields.entrySet()) {
			tech.ailef.snapadmin.external.dbmapping.fields.DbField field =
				schema.getFieldByJavaName(entry.getKey());
			String dbFieldName = (field != null) ? field.getName() : entry.getKey();
			dbFieldMap.put(dbFieldName, entry.getValue());
		}

		try {
			ObjectMapper mapper = new ObjectMapper();
			String json = mapper.writeValueAsString(dbFieldMap);
			return Base64.getUrlEncoder().withoutPadding().encodeToString(json.getBytes());
		} catch (JsonProcessingException e) {
			throw new RuntimeException("Failed to serialize composite key to JSON", e);
		}
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
