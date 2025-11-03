package importable.model;

/**
 * Represents an inventory item, linking a product to a warehouse and quantity.
 * Can be mapped from spreadsheet data (e.g., inventory.xlsx).
 */
public class Inventory {

	private Long productId;
	private Long warehouseId;
	private Integer quantity;
	private String locationCode; // Ex: Corredor A, Prateleira 3

	/** Default constructor. */
	public Inventory() {
	}

	/** Full constructor */
	public Inventory(Long productId, Long warehouseId, Integer quantity, String locationCode) {
		this.productId = productId;
		this.warehouseId = warehouseId;
		this.quantity = quantity;
		this.locationCode = locationCode;
	}

	// Getters and Setters
	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public Integer getQuantity() {
		return quantity;
	}

	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}

	public String getLocationCode() {
		return locationCode;
	}

	public void setLocationCode(String locationCode) {
		this.locationCode = locationCode;
	}

	@Override
	public String toString() {
		return String.format("Product ID: %d (Warehouse: %d) - Qty: %d [Location: %s]", productId, warehouseId,
				quantity, locationCode);
	}
}