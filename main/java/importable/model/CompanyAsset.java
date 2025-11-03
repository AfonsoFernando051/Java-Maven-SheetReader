package importable.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a company asset (e.g., notebook, chair, monitor). Uses simple
 * String for 'status'. Can be mapped from spreadsheet data (e.g., assets.xlsx).
 */
public class CompanyAsset {

	private String assetTag; // ID do Ativo (Tag, ex: "NTB-001")
	private String description;
	private String category; // Ex: "Electronics", "Furniture"
	private Long employeeId; // ID do funcionário alocado (pode ser null)
	private LocalDate purchaseDate;
	private String status; // Ex: "In Use", "In Storage", "Maintenance"

	/** Default constructor. */
	public CompanyAsset() {
	}

	/** Full constructor */
	public CompanyAsset(String assetTag, String description, String category, Long employeeId, LocalDate purchaseDate,
			String status) {
		this.assetTag = assetTag;
		this.description = description;
		this.category = category;
		this.employeeId = employeeId;
		this.purchaseDate = purchaseDate;
		this.status = status;
	}

	// Getters and Setters
	public String getAssetTag() {
		return assetTag;
	}

	public void setAssetTag(String assetTag) {
		this.assetTag = assetTag;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getCategory() {
		return category;
	}

	public void setCategory(String category) {
		this.category = category;
	}

	public Long getEmployeeId() {
		return employeeId;
	}

	public void setEmployeeId(Long employeeId) {
		this.employeeId = employeeId;
	}

	public LocalDate getPurchaseDate() {
		return purchaseDate;
	}

	public void setPurchaseDate(LocalDate purchaseDate) {
		this.purchaseDate = purchaseDate;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	// Setter para String (planilha) que converte para LocalDate
	public void setPurchaseDate(String dateString) {
		try {
			this.purchaseDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (Exception e) {
			System.err.println("Failed to parse date: " + dateString);
			this.purchaseDate = null;
		}
	}

	@Override
	public String toString() {
		return String.format("Asset: %s (%s) - Status: %s - Allocated to: %d", assetTag, description, status,
				employeeId);
	}
}