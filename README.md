# 📘 **Java-Maven-SheetReader: Refactoring Case Study with Design Patterns**

> *Academic Case Study: Evaluating the Impact of Design Patterns on Software Quality*

---

## 🧩 **1. Project Overview**

**SheetReader** was developed as a case study to evaluate how applying **object-oriented design patterns** affects **software quality, maintainability, and scalability** in Java systems.

The project implements two distinct versions of the same functionality — reading and importing Excel spreadsheets:

* **Original Version:** procedural logic, monolithic “God Classes,” and high coupling.
* **Refactored Version:** restructured using **design patterns** (Template Method, Factory Method, Strategy), focusing on **modularity and extensibility**.

---

## 🧱 **2. System Architecture**

### 🔹 **Original Version**

* Core classes: `OldProductImporter`, `OldCustomerImporter`
* Procedural structure with high redundancy
* Lack of abstraction and separation of responsibilities

### 🔹 **Refactored Version**

* Design Patterns Implemented:

  * **Template Method** → defines generic import workflow (`AbstractImportSheetService`, `GenericPlanilhaProcessor`)
  * **Factory Method** → dynamic instantiation of import services (`ImportServiceFactory`)
  * **Strategy** → flexible model and validation strategies (`CustomerImportationModel`, `ProductImportationModel`)
* Modular and interface-driven design, fully extensible.

📂 **Main Packages**

```
importable.config       → Configuration and enums
importable.model        → Domain models (POJOs)
importable.service      → Import services and factories
importable.utils        → File utilities and I/O handlers
importable.old          → Legacy procedural version
```

---

## ⚙️ **3. Tools Used**

| Tool              | Purpose                                         |
| ----------------- | ----------------------------------------------- |
| **CKJM Extended** | Class-level metrics (WMC, CBO, RFC, LCOM, etc.) |
| **SonarQube**     | Holistic quality and maintainability analysis   |
| **Maven**         | Dependency and build management                 |
| **Apache POI**    | Excel spreadsheet processing                    |
| **Java 17**       | Programming language used                       |

---

## 📊 **4. Results and Discussion**

### 4.1. **CKJM Class-Level Metrics Analysis**

Static analysis using **CKJM-Extended** focused on Coupling (CBO, RFC), Cohesion (LCOM), and Complexity (WMC).

| Metric (Focus)               | Original Version | Refactored Version | Change | Quality Impact                     |
| ---------------------------- | ---------------- | ------------------ | ------ | ---------------------------------- |
| **CBO (Coupling)**           | 6.00             | 4.85               | -19.2% | ✅ Reduced coupling                 |
| **RFC (Response for Class)** | 29.00            | 17.59              | -39.3% | ✅ Lower response complexity        |
| **LCOM (Lack of Cohesion)**  | 1.00             | 23.85              | +2285% | ⚠️ Needs qualitative analysis      |
| **WMC (Complexity)**         | 2.00             | 6.64               | +232%  | ⚠️ Increased due to modularization |

📌 **Interpretation:**
The refactored version significantly reduced coupling but increased the number of classes — distributing complexity more evenly.
The “Original Version” contained **God Classes** like `OldProductImporter` (WMC=72.5), which inflated metrics artificially.
The new architecture follows the **Single Responsibility Principle (SRP)**, resulting in higher maintainability and logical cohesion.

---

### 4.2. **SonarQube Quality Analysis**

Holistic analysis using **SonarQube** validated the CKJM findings:

| Metric (SonarQube)             | Original (299 LOC) | Refactored (1.8k LOC) |
| ------------------------------ | ------------------ | --------------------- |
| **Security (Vulnerabilities)** | A (0 Issues)       | A (0 Issues)          |
| **Maintainability**            | E (Critical)       | A (Excellent)         |
| **Technical Debt**             | 23 min             | 77 min                |

📌 **Interpretation:**
Despite being **six times larger**, the refactored version achieved the **highest possible maintainability rating (A)**.
The original version, while smaller, was densely packed with “Code Smells” and structural problems, receiving a **critical rating (E)**.

