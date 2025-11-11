/* 
 * SnapAdmin - An automatically generated CRUD admin UI for Spring Boot apps
 * Copyright (C) 2023 Ailef (http://ailef.tech)
 * 

 */


package tech.ailef.snapadmin.external.dbmapping;

import java.io.IOException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.web.multipart.MultipartFile;

import jakarta.persistence.EntityManager;
import jakarta.persistence.Query;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.CriteriaUpdate;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import tech.ailef.snapadmin.external.dbmapping.fields.DbField;
import tech.ailef.snapadmin.external.dto.CompareOperator;
import tech.ailef.snapadmin.external.dto.QueryFilter;
import tech.ailef.snapadmin.external.exceptions.SnapAdminException;

@SuppressWarnings("rawtypes")
public class CustomJpaRepository extends SimpleJpaRepository {

	private EntityManager entityManager;
	
	private DbObjectSchema schema;
	
	@SuppressWarnings("unchecked")
	public CustomJpaRepository(DbObjectSchema schema, EntityManager em) {
		super(schema.getJavaClass(), em);
		this.entityManager = em;
		this.schema = schema;
	}
	
	@SuppressWarnings("unchecked")
	public long count(String q, Set<QueryFilter> queryFilters) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(Long.class);
        Root root = query.from(schema.getJavaClass());

        List<Predicate> finalPredicates = buildPredicates(q, queryFilters, cb, root);

        // Use count(root) instead of count(primaryKey) to avoid issues with @EmbeddedId
        query.select(cb.count(root))
            .where(
        		cb.and(
                		finalPredicates.toArray(new Predicate[finalPredicates.size()])
                	)
        		);
        
