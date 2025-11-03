package importable.model;



import java.time.LocalDate;

/**
 * Represents a product entity with basic information such as name, category, price, and stock quantity.
 * Can be mapped from spreadsheet data (e.g., products.xlsx).
 */
public class Product {

    /** Unique identifier of the product. */
    private Long id;

    /** Name or title of the product. */
    private String name;

    /** Product category (e.g., "Peripherals", "Office"). */
    private String category;

    /** Unit price of the product in the local currency. */
    private Double price;

    /** Available quantity of the product in stock. */
    private Integer quantity;

    /** Date when the product was registered or added to the system. */
    private LocalDate registrationDate;

    /** Default constructor. */
    public Product() {}

    /**
     * Full constructor.
     *
     * @param id               the product ID
     * @param name             the product name
     * @param category         the category the product belongs to
     * @param price            the unit price
     * @param quantity         the stock quantity
     * @param registrationDate the registration date
     */
    public Product(Long id, String name, String category, Double price, Integer quantity, LocalDate registrationDate) {
        this.id = id;
        this.name = name;
        this.category = category;
        this.price = price;
        this.quantity = quantity;
        this.registrationDate = registrationDate;
    }

    /** @return the product ID */
    public Long getId() { return id; }

    /** @param id the product ID to set */
    public void setId(Long id) { this.id = id; }

    /** @return the product name */
    public String getName() { return name; }

    /** @param name the product name to set */
    public void setName(String name) { this.name = name; }

    /** @return the product category */
    public String getCategory() { return category; }

    /** @param category the product category to set */
    public void setCategory(String category) { this.category = category; }

    /** @return the unit price */
    public Double getPrice() { return price; }

    /** @param price the unit price to set */
    public void setPrice(Double price) { this.price = price; }

    /** @return the stock quantity */
    public Integer getQuantity() { return quantity; }

    /** @param quantity the stock quantity to set */
    public void setQuantity(Integer quantity) { this.quantity = quantity; }

    /** @return the registration date */
    public LocalDate getRegistrationDate() { return registrationDate; }

    /** @param registrationDate the registration date to set */
    public void setRegistrationDate(LocalDate registrationDate) { this.registrationDate = registrationDate; }

    /** @return a short string representation of the product */
    @Override
    public String toString() {
        return String.format("%d - %s (%s) - $%.2f", id, name, category, price);
    }
}

