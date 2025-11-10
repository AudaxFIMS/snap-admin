/*
 * Author: Alexey Semeshin aka Audax
 * https://github.com/AudaxFIMS/snap-admin
 */

package tech.ailef.snapadmin.external.dbmapping;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Id;
import jakarta.persistence.IdClass;
import tech.ailef.snapadmin.external.dbmapping.fields.DbField;
import tech.ailef.snapadmin.external.exceptions.SnapAdminException;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * Utility class for working with composite primary keys (@EmbeddedId and @IdClass)
 */
public class CompositeKeyUtils {

	/**
	 * Checks if an entity class uses a composite primary key
	 * @param entityClass the entity class to check
	 * @return true if the entity uses @EmbeddedId or @IdClass
	 */
	public static boolean hasCompositeKey(Class<?> entityClass) {
		return hasEmbeddedId(entityClass) || hasIdClass(entityClass);
	}

	/**
	 * Checks if an entity class uses @EmbeddedId
	 * @param entityClass the entity class to check
	 * @return true if the entity uses @EmbeddedId
	 */
	public static boolean hasEmbeddedId(Class<?> entityClass) {
		return Arrays.stream(entityClass.getDeclaredFields())
			.anyMatch(f -> f.getAnnotation(EmbeddedId.class) != null);
	}

	/**
	 * Checks if an entity class uses @IdClass
	 * @param entityClass the entity class to check
	 * @return true if the entity uses @IdClass
	 */
	public static boolean hasIdClass(Class<?> entityClass) {
		return entityClass.getAnnotation(IdClass.class) != null;
	}

	/**
	 * Gets the @EmbeddedId field from an entity class
	 * @param entityClass the entity class
	 * @return the @EmbeddedId field, or null if not found
	 */
	public static Field getEmbeddedIdField(Class<?> entityClass) {
		return Arrays.stream(entityClass.getDeclaredFields())
			.filter(f -> f.getAnnotation(EmbeddedId.class) != null)
			.findFirst()
			.orElse(null);
	}

	/**
	 * Gets all @Id fields from an entity class (used for @IdClass)
	 * @param entityClass the entity class
	 * @return list of @Id fields
	 */
	public static List<Field> getIdClassFields(Class<?> entityClass) {
		return Arrays.stream(entityClass.getDeclaredFields())
			.filter(f -> f.getAnnotation(Id.class) != null)
			.collect(Collectors.toList());
	}

	/**
	 * Gets the @IdClass type from an entity class
	 * @param entityClass the entity class
	 * @return the @IdClass type, or null if not found
	 */
	public static Class<?> getIdClassType(Class<?> entityClass) {
		IdClass idClassAnnotation = entityClass.getAnnotation(IdClass.class);
		return idClassAnnotation != null ? idClassAnnotation.value() : null;
	}

	/**
	 * Gets all fields that are part of the composite key (from @EmbeddedId class or entity @Id fields)
	 * @param entityClass the entity class
	 * @return list of key fields
	 */
	public static List<Field> getCompositeKeyFields(Class<?> entityClass) {
		if (hasEmbeddedId(entityClass)) {
			Field embeddedIdField = getEmbeddedIdField(entityClass);
			if (embeddedIdField != null) {
				Class<?> embeddedIdType = embeddedIdField.getType();
				// Filter out static and transient fields
				return Arrays.stream(embeddedIdType.getDeclaredFields())
					.filter(f -> !java.lang.reflect.Modifier.isStatic(f.getModifiers()))
					.filter(f -> !java.lang.reflect.Modifier.isTransient(f.getModifiers()))
					.collect(Collectors.toList());
			}
		} else if (hasIdClass(entityClass)) {
			return getIdClassFields(entityClass);
		}
		return new ArrayList<>();
	}

	/**
	 * Parses a composite key from URL string format (field1:value1,field2:value2,...)
	 * @param urlString the URL string to parse
	 * @param schema the entity schema
	 * @return CompositeKey object with parsed values
	 */
	public static CompositeKey parseFromUrl(String urlString, DbObjectSchema schema) {
		CompositeKey key = new CompositeKey();

		if (urlString == null || urlString.isBlank()) {
			throw new SnapAdminException("Cannot parse empty composite key string");
		}

		String[] pairs = urlString.split(",");
		for (String pair : pairs) {
			String[] parts = pair.split(":", 2);
			if (parts.length != 2) {
				throw new SnapAdminException("Invalid composite key format: " + pair +
					". Expected format: fieldName:value");
			}

			String fieldName = parts[0].trim();
			String rawValue = parts[1].trim();

			DbField field = schema.getFieldByName(fieldName);
			if (field == null) {
				throw new SnapAdminException("Field " + fieldName +
					" not found in schema " + schema.getSimpleClassName());
			}

			Object parsedValue = field.getType().parseValue(rawValue);
			key.put(fieldName, parsedValue);
		}

		return key;
	}