        Object o = entityManager.createQuery(query).getSingleResult();
        return (Long)o;
	}
	
	@SuppressWarnings("unchecked")
	public List<Object> search(String q, int page, int pageSize, String sortKey, String sortOrder, Set<QueryFilter> filters) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery query = cb.createQuery(schema.getJavaClass());
        Root root = query.from(schema.getJavaClass());
        
        List<Predicate> finalPredicates = buildPredicates(q, filters, cb, root);

	if (q != null && !q.isEmpty()) {
		// Get primary key path (handle @EmbeddedId)
		Path pkPath;
		DbField pkField = schema.getPrimaryKey();
		if (pkField.isPartOfEmbeddedId()) {
			String embeddedIdFieldName = pkField.getEmbeddedIdFieldName();
			pkPath = root.get(embeddedIdFieldName).get(pkField.getJavaName());
		} else {
			pkPath = root.get(pkField.getJavaName());
		}

		// Check if we can search by primary key (skip UUID as it's stored as binary)
		if (pkPath.getJavaType() == java.util.UUID.class) {
			// For UUID primary keys, only use the general field predicates (no PK search)
			query.select(root)
				.where(
					cb.and(finalPredicates.toArray(new Predicate[finalPredicates.size()]))
				);
		} else {
			// For non-UUID keys, include primary key search
			jakarta.persistence.criteria.Expression<String> pkPathAsString = cb.toString(pkPath);

			query.select(root)
				.where(
					cb.or(
						cb.and(finalPredicates.toArray(new Predicate[finalPredicates.size()])),
						// query search on primary key field (partial match)
						cb.like(
								cb.lower(pkPathAsString),
								"%" + q.toLowerCase() + "%"
						)
					)
				);
		}
	} else {
			query.select(root)
				.where(
					cb.and(finalPredicates.toArray(new Predicate[finalPredicates.size()])) // query search on String fields
				);
		}


        if (sortKey !=  null) {
        	// Get sort field path (handle @EmbeddedId)
        	DbField sortField = schema.getFieldByJavaName(sortKey);
        	Path sortPath;
        	if (sortField != null && sortField.isPartOfEmbeddedId()) {
        		String embeddedIdFieldName = sortField.getEmbeddedIdFieldName();
        		sortPath = root.get(embeddedIdFieldName).get(sortKey);
        	} else {
        		sortPath = root.get(sortKey);
        	}
        	query.orderBy(sortOrder.equals("DESC") ? cb.desc(sortPath) : cb.asc(sortPath));
        }
        
        return entityManager.createQuery(query).setMaxResults(pageSize)
        			.setFirstResult((page - 1) * pageSize).getResultList();
	}

	
	
	public List<Object> search(String query, Set<QueryFilter> filters) {
		return search(query, 1, Integer.MAX_VALUE, null, null, filters);
	}

	

	@SuppressWarnings("unchecked")
	public int update(DbObjectSchema schema, Map<String, String> params, Map<String, MultipartFile> files) {
		CriteriaBuilder cb = entityManager.getCriteriaBuilder();

		CriteriaUpdate update = cb.createCriteriaUpdate(schema.getJavaClass());

		Root root = update.from(schema.getJavaClass());

		boolean hasUpdate = false;
		for (DbField field : schema.getSortedFields()) {
			if (field.isPrimaryKey()) continue;
			if (field.isReadOnly()) continue;

			boolean keepValue = params.getOrDefault("__keep_" + field.getName(), "off").equals("on");
			if (keepValue) continue;

			String stringValue = params.get(field.getName());
			Object value = null;
			if (stringValue != null && stringValue.isBlank()) stringValue = null;
			if (stringValue != null) {
				value = field.getType().parseValue(stringValue);
			} else {
				try {
					MultipartFile file = files.get(field.getName());
					if (file != null) {
						if (file.isEmpty()) value = null;
						else value = file.getBytes();
					}
				} catch (IOException e) {
					throw new SnapAdminException(e);
				}
			}

			if (field.getConnectedSchema() != null) {
				if (value != null) {
					value = field.getConnectedSchema().getJpaRepository().findById(value).orElse(null);
				}
			}

			update.set(root.get(field.getJavaName()), value);
			hasUpdate = true;
		}

		if (!hasUpdate) return 0;

		// Build WHERE clause for composite or simple keys
		if (schema.hasCompositeKey() || schema.hasMultiplePrimaryKeys()) {
			// For composite keys, build WHERE with all PK fields
			List<Predicate> pkPredicates = new ArrayList<>();
			for (DbField pkField : schema.getPrimaryKeys()) {
				String paramValue = params.get(pkField.getName());
				Object parsedValue = pkField.getType().parseValue(paramValue);

				// For @EmbeddedId fields, use path through embedded object
				if (pkField.isPartOfEmbeddedId()) {
					String embeddedIdFieldName = pkField.getEmbeddedIdFieldName();
					pkPredicates.add(cb.equal(root.get(embeddedIdFieldName).get(pkField.getJavaName()), parsedValue));
				} else {
					// For @IdClass fields, use direct path
					pkPredicates.add(cb.equal(root.get(pkField.getJavaName()), parsedValue));
				}
			}
			update.where(cb.and(pkPredicates.toArray(new Predicate[0])));
		} else {
			// For simple keys
			String pkName = schema.getPrimaryKey().getJavaName();
			Object parsedPk = schema.getPrimaryKey().getType().parseValue(
					params.get(schema.getPrimaryKey().getName())
			);
			update.where(cb.equal(root.get(pkName), parsedPk));
		}

		Query query = entityManager.createQuery(update);
		return query.executeUpdate();
	}
	
	@SuppressWarnings("unchecked")
	private List<Predicate> buildPredicates(String q, Set<QueryFilter> queryFilters, CriteriaBuilder cb, Path root) {
		List<Predicate> finalPredicates = new ArrayList<>();

		List<DbField> stringFields = schema.getSortedFields();
        
        List<Predicate> queryPredicates = new ArrayList<>();
        if (q != null && !q.isBlank()) {
	        for (DbField f : stringFields) {
	        	Path path;
	        	if (f.isPartOfEmbeddedId()) {
	        		// For @EmbeddedId fields, use path through embedded object
	        		String embeddedIdFieldName = f.getEmbeddedIdFieldName();
	        		path = root.get(embeddedIdFieldName).get(f.getJavaName());
	        	} else {
	        		path = root.get(f.getJavaName());
	        	}

	        	// Skip UUID fields from text search (UUID stored as binary cannot be searched as text)
	        	if (path.getJavaType() == java.util.UUID.class) {
	        		continue;
	        	}

	        	// Convert to string for text search
	        	jakarta.persistence.criteria.Expression<String> pathAsString = cb.toString(path);
	        	queryPredicates.add(cb.like(cb.lower(pathAsString), "%" + q.toLowerCase() + "%"));
	        }

	        Predicate queryPredicate = cb.or(queryPredicates.toArray(new Predicate[queryPredicates.size()]));
	        finalPredicates.add(queryPredicate);
        }

        
        if (queryFilters == null) queryFilters = new HashSet<>();
        for (QueryFilter filter  : queryFilters) {
        	CompareOperator op = filter.getOp();
        	DbField dbField = filter.getField();
        	String fieldName = dbField.getJavaName();
        	String v = filter.getValue();

        	Object value = null;

        	if (!v.isBlank()) {
	        	try {
	        		value = dbField.getType().parseValue(v);
	        	} catch (Exception e) {
	        		throw new SnapAdminException("Invalid value `" + v + "` specified for field `" + dbField.getName() + "`");
	        	}
        	}

        	// Get the correct path for the field (handle @EmbeddedId)
        	Path fieldPath;
        	if (dbField.isPartOfEmbeddedId()) {
        		String embeddedIdFieldName = dbField.getEmbeddedIdFieldName();
        		fieldPath = root.get(embeddedIdFieldName).get(fieldName);
        	} else {
        		fieldPath = root.get(fieldName);
        	}

			if (op == CompareOperator.STRING_EQ) {
				if (value == null)
					finalPredicates.add(cb.isNull(fieldPath));
				else
					finalPredicates.add(cb.equal(cb.lower(cb.toString(fieldPath)), value.toString().toLowerCase()));
			} else if (op == CompareOperator.CONTAINS) {
				if (value != null)
					finalPredicates.add(
						cb.like(cb.lower(cb.toString(fieldPath)), "%" + value.toString().toLowerCase() + "%")
					);
			} else if (op == CompareOperator.EQ) {
				finalPredicates.add(
					cb.equal(fieldPath, value)
				);
			} else if (op == CompareOperator.GT) {
				if (value != null)
					finalPredicates.add(
						cb.greaterThan(fieldPath, value.toString())
					);
			} else if (op == CompareOperator.LT) {
				if (value != null)
					finalPredicates.add(
						cb.lessThan(fieldPath, value.toString())
					);
			} else if (op == CompareOperator.AFTER) {
				if (value instanceof LocalDate)
					finalPredicates.add(
						cb.greaterThan(fieldPath, (LocalDate)value)
					);
				else if (value instanceof LocalDateTime)
					finalPredicates.add(
						cb.greaterThan(fieldPath, (LocalDateTime)value)
					);

			} else if (op == CompareOperator.BEFORE) {
				if (value instanceof LocalDate)
					finalPredicates.add(
						cb.lessThan(fieldPath, (LocalDate)value)
					);
				else if (value instanceof LocalDateTime)
					finalPredicates.add(
						cb.lessThan(fieldPath, (LocalDateTime)value)
					);

			}
        }
        return finalPredicates;
	}
}
