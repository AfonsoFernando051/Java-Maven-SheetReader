package importable.model;


import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

/**
 * Represents an employee entity.
 * Can be mapped from spreadsheet data (e.g., employees.xlsx).
 */
public class Employee {

    private Long id;
    private String name;
    private String email;
    private String department;
    private String position; // Cargo
    private LocalDate hireDate;

    /** Default constructor. */
    public Employee() {}

    /** Full constructor */
    public Employee(Long id, String name, String email, String department, String position, LocalDate hireDate) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.department = department;
        this.position = position;
        this.hireDate = hireDate;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getName() { return name; }
    public void setName(String name) { this.name = name; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getDepartment() { return department; }
    public void setDepartment(String department) { this.department = department; }
    public String getPosition() { return position; }
    public void setPosition(String position) { this.position = position; }
    public LocalDate getHireDate() { return hireDate; }
    public void setHireDate(LocalDate hireDate) { this.hireDate = hireDate; }
    
    // Setter para String (planilha) que converte para LocalDate
    public void setHireDate(String dateString) {
        // Assume o formato AAAA-MM-DD
        try {
            this.hireDate = LocalDate.parse(dateString, DateTimeFormatter.ISO_LOCAL_DATE);
        } catch (Exception e) {
            System.err.println("Failed to parse date: " + dateString);
            this.hireDate = null;
        }
    }

    @Override
    public String toString() {
        return String.format("%d - %s (Dept: %s, Position: %s)", id, name, department, position);
    }
}