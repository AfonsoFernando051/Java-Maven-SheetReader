package importable.run;

import java.io.InputStream;
import java.util.List;

import importable.model.Address;
import importable.model.CompanyAsset;
import importable.model.Customer;
import importable.model.Employee;
import importable.model.FinancialTransaction;
import importable.model.Inventory;
import importable.model.Order;
import importable.model.Product;
import importable.model.Project;
import importable.model.Shipment;
import importable.model.Supplier;
import importable.model.Task;
import importable.model.Warehouse;
import importable.old.OldAddressImporter;
import importable.old.OldAssetImporter;
import importable.old.OldCustomerImporter;
import importable.old.OldEmployeeImporter;
import importable.old.OldFinancialTransactionImporter;
import importable.old.OldInventoryImporter;
import importable.old.OldOrderImporter;
import importable.old.OldProductImporter;
import importable.old.OldProjectImporter;
import importable.old.OldShipmentImporter;
import importable.old.OldSupplierImporter;
import importable.old.OldTaskImporter;
import importable.old.OldWarehouseImporter;

public class MainWDP {

	public static void main(String[] args) {
		readDataWithoutDesignPatterns();
	}

	private static void readDataWithoutDesignPatterns() {
		System.out.println("--- Using static methods with hardcoded values. Very rigid. ---");
		try {
			InputStream customerStream = MainWDP.class.getClassLoader().getResourceAsStream("customers.xlsx");
			byte[] customerFile = customerStream.readAllBytes();
			List<Customer> customers = OldCustomerImporter.importarDadosPlanilha("0", true, "A", "B", "D", "F", "G",
					customerFile);
			System.out.println("\nCustomers imported: " + customers.size());
			for (Object c : customers) {
				System.out.println(c);
			}

			InputStream productStream = MainWDP.class.getClassLoader().getResourceAsStream("products.xlsx");
			byte[] productFile = productStream.readAllBytes();
			List<Product> products = OldProductImporter.importarDadosPlanilha("0", true, "A", "B", "D", "C",
					productFile);
			System.out.println("\nProducts imported: " + products.size());
			for (Object p : products) {
				System.out.println(p);
			}
			System.out.println();
			InputStream supplierStream = MainWDP.class.getClassLoader().getResourceAsStream("suppliers.xlsx");
			byte[] supplierFile = supplierStream.readAllBytes();
			List<Supplier> suppliers = OldSupplierImporter.importarDadosPlanilha("0", true, "A", "B", "D", "E", "G",
					supplierFile);

			System.out.println("\nSuppliers imported: " + suppliers.size());
			for (Object s : suppliers) {
				System.out.println(s);
			}

			InputStream employeeStream = MainWDP.class.getClassLoader().getResourceAsStream("employees.xlsx");
			byte[] employeeFile = employeeStream.readAllBytes();
			List<Employee> employees = OldEmployeeImporter.importarDadosPlanilha("0", true, "A", "B", "C", "D", "E",
					"F", employeeFile);

			System.out.println("\nEmployees imported: " + employees.size());
			for (Object e : employees) {
				System.out.println(e);
			}

			InputStream orderStream = MainWDP.class.getClassLoader().getResourceAsStream("orders.xlsx");
			byte[] orderFile = orderStream.readAllBytes();
			List<Order> orders = OldOrderImporter.importarDadosPlanilha("0", true, "A", "B", "C", "D", "E", orderFile);

			System.out.println("\nOrders imported: " + orders.size());
			for (Object o : orders) {
				System.out.println(o);
			}

			InputStream inventoryStream = MainWDP.class.getClassLoader().getResourceAsStream("inventory.xlsx");
			byte[] inventoryFile = inventoryStream.readAllBytes();
			List<Inventory> inventory = OldInventoryImporter.importarDadosPlanilha("0", true, "A", "B", "C", "D",
					inventoryFile);

			System.out.println("\nInventory items imported: " + inventory.size());
			for (Object i : inventory) {
				System.out.println(i);
			}
			InputStream shipmentStream = MainWDP.class.getClassLoader().getResourceAsStream("shipments.xlsx");
			byte[] shipmentFile = shipmentStream.readAllBytes();
			List<Shipment> shipments = OldShipmentImporter.importarDadosPlanilha("0", true, "A", "B", "C", "D", "E",
					"F", shipmentFile);

			System.out.println("\nShipments imported: " + shipments.size());
			for (Shipment s : shipments) {
				System.out.println(s);
			}
			InputStream assetStream = MainWDP.class.getClassLoader().getResourceAsStream("assets.xlsx");
			byte[] assetFile = assetStream.readAllBytes();
			List<CompanyAsset> assets = OldAssetImporter.importarDadosPlanilha("0", true, "A", "B", "C", "D", "E", "F",
					assetFile);

			System.out.println("\nCompany Assets imported: " + assets.size());
			for (CompanyAsset a : assets) {
				System.out.println(a);
			}

			InputStream taskStream = MainWDP.class.getClassLoader().getResourceAsStream("tasks.xlsx");
			byte[] taskFile = taskStream.readAllBytes();
			// Mapeamento: ID(A), ProjectID(B), Desc(C), Assignee(D), Prio(E), Data(F)
			List<Task> tasks = OldTaskImporter.importarDadosPlanilha("0", true, "A", "B", "C", "D", "E", "F", taskFile);

			System.out.println("\nTasks imported: " + tasks.size());
			for (Task t : tasks) {
				System.out.println(t);
			}
			InputStream warehouseStream = MainWDP.class.getClassLoader().getResourceAsStream("warehouses.xlsx");
			byte[] warehouseFile = warehouseStream.readAllBytes();
			List<Warehouse> warehouses = OldWarehouseImporter.importarDadosPlanilha("0", true, "A", "B", "C", "D",
					warehouseFile);

			System.out.println("\nWarehouses imported: " + warehouses.size());
			for (Warehouse w : warehouses) {
				System.out.println(w);
			}

			InputStream addressStream = MainWDP.class.getClassLoader().getResourceAsStream("address.xlsx");
			byte[] addressFile = addressStream.readAllBytes();
			List<Address> addresses = OldAddressImporter.importarDadosPlanilha("0", true, "A", "B", "C", "D", "E", "F", "G",
					addressFile);

			System.out.println("\nAddresses imported: " + addresses.size());
			for (Address a : addresses) {
				System.out.println(a);
			}

			InputStream projectStream = MainWDP.class.getClassLoader().getResourceAsStream("projects.xlsx");
			byte[] projectFile = projectStream.readAllBytes();
			List<Project> projects = OldProjectImporter.importarDadosPlanilha("0", true, "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O",
					projectFile);

			System.out.println("\nProjects imported: " + projects.size());
			for (Project p : projects) {
				System.out.println(p);
			}

			InputStream transactionStream = MainWDP.class.getClassLoader().getResourceAsStream("financial-transaction.xlsx");
			byte[] transactionFile = transactionStream.readAllBytes();
			List<FinancialTransaction> transactions = OldFinancialTransactionImporter.importarDadosPlanilha("0", true, "A", "B", "C", "D", "E", "F", "G", "H", "I", "J", "K", "L", "M", "N", "O", "P", "Q",
					transactionFile);

			System.out.println("\nFinancial Transactions imported: " + transactions.size());
			for (FinancialTransaction ft : transactions) {
				System.out.println(ft);
			}
			
			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}