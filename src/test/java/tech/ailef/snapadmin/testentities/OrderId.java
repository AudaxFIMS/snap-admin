package tech.ailef.snapadmin.testentities;

import java.io.Serializable;
import java.util.Objects;

import jakarta.persistence.Embeddable;

/**
 * Composite primary key for OrderEntity using @EmbeddedId
 */
@Embeddable
public class OrderId implements Serializable {
	private static final long serialVersionUID = 1L;

	private Long customerId;
	private String orderNumber;

	public OrderId() {
	}

	public OrderId(Long customerId, String orderNumber) {
		this.customerId = customerId;
		this.orderNumber = orderNumber;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public String getOrderNumber() {
		return orderNumber;
	}

	public void setOrderNumber(String orderNumber) {
		this.orderNumber = orderNumber;
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;
		OrderId orderId = (OrderId) o;
		return Objects.equals(customerId, orderId.customerId) &&
			Objects.equals(orderNumber, orderId.orderNumber);
	}

	@Override
	public int hashCode() {
		return Objects.hash(customerId, orderNumber);
	}

	@Override
	public String toString() {
		return "OrderId{customerId=" + customerId + ", orderNumber='" + orderNumber + "'}";
	}
}
