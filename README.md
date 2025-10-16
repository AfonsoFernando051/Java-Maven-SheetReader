-----

-----

# SheetReader - A Generic Java Sheet Import Library

SheetReader is a lightweight, annotation-driven Java library designed to import data from spreadsheet files (`.xlsx`, `.xls`) into a list of Plain Old Java Objects (POJOs).

It leverages Java Annotations and Reflection to create a highly decoupled and reusable solution, eliminating the need for boilerplate code for each data model you need to import. 🚀

## The Problem It Solves

Traditionally, importing data from spreadsheets into a Java application involves writing specific, repetitive code for each type of object (e.g., `Product`, `Customer`, `Invoice`). This approach leads to several issues:

  * **Tight Coupling:** The import logic is tightly coupled to the structure of the specific Java class. Any change in the class requires a change in the importer.
  * **Boilerplate Code:** Most of the code for reading rows and cells is repeated for every different object type.
  * **Low Reusability:** The importer for `Product` cannot be used to import `Customer` data.
  * **Difficult Maintenance:** Managing dozens of different importers becomes complex and error-prone.

SheetReader solves this by inverting the responsibility. Instead of the importer knowing the object, the **object itself declares how it should be populated** from a spreadsheet.

-----

## Key Features

  * **Annotation-Driven:** Simply annotate your model classes and their fields to map them to spreadsheet columns.
  * **Generic and Type-Safe:** The core engine works with any Java class (`<T>`) and returns a type-safe `List<T>`.
  * **Highly Decoupled:** The library has no direct dependency on your project's domain models.
  * **Reduces Boilerplate:** Write the import logic once. The library handles the file I/O, iteration, and object instantiation dynamically.
  * **Easy to Use:** The API is simple and requires minimal configuration.

-----

## Installation

To use SheetReader in your Maven project, you first need to add the **Apache POI** dependency to handle Excel files.

Add the following to your `pom.xml`:

```xml
<dependencies>
    <dependency>
        <groupId>org.apache.poi</groupId>
        <artifactId>poi-ooxml</artifactId>
        <version>5.2.5</version>
    </dependency>
</dependencies>
```

-----

## How to Use

Using the library involves three simple steps:

### Step 1: Annotate Your Model Class (POJO)

First, define your Java object and use the `@Importable` and `@Annotation` annotations to map its fields to the spreadsheet columns.

  * `@Importable`: A marker annotation placed on the class to identify it as a target for the reader.
  * `@Annotation(columnIndex)`: Placed on each field that should be populated. `columnIndex` corresponds to the 0-based index of the column in the spreadsheet.

**Example: `Product.java`**

```java
package com.yourproject.model;

import importable.Importable;
import importable.Annotation;

@Importable // Marks this class as eligible for import
public class Product {

    @Annotation(columnIndex = 0) // Maps this field to the first column (A)
    private String name;

    @Annotation(columnIndex = 1) // Maps this field to the second column (B)
    private Double price;
    
    @Annotation(columnIndex = 2) // Maps this field to the third column (C)
    private Integer stock;

    // Getters, Setters, and Constructors are required for the library to work
    // ...
}
```

### Step 2: Prepare Your Spreadsheet

Create an Excel file (`.xlsx`) where the data matches the column mapping defined in your POJO.

**Example: `products.xlsx`**
| A | B | C |
| :--- | :--- | :--- |
| Laptop | 1200.50 | 50 |
| Mouse | 25.00 | 300 |
| Keyboard | 75.99 | 150 |

### Step 3: Run the Reader

Use the `Reader` class to execute the import. Pass the file path and the `.class` object of your model.

**Example: `Main.java`**

```java
import java.util.List;
import importable.Reader;
import com.yourproject.model.Product;
import importable.exception.SheetReaderException;

public class Main {
    public static void main(String[] args) {
        Reader reader = new Reader();
        String filePath = "path/to/your/products.xlsx";

        try {
            // The magic happens here!
            List<Product> productList = reader.read(filePath, Product.class);

            System.out.println("Successfully imported " + productList.size() + " products.");
            for (Product product : productList) {
                System.out.println("Name: " + product.getName() + ", Price: " + product.getPrice());
            }

        } catch (SheetReaderException e) {
            System.err.println("Failed to read the sheet: " + e.getMessage());
            e.printStackTrace();
        }
    }
}
```

-----

## Architectural Concepts 🏛️

This library is a practical demonstration of software design principles that promote code quality.

#### 1\. Decoupling via Annotations and Reflection

The core strength of this design is its **extremely low coupling**. The `Reader` component does not know that a `Product` class exists. It is only programmed to look for classes marked with `@Importable` and fields marked with `@Annotation`.

By using the **Java Reflection API**, the `Reader` can inspect any class at runtime, discover its annotations, create a new instance, and set its fields dynamically. This means you can add hundreds of new importable classes to your project **without ever modifying a single line of code** in the `SheetReader` library, perfectly illustrating the **Open-Closed Principle**.

#### 2\. High Cohesion

Cohesion is improved because the responsibility for defining the data mapping is placed directly within the domain model itself. The `Product` class is now the single source of truth for how its fields map to spreadsheet columns. The `Reader` class also has high cohesion, as its only responsibility is the technical process of reading a file and using reflection to populate objects.

#### 3\. Generics for Reusability and Type Safety

The use of Generics (`<T>`) in the `read(String filePath, Class<T> clazz)` method is fundamental. It allows the same `read` method to be used for any type of object while providing compile-time type safety. The method returns a `List<Product>` when passed `Product.class`, and a `List<Customer>` when passed `Customer.class`, ensuring the client code is clean and safe.

-----