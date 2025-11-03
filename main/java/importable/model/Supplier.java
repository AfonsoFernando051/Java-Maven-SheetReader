package importable.model;

/**
 * Represents a supplier entity.
 * Can be mapped from spreadsheet data (e.g., suppliers.xlsx).
 */
public class Supplier {

    private Long id;
    private String companyName;
    private String cnpj;
    private String contactPerson;
    private String email;
    private String phone;
    private String address;

    /** Default constructor. */
    public Supplier() {}

    /** Full constructor */
    public Supplier(Long id, String companyName, String cnpj, String contactPerson, String email, String phone, String address) {
        this.id = id;
        this.companyName = companyName;
        this.cnpj = cnpj;
        this.contactPerson = contactPerson;
        this.email = email;
        this.phone = phone;
        this.address = address;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getCompanyName() { return companyName; }
    public void setCompanyName(String companyName) { this.companyName = companyName; }
    public String getCnpj() { return cnpj; }
    public void setCnpj(String cnpj) { this.cnpj = cnpj; }
    public String getContactPerson() { return contactPerson; }
    public void setContactPerson(String contactPerson) { this.contactPerson = contactPerson; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }

    @Override
    public String toString() {
        return String.format("%d - %s (Contato: %s, Email: %s)", id, companyName, contactPerson, email);
    }
}