---

### 4.3. **Scalability Simulation**

A scalability test simulated system growth from **2 to 10 importers**, estimating the impact on complexity and technical debt.

| Metric (Project Total)      | Original (2 imports) | Original (10 imports) | Refactored (2 imports) | Refactored (10 imports) |
| --------------------------- | -------------------- | --------------------- | ---------------------- | ----------------------- |
| **Total Complexity (WMC)**  | ~153                 | ~765                  | ~285                   | ~365                    |
| **Maintainability (Sonar)** | E                    | E                     | A                      | A                       |
| **Technical Debt**          | 23 min               | ~115 min              | 77 min                 | ~101 min                |

📌 **Interpretation:**
The procedural version **does not scale** — complexity grows exponentially.
In contrast, the refactored version maintains **linear complexity growth**, keeping maintainability at an **A rating**.
Even with more importers, total technical debt remains **lower** than the procedural system, proving the **long-term payoff of good architecture**.

---

## 🧮 **5. Conclusion**

This study quantitatively demonstrates the positive impact of **design patterns** on software structure and maintainability.

* **CBO (-19%) and RFC (-39%)**: significant reduction in coupling and response complexity.
* **WMC and LCOM** increases are expected — complexity was **distributed**, not accumulated.
* **SonarQube** confirms: maintainability improved from **E (Critical)** to **A (Excellent)**.
* The **scalability simulation** shows that the refactored architecture supports sustainable growth, whereas the original design becomes unmaintainable.

🔹 **Final Statement:**

> The initial investment in design patterns yields long-term benefits — resulting in a system that is more **modular, cohesive, extensible, and maintainable**.

---

## 📘 **6. CKJM Metric Definitions**

| Metric   | Description                                            | Desirable  | Interpretation                      |
| -------- | ------------------------------------------------------ | ---------- | ----------------------------------- |
| **CBO**  | Coupling Between Objects — number of dependent classes | Low        | Fewer external dependencies         |
| **LCOM** | Lack of Cohesion of Methods — measures class focus     | Low        | High cohesion and SRP adherence     |
| **RFC**  | Response For Class — number of callable methods        | Low        | Lower complexity, simpler classes   |
| **WMC**  | Weighted Methods per Class — total complexity          | Low        | Easier to understand and maintain   |
| **DIT**  | Depth of Inheritance Tree                              | Medium     | Good reuse without deep hierarchies |
| **NOC**  | Number of Children                                     | Low–Medium | Indicates reuse through inheritance |
| **Ca**   | Afferent Coupling — classes depending on this one      | Medium     | Indicates central or core classes   |
| **Ce**   | Efferent Coupling — classes this one depends on        | Low        | Indicates independence              |
| **NPM**  | Number of Public Methods                               | Low        | Smaller, simpler interfaces         |
| **LOC**  | Lines of Code                                          | Low        | Simpler and smaller codebase        |

---

## 🧰 **7. How to Run**

### 🖥️ **Requirements**

* Java 17+
* Maven 3.9+
* Apache POI (included via Maven)

### ▶️ **Commands**

```bash
# Clone repository
git clone https://github.com/AfonsoFernando051/Java-Maven-SheetReader.git
cd Java-Maven-SheetReader

# Build
mvn clean package

# Run refactored version
java -cp target/SheetReader-1.0.jar importable.run.Main

# Run original version (without design patterns)
java -cp target/SheetReader-1.0.jar importable.old.OldProductImporter
```

---

## 📈 **8. CKJM Metric Trends**

| Metric   | Trend                           |
| -------- | ------------------------------- |
| **CBO**  | 🔻 Reduced coupling             |
| **RFC**  | 🔻 Reduced response complexity  |
| **LCOM** | 🔺 Higher due to modularization |
| **WMC**  | 🔺 Distributed complexity       |

---

## 👨‍💻 **9. Author**

**Afonso Fernando Afonso**
Developed as part of an academic case study for a **Bachelor’s Thesis (TCC)**.
📎 GitHub: [@AfonsoFernando051](https://github.com/AfonsoFernando051)


