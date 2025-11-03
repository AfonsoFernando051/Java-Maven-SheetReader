package importable.model;

import java.time.LocalDate;

/**
 * Represents a customer entity with personal and contact information. Can be
 * mapped from spreadsheet data (e.g., customers.xlsx).
 */
public class Customer {

	/** Unique identifier of the customer. */
	private Long id;

	/** Full name of the customer. */
	private String name;

	/** CPF (Brazilian personal identification number). */
	private String cpf;

	/** Email address of the customer. */
	private String email;

	/** Customer's date of birth. */
	private LocalDate birthDate;

	/** City where the customer lives. */
	private String city;

	/** State (UF) corresponding to the customer's city. */
	private String state;

	/** Default constructor. */
	public Customer() {
	}

	/**
	 * Full constructor.
	 *
	 * @param id        the customer ID
	 * @param name      the customer's full name
	 * @param cpf       the CPF number
	 * @param email     the customer's email address
	 * @param birthDate the customer's date of birth
	 * @param city      the customer's city
	 * @param state     the customer's state (UF)
	 */
	public Customer(Long id, String name, String cpf, String email, LocalDate birthDate, String city, String state) {
		this.id = id;
		this.name = name;
		this.cpf = cpf;
		this.email = email;
		this.birthDate = birthDate;
		this.city = city;
		this.state = state;
	}

	/** @return the customer ID */
	public Long getId() {
		return id;
	}

	/** @param id the customer ID to set */
	public void setId(Long id) {
		this.id = id;
	}

	/** @return the customer's name */
	public String getName() {
		return name;
	}

	/** @param name the customer's name to set */
	public void setName(String name) {
		this.name = name;
	}

	/** @return the customer's CPF */
	public String getCpf() {
		return cpf;
	}

	/** @param cpf the customer's CPF to set */
	public void setCpf(String cpf) {
		this.cpf = cpf;
	}

	/** @return the customer's email */
	public String getEmail() {
		return email;
	}

	/** @param email the customer's email to set */
	public void setEmail(String email) {
		this.email = email;
	}

	/** @return the customer's date of birth */
	public LocalDate getBirthDate() {
		return birthDate;
	}

	/** @param birthDate the customer's date of birth to set */
	public void setBirthDate(LocalDate birthDate) {
		this.birthDate = birthDate;
	}

	/** @return the customer's city */
	public String getCity() {
		return city;
	}

	/** @param city the customer's city to set */
	public void setCity(String city) {
		this.city = city;
	}

	/** @return the customer's state (UF) */
	public String getState() {
		return state;
	}

	/** @param state the customer's state (UF) to set */
	public void setState(String state) {
		this.state = state;
	}

	/** @return a short string representation of the customer */
	@Override
	public String toString() {
		return String.format("%d - %s (%s, %s)", id, name, city, state);
	}
}
