package importable.model;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents a project task. Uses simple String for 'priority'. Can be mapped
 * from spreadsheet data (e.g., tasks.xlsx).
 */
public class Task {

	private Long taskId;
	private Long projectId; // ID do projeto ao qual pertence
	private String description;
	private Long assigneeId; // ID do funcionário responsável
	private String priority; // Prioridade como String (ex: "High", "Medium", "Low")
	private LocalDate dueDate; // Data de Vencimento

	/** Default constructor. */
	public Task() {
	}

	/** Full constructor */
	public Task(Long taskId, Long projectId, String description, Long assigneeId, String priority, LocalDate dueDate) {
		this.taskId = taskId;
		this.projectId = projectId;
		this.description = description;
		this.assigneeId = assigneeId;
		this.priority = priority;
		this.dueDate = dueDate;
	}

	// Getters and Setters
	public Long getTaskId() {
		return taskId;
	}

	public void setTaskId(Long taskId) {
		this.taskId = taskId;
	}

	public Long getProjectId() {
		return projectId;
	}

	public void setProjectId(Long projectId) {
		this.projectId = projectId;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public Long getAssigneeId() {
		return assigneeId;
	}

	public void setAssigneeId(Long assigneeId) {
		this.assigneeId = assigneeId;
	}

	public String getPriority() {
		return priority;
	}

	public void setPriority(String priority) {
		this.priority = priority;
	}

	public LocalDate getDueDate() {
		return dueDate;
	}

	public void setDueDate(LocalDate dueDate) {
		this.dueDate = dueDate;
	}

	// Setter para String (planilha) que converte para LocalDate
	public void setDueDate(String dateString) {
		try {
			this.dueDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
		} catch (Exception e) {
			System.err.println("Failed to parse date: " + dateString);
			this.dueDate = null;
		}
	}

	@Override
	public String toString() {
		return String.format("Task ID: %d (Project: %d) - Prio: %s - Due: %s - Assignee: %d", taskId, projectId,
				priority, dueDate, assigneeId);
	}
}