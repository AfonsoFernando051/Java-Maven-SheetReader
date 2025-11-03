package importable.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an order entity. Can be mapped from spreadsheet data (e.g.,
 * orders.xlsx).
 */
public class Order {

	private Long orderId;
	private Long customerId; // ID do cliente que fez o pedido
	private LocalDate orderDate;
	private Double totalValue;
	private String status;

	/** Default constructor. */
	public Order() {
	}

	/** Full constructor */
	public Order(Long orderId, Long customerId, LocalDate orderDate, Double totalValue, String status) {
		this.orderId = orderId;
		this.customerId = customerId;
		this.orderDate = orderDate;
		this.totalValue = totalValue;
		this.status = status;
	}

	// Getters and Setters
	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public Long getCustomerId() {
		return customerId;
	}

	public void setCustomerId(Long customerId) {
		this.customerId = customerId;
	}

	public LocalDate getOrderDate() {
		return orderDate;
	}

	public void setOrderDate(LocalDate orderDate) {
		this.orderDate = orderDate;
	}

	public Double getTotalValue() {
		return totalValue;
	}

	public void setTotalValue(Double totalValue) {
		this.totalValue = totalValue;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// Setter para String (planilha) que converte para LocalDate
	public void setOrderDate(String dateString) {
		// Assume o formato AAAA-MM-DD
		try {
			this.orderDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (Exception e) {
			System.err.println("Failed to parse date: " + dateString);
			this.orderDate = null;
		}
	}

	@Override
	public String toString() {
		return String.format("Order ID: %d (Customer: %d) - Value: %.2f - Status: %s", orderId, customerId, totalValue,
				status);
	}
}