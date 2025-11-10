package tech.ailef.snapadmin;

import org.junit.jupiter.api.Test;
import tech.ailef.snapadmin.external.dbmapping.CompositeKey;
import tech.ailef.snapadmin.external.dbmapping.CompositeKeyUtils;
import tech.ailef.snapadmin.testentities.OrderEntity;
import tech.ailef.snapadmin.testentities.OrderId;
import tech.ailef.snapadmin.testentities.UserRole;
import tech.ailef.snapadmin.testentities.UserRoleId;

import java.lang.reflect.Field;
import java.math.BigDecimal;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests for composite key functionality
 */
public class CompositeKeyTest {

	@Test
	public void testCompositeKeyCreation() {
		CompositeKey key = new CompositeKey();
		key.put("customerId", 123L);
		key.put("orderNumber", "ORD-001");

		assertEquals(2, key.size());
		assertEquals(123L, key.get("customerId"));
		assertEquals("ORD-001", key.get("orderNumber"));
	}

	@Test
	public void testCompositeKeyUrlString() {
		CompositeKey key = new CompositeKey();
		key.put("customer_id", 123L);
		key.put("order_number", "ORD-001");

		String urlString = key.toUrlString();
		assertTrue(urlString.contains("customer_id:123"));
		assertTrue(urlString.contains("order_number:ORD-001"));
	}

	@Test
	public void testHasEmbeddedId() {
		assertTrue(CompositeKeyUtils.hasEmbeddedId(OrderEntity.class));
		assertFalse(CompositeKeyUtils.hasEmbeddedId(UserRole.class));
	}

	@Test
	public void testHasIdClass() {
		assertTrue(CompositeKeyUtils.hasIdClass(UserRole.class));
		assertFalse(CompositeKeyUtils.hasIdClass(OrderEntity.class));
	}

	@Test
	public void testHasCompositeKey() {
		assertTrue(CompositeKeyUtils.hasCompositeKey(OrderEntity.class));
		assertTrue(CompositeKeyUtils.hasCompositeKey(UserRole.class));
	}

	@Test
	public void testGetEmbeddedIdField() {
		var field = CompositeKeyUtils.getEmbeddedIdField(OrderEntity.class);
		assertNotNull(field);
		assertEquals("id", field.getName());
	}

	@Test
	public void testGetIdClassType() {
		var idClass = CompositeKeyUtils.getIdClassType(UserRole.class);
		assertNotNull(idClass);
		assertEquals(UserRoleId.class, idClass);
	}

	@Test
	public void testGetIdClassFields() {
		var fields = CompositeKeyUtils.getIdClassFields(UserRole.class);
		assertEquals(2, fields.size());

		var fieldNames = fields.stream().map(Field::getName).toList();
		assertTrue(fieldNames.contains("userId"));
		assertTrue(fieldNames.contains("roleCode"));
	}

	@Test
	public void testExtractCompositeKeyFromEmbeddedId() {
		OrderId orderId = new OrderId(100L, "ORD-999");
		OrderEntity order = new OrderEntity(orderId, "Laptop", 2, new BigDecimal("2000.00"));

		CompositeKey extracted = CompositeKeyUtils.extractCompositeKey(order, OrderEntity.class);

		assertNotNull(extracted);
		assertEquals(2, extracted.size());
		assertEquals(100L, extracted.get("customerId"));
		assertEquals("ORD-999", extracted.get("orderNumber"));
	}

	@Test
	public void testExtractCompositeKeyFromIdClass() {
		UserRole userRole = new UserRole(42L, "ADMIN", "John Doe", "Administrator");

		CompositeKey extracted = CompositeKeyUtils.extractCompositeKey(userRole, UserRole.class);

		assertNotNull(extracted);
		assertEquals(2, extracted.size());
		assertEquals(42L, extracted.get("userId"));
		assertEquals("ADMIN", extracted.get("roleCode"));
	}

	@Test
	public void testCreateEmbeddedIdInstance() {
		CompositeKey key = new CompositeKey();
		key.put("customerId", 200L);
		key.put("orderNumber", "ORD-888");

		Object embeddedId = CompositeKeyUtils.createEmbeddedIdInstance(key, OrderEntity.class);

		assertNotNull(embeddedId);
		assertInstanceOf(OrderId.class, embeddedId);

		OrderId orderId = (OrderId) embeddedId;
		assertEquals(200L, orderId.getCustomerId());
		assertEquals("ORD-888", orderId.getOrderNumber());
	}

	@Test
	public void testCreateIdClassInstance() {
		CompositeKey key = new CompositeKey();
		key.put("userId", 99L);
		key.put("roleCode", "USER");

		Object idClass = CompositeKeyUtils.createIdClassInstance(key, UserRole.class);

		assertNotNull(idClass);
		assertInstanceOf(UserRoleId.class, idClass);

		UserRoleId userRoleId = (UserRoleId) idClass;
		assertEquals(99L, userRoleId.getUserId());
		assertEquals("USER", userRoleId.getRoleCode());
	}
}