	/**
	 * Creates a composite key object instance for @EmbeddedId
	 * @param compositeKey the CompositeKey with field values
	 * @param entityClass the entity class
	 * @return the instantiated embedded ID object
	 */
	public static Object createEmbeddedIdInstance(CompositeKey compositeKey, Class<?> entityClass) {
		Field embeddedIdField = getEmbeddedIdField(entityClass);
		if (embeddedIdField == null) {
			throw new SnapAdminException("No @EmbeddedId field found in " + entityClass.getName());
		}

		Class<?> embeddedIdType = embeddedIdField.getType();

		try {
			Object embeddedIdInstance = embeddedIdType.getConstructor().newInstance();

			for (Map.Entry<String, Object> entry : compositeKey.getKeyFields().entrySet()) {
				try {
					Field field = embeddedIdType.getDeclaredField(entry.getKey());
					// Skip static and transient fields
					if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
						java.lang.reflect.Modifier.isTransient(field.getModifiers())) {
						continue;
					}
					field.setAccessible(true);
					field.set(embeddedIdInstance, entry.getValue());
				} catch (NoSuchFieldException e) {
					// Field not found, skip it
					continue;
				}
			}

			return embeddedIdInstance;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
				NoSuchMethodException e) {
			throw new SnapAdminException("Failed to create @EmbeddedId instance: " + e.getMessage(), e);
		}
	}

	/**
	 * Creates a composite key object instance for @IdClass
	 * @param compositeKey the CompositeKey with field values
	 * @param entityClass the entity class
	 * @return the instantiated ID class object
	 */
	public static Object createIdClassInstance(CompositeKey compositeKey, Class<?> entityClass) {
		Class<?> idClassType = getIdClassType(entityClass);
		if (idClassType == null) {
			throw new SnapAdminException("No @IdClass annotation found on " + entityClass.getName());
		}

		try {
			Object idClassInstance = idClassType.getConstructor().newInstance();

			for (Map.Entry<String, Object> entry : compositeKey.getKeyFields().entrySet()) {
				Field field = idClassType.getDeclaredField(entry.getKey());
				field.setAccessible(true);
				field.set(idClassInstance, entry.getValue());
			}

			return idClassInstance;
		} catch (InstantiationException | IllegalAccessException | InvocationTargetException |
				NoSuchMethodException | NoSuchFieldException e) {
			throw new SnapAdminException("Failed to create @IdClass instance: " + e.getMessage(), e);
		}
	}

	/**
	 * Extracts composite key values from an entity instance
	 * @param entity the entity instance
	 * @param entityClass the entity class
	 * @return CompositeKey with extracted values
	 */
	public static CompositeKey extractCompositeKey(Object entity, Class<?> entityClass) {
		CompositeKey key = new CompositeKey();

		try {
			if (hasEmbeddedId(entityClass)) {
				Field embeddedIdField = getEmbeddedIdField(entityClass);
				embeddedIdField.setAccessible(true);
				Object embeddedIdValue = embeddedIdField.get(entity);

				if (embeddedIdValue != null) {
					Class<?> embeddedIdType = embeddedIdField.getType();
					// Filter out static and transient fields
					for (Field field : embeddedIdType.getDeclaredFields()) {
						if (java.lang.reflect.Modifier.isStatic(field.getModifiers()) ||
							java.lang.reflect.Modifier.isTransient(field.getModifiers())) {
							continue;
						}
						field.setAccessible(true);
						Object value = field.get(embeddedIdValue);
						key.put(field.getName(), value);
					}
				}
			} else if (hasIdClass(entityClass)) {
				List<Field> idFields = getIdClassFields(entityClass);
				for (Field field : idFields) {
					field.setAccessible(true);
					Object value = field.get(entity);
					key.put(field.getName(), value);
				}
			}
		} catch (IllegalAccessException e) {
			throw new SnapAdminException("Failed to extract composite key: " + e.getMessage(), e);
		}

		return key;
	}

	/**
	 * Gets the list of DbFields that comprise the composite primary key
	 * @param schema the entity schema
	 * @return list of primary key DbFields
	 */
	public static List<DbField> getCompositeKeyDbFields(DbObjectSchema schema) {
		return schema.getFields().stream()
			.filter(DbField::isPrimaryKey)
			.collect(Collectors.toList());
	}
}
