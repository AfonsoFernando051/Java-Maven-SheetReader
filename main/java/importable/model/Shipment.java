package importable.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a shipment entity for an order. Uses a simple String for 'status'.
 * Can be mapped from spreadsheet data (e.g., shipments.xlsx).
 */
public class Shipment {

	private Long shipmentId;
	private Long orderId; // ID do pedido associado
	private String carrier; // Transportadora (ex: "Correios", "FedEx")
	private String trackingCode;
	private String status; // Status como String simples (ex: "Processing", "Shipped")
	private LocalDate estimatedDeliveryDate;

	/** Default constructor. */
	public Shipment() {
	}

	/** Full constructor */
	public Shipment(Long shipmentId, Long orderId, String carrier, String trackingCode, String status,
			LocalDate estimatedDeliveryDate) {
		this.shipmentId = shipmentId;
		this.orderId = orderId;
		this.carrier = carrier;
		this.trackingCode = trackingCode;
		this.status = status;
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	// Getters and Setters
	public Long getShipmentId() {
		return shipmentId;
	}

	public void setShipmentId(Long shipmentId) {
		this.shipmentId = shipmentId;
	}

	public Long getOrderId() {
		return orderId;
	}

	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}

	public String getCarrier() {
		return carrier;
	}

	public void setCarrier(String carrier) {
		this.carrier = carrier;
	}

	public String getTrackingCode() {
		return trackingCode;
	}

	public void setTrackingCode(String trackingCode) {
		this.trackingCode = trackingCode;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public LocalDate getEstimatedDeliveryDate() {
		return estimatedDeliveryDate;
	}

	public void setEstimatedDeliveryDate(LocalDate estimatedDeliveryDate) {
		this.estimatedDeliveryDate = estimatedDeliveryDate;
	}

	// Setter para String (planilha) que converte para LocalDate
	public void setEstimatedDeliveryDate(String dateString) {
		try {
			this.estimatedDeliveryDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (Exception e) {
			System.err.println("Failed to parse date: " + dateString);
			this.estimatedDeliveryDate = null;
		}
	}

	@Override
	public String toString() {
		return String.format("Shipment ID: %d (Order: %d) - Carrier: %s [%s] - Status: %s", shipmentId, orderId,
				carrier, trackingCode, status);
	}
}