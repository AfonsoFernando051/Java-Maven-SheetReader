package importable.model;

/**
 * Represents a warehouse location. Can be mapped from spreadsheet data (e.g.,
 * warehouses.xlsx).
 */
public class Warehouse {

	private Long warehouseId;
	private String name;
	private String city;
	private Integer capacity; // Ex: Pallet capacity or square meters

	/** Default constructor. */
	public Warehouse() {
	}

	/** Full constructor */
	public Warehouse(Long warehouseId, String name, String city, Integer capacity) {
		this.warehouseId = warehouseId;
		this.name = name;
		this.city = city;
		this.capacity = capacity;
	}

	// Getters and Setters
	public Long getWarehouseId() {
		return warehouseId;
	}

	public void setWarehouseId(Long warehouseId) {
		this.warehouseId = warehouseId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public Integer getCapacity() {
		return capacity;
	}

	public void setCapacity(Integer capacity) {
		this.capacity = capacity;
	}

	@Override
	public String toString() {
		return String.format("Warehouse ID: %d - %s (%s) - Capacity: %d", warehouseId, name, city, capacity);
	}
}