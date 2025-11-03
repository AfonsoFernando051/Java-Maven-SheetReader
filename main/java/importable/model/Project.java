package importable.model;

import java.time.LocalDate;

/**
 * Represents a project entity with management and financial information. Can be
 * mapped from spreadsheet data (e.g., projects.xlsx).
 */
public class Project {

	/** Unique identifier of the project. */
	private Long id;

	/** Name of the project. */
	private String name;

	/** Project description. */
	private String description;

	/** Project manager responsible. */
	private String projectManager;

	/** Project status (e.g., PLANNING, IN_PROGRESS, COMPLETED, CANCELLED). */
	private String status;

	/** Project priority (e.g., LOW, MEDIUM, HIGH, URGENT). */
	private String priority;

	/** Project start date. */
	private LocalDate startDate;

	/** Project expected end date. */
	private LocalDate expectedEndDate;

	/** Project actual end date. */
	private LocalDate actualEndDate;

	/** Allocated budget for the project. */
	private Double allocatedBudget;

	/** Actual spent amount on the project. */
	private Double actualSpent;

	/** Client or department requesting the project. */
	private String client;

	/** Project category (e.g., SOFTWARE, CONSTRUCTION, MARKETING, RESEARCH). */
	private String category;

	/** Percentage of project completion. */
	private Integer completionPercentage;

	/** Risk level of the project (e.g., LOW, MEDIUM, HIGH). */
	private String riskLevel;

	/** Default constructor. */
	public Project() {
	}

	/**
	 * Full constructor.
	 *
	 * @param id                   the project ID
	 * @param name                 the project name
	 * @param description          the project description
	 * @param projectManager       the project manager responsible
	 * @param status               the project status
	 * @param priority             the project priority
	 * @param startDate            the project start date
	 * @param expectedEndDate      the expected end date
	 * @param actualEndDate        the actual end date
	 * @param allocatedBudget      the allocated budget
	 * @param actualSpent          the actual spent amount
	 * @param client               the client/department
	 * @param category             the project category
	 * @param completionPercentage the completion percentage
	 * @param riskLevel            the risk level
	 */
	public Project(Long id, String name, String description, String projectManager, String status, String priority,
			LocalDate startDate, LocalDate expectedEndDate, LocalDate actualEndDate, Double allocatedBudget,
			Double actualSpent, String client, String category, Integer completionPercentage, String riskLevel) {
		this.id = id;
		this.name = name;
		this.description = description;
		this.projectManager = projectManager;
		this.status = status;
		this.priority = priority;
		this.startDate = startDate;
		this.expectedEndDate = expectedEndDate;
		this.actualEndDate = actualEndDate;
		this.allocatedBudget = allocatedBudget;
		this.actualSpent = actualSpent;
		this.client = client;
		this.category = category;
		this.completionPercentage = completionPercentage;
		this.riskLevel = riskLevel;
	}

	/** @return the project ID */
	public Long getId() {
		return id;
	}

	/** @param id the project ID to set */
	public void setId(Long id) {
		this.id = id;
	}

	/** @return the project name */
	public String getName() {
		return name;
	}

	/** @param name the project name to set */
	public void setName(String name) {
		this.name = name;
	}

	/** @return the project description */
	public String getDescription() {
		return description;
	}

	/** @param description the project description to set */
	public void setDescription(String description) {
		this.description = description;
	}

	/** @return the project manager */
	public String getProjectManager() {
		return projectManager;
	}

	/** @param projectManager the project manager to set */
	public void setProjectManager(String projectManager) {
		this.projectManager = projectManager;
	}

	/** @return the project status */
	public String getStatus() {
		return status;
	}

	/** @param status the project status to set */
	public void setStatus(String status) {
		this.status = status;
	}

	/** @return the project priority */
	public String getPriority() {
		return priority;
	}

	/** @param priority the project priority to set */
	public void setPriority(String priority) {
		this.priority = priority;
	}

	/** @return the project start date */
	public LocalDate getStartDate() {
		return startDate;
	}

	/** @param startDate the project start date to set */
	public void setStartDate(LocalDate startDate) {
		this.startDate = startDate;
	}

	/** @return the expected end date */
	public LocalDate getExpectedEndDate() {
		return expectedEndDate;
	}

	/** @param expectedEndDate the expected end date to set */
	public void setExpectedEndDate(LocalDate expectedEndDate) {
		this.expectedEndDate = expectedEndDate;
	}

	/** @return the actual end date */
	public LocalDate getActualEndDate() {
		return actualEndDate;
	}

	/** @param actualEndDate the actual end date to set */
	public void setActualEndDate(LocalDate actualEndDate) {
		this.actualEndDate = actualEndDate;
	}

	/** @return the allocated budget */
	public Double getAllocatedBudget() {
		return allocatedBudget;
	}

	/** @param allocatedBudget the allocated budget to set */
	public void setAllocatedBudget(Double allocatedBudget) {
		this.allocatedBudget = allocatedBudget;
	}

	/** @return the actual spent amount */
	public Double getActualSpent() {
		return actualSpent;
	}

	/** @param actualSpent the actual spent amount to set */
	public void setActualSpent(Double actualSpent) {
		this.actualSpent = actualSpent;
	}

	/** @return the client/department */
	public String getClient() {
		return client;
	}

	/** @param client the client/department to set */
	public void setClient(String client) {
		this.client = client;
	}

	/** @return the project category */
	public String getCategory() {
		return category;
	}

	/** @param category the project category to set */
	public void setCategory(String category) {
		this.category = category;
	}

	/** @return the completion percentage */
	public Integer getCompletionPercentage() {
		return completionPercentage;
	}

	/** @param completionPercentage the completion percentage to set */
	public void setCompletionPercentage(Integer completionPercentage) {
		this.completionPercentage = completionPercentage;
	}

	/** @return the risk level */
	public String getRiskLevel() {
		return riskLevel;
	}

	/** @param riskLevel the risk level to set */
	public void setRiskLevel(String riskLevel) {
		this.riskLevel = riskLevel;
	}

	/** @return a short string representation of the project */
	@Override
	public String toString() {
		return String.format("%d - %s [%s] - %d%% complete", id, name, status, completionPercentage);
	}

}