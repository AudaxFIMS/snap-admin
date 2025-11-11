package tech.ailef.snapadmin;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import tech.ailef.snapadmin.external.dbmapping.DbObject;
import tech.ailef.snapadmin.external.dbmapping.DbObjectSchema;
import tech.ailef.snapadmin.external.dbmapping.fields.DbField;
import tech.ailef.snapadmin.external.dbmapping.fields.LongFieldType;
import tech.ailef.snapadmin.external.dbmapping.fields.StringFieldType;
import tech.ailef.snapadmin.testentities.OrderEntity;
import tech.ailef.snapadmin.testentities.OrderId;

import java.math.BigDecimal;
import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

/**
 * Unit test for DbObject with @EmbeddedId fields
 */
public class DbObjectEmbeddedIdTest {

	@Test
	public void testDbObjectHasMethodWithEmbeddedIdFields() throws Exception {
		// Create a test entity
		OrderId orderId = new OrderId(100L, "ORD-TEST-001");
		OrderEntity order = new OrderEntity(orderId, "Test Product", 1, new BigDecimal("99.99"));

		// Create mock schema
		DbObjectSchema schema = mock(DbObjectSchema.class);

		// Create mock DbFields for embedded ID fields
		DbField customerIdField = createMockEmbeddedIdField("customerId", "customer_id", "id");
		DbField orderNumberField = createMockEmbeddedIdField("orderNumber", "order_number", "id");

		// Configure mock schema
		when(schema.getFieldByJavaName("customerId")).thenReturn(customerIdField);
		when(schema.getFieldByJavaName("orderNumber")).thenReturn(orderNumberField);
		when(schema.getFieldByJavaName("id")).thenReturn(null); // @EmbeddedId field is not in schema

		// Create DbObject
		DbObject dbObject = new DbObject(order, schema);

		// Test has() method - should return true for both fields
		assertTrue(dbObject.has(customerIdField), "DbObject.has() should return true for customerId field");
		assertTrue(dbObject.has(orderNumberField), "DbObject.has() should return true for orderNumber field");
	}

	@Test
	public void testDbObjectGetMethodWithEmbeddedIdFields() throws Exception {
		// Create a test entity
		OrderId orderId = new OrderId(200L, "ORD-TEST-002");
		OrderEntity order = new OrderEntity(orderId, "Another Product", 5, new BigDecimal("199.95"));

		// Create mock schema
		DbObjectSchema schema = mock(DbObjectSchema.class);

		// Create mock DbFields for embedded ID fields
		DbField customerIdField = createMockEmbeddedIdField("customerId", "customer_id", "id");
		DbField orderNumberField = createMockEmbeddedIdField("orderNumber", "order_number", "id");

		// Configure mock schema
		when(schema.getFieldByJavaName("customerId")).thenReturn(customerIdField);
		when(schema.getFieldByJavaName("orderNumber")).thenReturn(orderNumberField);
		when(schema.getFieldByJavaName("id")).thenReturn(null); // @EmbeddedId field is not in schema

		// Create DbObject
		DbObject dbObject = new DbObject(order, schema);

		// Test get() method - should return correct values
		var customerIdValue = dbObject.get(customerIdField);
		var orderNumberValue = dbObject.get(orderNumberField);

		assertNotNull(customerIdValue, "customerId value should not be null");
		assertNotNull(orderNumberValue, "orderNumber value should not be null");

		assertEquals(200L, customerIdValue.getValue(), "customerId value should be 200");
		assertEquals("ORD-TEST-002", orderNumberValue.getValue(), "orderNumber value should be ORD-TEST-002");
	}

	@Test
	public void testDbObjectWithNullEmbeddedId() throws Exception {
		// Create entity with null embedded ID
		OrderEntity order = new OrderEntity();
		order.setProductName("Product with null ID");

		// Create mock schema
		DbObjectSchema schema = mock(DbObjectSchema.class);

		// Create mock DbFields for embedded ID fields
		DbField customerIdField = createMockEmbeddedIdField("customerId", "customer_id", "id");
		DbField orderNumberField = createMockEmbeddedIdField("orderNumber", "order_number", "id");

		// Configure mock schema
		when(schema.getFieldByJavaName("customerId")).thenReturn(customerIdField);
		when(schema.getFieldByJavaName("orderNumber")).thenReturn(orderNumberField);
		when(schema.getFieldByJavaName("id")).thenReturn(null); // @EmbeddedId field is not in schema

		// Create DbObject
		DbObject dbObject = new DbObject(order, schema);

		// When embedded ID is null, has() should return false
		assertFalse(dbObject.has(customerIdField), "DbObject.has() should return false when embedded ID is null");
		assertFalse(dbObject.has(orderNumberField), "DbObject.has() should return false when embedded ID is null");
	}

	/**
	 * Helper method to create a mock DbField for an @EmbeddedId field
	 */
	private DbField createMockEmbeddedIdField(String javaName, String dbName, String embeddedIdFieldName) throws Exception {
		// Get the actual Field from OrderId class
		Field primitiveField = OrderId.class.getDeclaredField(javaName);

		// Create the field type
		Object fieldType = javaName.equals("customerId") ? new LongFieldType() : new StringFieldType();

		// Create a real DbField instance (not a mock)
		DbField field = new DbField(javaName, dbName, primitiveField, (tech.ailef.snapadmin.external.dbmapping.fields.DbFieldType) fieldType, null, null);
		field.setEmbeddedIdFieldName(embeddedIdFieldName); // Mark as part of @EmbeddedId
		field.setPrimaryKey(true);
		field.setNullable(false);

		return field;
	}
}
