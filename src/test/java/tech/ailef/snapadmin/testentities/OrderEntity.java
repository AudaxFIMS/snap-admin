package tech.ailef.snapadmin.testentities;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import jakarta.persistence.EmbeddedId;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;

/**
 * Test entity using @EmbeddedId for composite primary key
 */
@Entity
@Table(name = "orders")
public class OrderEntity {

	@EmbeddedId
	private OrderId id;

	private String productName;
	private Integer quantity;
	private BigDecimal totalAmount;
	private LocalDateTime orderDate;
	private String status;

	public OrderEntity() {
	}

	public OrderEntity(OrderId id, String productName, Integer quantity, BigDecimal totalAmount) {
		this.id = id;
		this.productName = productName;
		this.quantity = quantity;
		this.totalAmount = totalAmount;
		this.orderDate = LocalDateTime.now();
		this.status = "PENDING";
	}

	public OrderId getId() {
		return id;
	}

	public void setId(OrderId id) {
		this.id = id;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public BigDecimal getTotalAmount() {
		return totalAmount;
	}

	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}

	public LocalDateTime getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDateTime orderDate) {
		this.orderDate = orderDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	@Override
	public String toString() {
		return "OrderEntity{" +
			"id=" + id +
			", productName='" + productName + '\'' +
			", quantity=" + quantity +
			", totalAmount=" + totalAmount +
			", orderDate=" + orderDate +
			", status='" + status + '\'' +
			'}';
	}
}
