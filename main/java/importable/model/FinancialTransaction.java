package importable.model;

import java.time.LocalDate;

/**
 * Represents a financial transaction entity with monetary movement information.
 * Can be mapped from spreadsheet data (e.g., transactions.xlsx).
 */
public class FinancialTransaction {

	/** Unique identifier of the transaction. */
	private Long id;

	/** Transaction type (e.g., INCOME, EXPENSE, TRANSFER, INVESTMENT, PAYMENT). */
	private String type;

	/**
	 * Transaction category (e.g., SALARY, UTILITIES, SALES, TAXES, MAINTENANCE).
	 */
	private String category;

	/** Transaction description. */
	private String description;

	/** Transaction amount. */
	private Double amount;

	/** Transaction currency (e.g., BRL, USD, EUR). */
	private String currency;

	/** Transaction date. */
	private LocalDate transactionDate;

	/** Payment method (e.g., CASH, CREDIT_CARD, DEBIT_CARD, BANK_TRANSFER, PIX). */
	private String paymentMethod;

	/** Transaction status (e.g., PENDING, COMPLETED, CANCELLED, REFUNDED). */
	private String status;

	/** Source account or wallet. */
	private String sourceAccount;

	/** Destination account or wallet. */
	private String destinationAccount;

	/** Reference number (e.g., invoice number, receipt number). */
	private String referenceNumber;

	/** Related entity ID (e.g., customerId, supplierId, employeeId). */
	private Long relatedEntityId;

	/** Related entity type (e.g., CUSTOMER, SUPPLIER, EMPLOYEE). */
	private String relatedEntityType;

	/** Transaction notes or observations. */
	private String notes;

	/** User who registered the transaction. */
	private String createdBy;

	/** Date when the transaction was recorded. */
	private LocalDate recordedDate;

	/** Default constructor. */
	public FinancialTransaction() {
	}

	/**
	 * Full constructor.
	 *
	 * @param id                 the transaction ID
	 * @param type               the transaction type
	 * @param category           the transaction category
	 * @param description        the transaction description
	 * @param amount             the transaction amount
	 * @param currency           the transaction currency
	 * @param transactionDate    the transaction date
	 * @param paymentMethod      the payment method
	 * @param status             the transaction status
	 * @param sourceAccount      the source account
	 * @param destinationAccount the destination account
	 * @param referenceNumber    the reference number
	 * @param relatedEntityId    the related entity ID
	 * @param relatedEntityType  the related entity type
	 * @param notes              the transaction notes
	 * @param createdBy          the user who created the transaction
	 * @param recordedDate       the recording date
	 */
	public FinancialTransaction(Long id, String type, String category, String description, Double amount,
			String currency, LocalDate transactionDate, String paymentMethod, String status, String sourceAccount,
			String destinationAccount, String referenceNumber, Long relatedEntityId, String relatedEntityType,
			String notes, String createdBy, LocalDate recordedDate) {
		this.id = id;
		this.type = type;
		this.category = category;
		this.description = description;
		this.amount = amount;
		this.currency = currency;
		this.transactionDate = transactionDate;
		this.paymentMethod = paymentMethod;
		this.status = status;
		this.sourceAccount = sourceAccount;
		this.destinationAccount = destinationAccount;
		this.referenceNumber = referenceNumber;
		this.relatedEntityId = relatedEntityId;
		this.relatedEntityType = relatedEntityType;
		this.notes = notes;
		this.createdBy = createdBy;
		this.recordedDate = recordedDate;
	}

	/** @return the transaction ID */
	public Long getId() {
		return id;
	}

	/** @param id the transaction ID to set */
	public void setId(Long id) {
		this.id = id;
	}

	/** @return the transaction type */
	public String getType() {
		return type;
	}

	/** @param type the transaction type to set */
	public void setType(String type) {
		this.type = type;
	}

	/** @return the transaction category */
	public String getCategory() {
		return category;
	}

	/** @param category the transaction category to set */
	public void setCategory(String category) {
		this.category = category;
	}

	/** @return the transaction description */
	public String getDescription() {
		return description;
	}

	/** @param description the transaction description to set */
	public void setDescription(String description) {
		this.description = description;
	}

	/** @return the transaction amount */
	public Double getAmount() {
		return amount;
	}

	/** @param amount the transaction amount to set */
	public void setAmount(Double amount) {
		this.amount = amount;
	}

	/** @return the transaction currency */
	public String getCurrency() {
		return currency;
	}

	/** @param currency the transaction currency to set */
	public void setCurrency(String currency) {
		this.currency = currency;
	}

	/** @return the transaction date */
	public LocalDate getTransactionDate() {
		return transactionDate;
	}

	/** @param transactionDate the transaction date to set */
	public void setTransactionDate(LocalDate transactionDate) {
		this.transactionDate = transactionDate;
	}

	/** @return the payment method */
	public String getPaymentMethod() {
		return paymentMethod;
	}

	/** @param paymentMethod the payment method to set */
	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	/** @return the transaction status */
	public String getStatus() {
		return status;
	}

	/** @param status the transaction status to set */
	public void setStatus(String status) {
		this.status = status;
	}

	/** @return the source account */
	public String getSourceAccount() {
		return sourceAccount;
	}

	/** @param sourceAccount the source account to set */
	public void setSourceAccount(String sourceAccount) {
		this.sourceAccount = sourceAccount;
	}

	/** @return the destination account */
	public String getDestinationAccount() {
		return destinationAccount;
	}

	/** @param destinationAccount the destination account to set */
	public void setDestinationAccount(String destinationAccount) {
		this.destinationAccount = destinationAccount;
	}

	/** @return the reference number */
	public String getReferenceNumber() {
		return referenceNumber;
	}

	/** @param referenceNumber the reference number to set */
	public void setReferenceNumber(String referenceNumber) {
		this.referenceNumber = referenceNumber;
	}

	/** @return the related entity ID */
	public Long getRelatedEntityId() {
		return relatedEntityId;
	}

	/** @param relatedEntityId the related entity ID to set */
	public void setRelatedEntityId(Long relatedEntityId) {
		this.relatedEntityId = relatedEntityId;
	}

	/** @return the related entity type */
	public String getRelatedEntityType() {
		return relatedEntityType;
	}

	/** @param relatedEntityType the related entity type to set */
	public void setRelatedEntityType(String relatedEntityType) {
		this.relatedEntityType = relatedEntityType;
	}

	/** @return the transaction notes */
	public String getNotes() {
		return notes;
	}

	/** @param notes the transaction notes to set */
	public void setNotes(String notes) {
		this.notes = notes;
	}

	/** @return the user who created the transaction */
	public String getCreatedBy() {
		return createdBy;
	}

	/** @param createdBy the user who created the transaction to set */
	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	/** @return the recording date */
	public LocalDate getRecordedDate() {
		return recordedDate;
	}

	/** @param recordedDate the recording date to set */
	public void setRecordedDate(LocalDate recordedDate) {
		this.recordedDate = recordedDate;
	}

	/** @return a short string representation of the transaction */
	@Override
	public String toString() {
		return String.format("%d - %s: %s %s (%s)", id, type, amount, currency, status);
	}

}