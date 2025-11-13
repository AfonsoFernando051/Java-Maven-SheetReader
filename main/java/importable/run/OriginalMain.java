package importable.run;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner; // 1. Import the Scanner class

import importable.config.SheetModel;
import importable.config.SheetTypeEnum;
import importable.model.CompanyAsset;
import importable.model.Customer;
import importable.model.Employee;
import importable.model.Inventory;
import importable.model.Order;
import importable.model.Product;
import importable.model.Shipment;
import importable.model.Supplier;
import importable.model.Task;
import importable.model.Warehouse;
import importable.old.OldAssetImporter;
import importable.old.OldCustomerImporter;
import importable.old.OldEmployeeImporter;
import importable.old.OldInventoryImporter;
import importable.old.OldOrderImporter;
import importable.old.OldProductImporter;
import importable.old.OldShipmentImporter;
import importable.old.OldSupplierImporter;
import importable.old.OldTaskImporter;
import importable.old.OldWarehouseImporter;
import importable.service.ImportService;
// Using the English version translated previously
import importable.service.SheetImportConfigManager;
import importable.service.factory.ImportServiceFactory;

/**
 * Main class to demonstrate and compare import strategies.
 * (Renamed from OriginalMain to Main)
 */
public class OriginalMain {

	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.println("=============================================");
				System.out.println("Choose the import method:");
				System.out.println("1 - WITH Design Patterns (Flexible & Maintainable)");
				System.out.println("2 - WITHOUT Design Patterns (Rigid & Hardcoded)");
				System.out.println("0 - Exit");
				System.out.print("Enter your choice: ");

				String choice = scanner.nextLine(); 

				switch (choice) {
				case "1":
					System.out.println("\n--- Running import WITH design patterns... ---");
					readDataWithDesignPatterns();
					break;
				case "2":
					System.out.println("\n--- Running import WITHOUT design patterns... ---");
					readDataWithoutDesignPatterns();
					break;
				case "0":
					System.out.println("Exiting the application. Goodbye!");
					return;
				default:
					System.out.println("Invalid option! Please try again.");
					break;
				}
				System.out.println("=============================================\n");
			}
		}
	}

	/**
	 * Runs the import process using Factory and Strategy patterns.
	 */
	 static void readDataWithDesignPatterns() {
		System.out.println("--- Using Factory and Strategy patterns to decouple the import logic ---");
		for (SheetTypeEnum sheet : SheetTypeEnum.values()) {
			// Use the translated class and variable names
			SheetImportConfigManager sheetManager = new SheetImportConfigManager();
			ImportService<?> service = ImportServiceFactory.getServiceByType(sheet);
			HashMap<SheetTypeEnum, SheetModel> sheetModel = service.generateSheetModel(sheet);
			
			// Use the translated method name
			sheetManager.setSheetModels(sheetModel); 
			
			// Use the translated method name
			HashMap<SheetTypeEnum, ?> result = service.importAndInsertDataFromSheets(sheetManager, service.getBytesManager(sheet));
			
			ArrayList<?> arrayList = (ArrayList<?>) result.get(sheet);
			System.out.println("--- Imported " + arrayList.size() + " records from " + sheet.name() + " ---");
			for (Object object : arrayList) {
				System.out.println(object.toString());
			}
			System.out.println();
		}
	}

	/**
	 * Runs the import process using rigid, hardcoded static methods.
	 */
	static void readDataWithoutDesignPatterns() {
		System.out.println("--- Using static methods with hardcoded values. Very rigid. ---");
		try {
			// Use the new class name to get the resource
			InputStream customerStream = OriginalMain.class.getClassLoader().getResourceAsStream("customers.xlsx");
			byte[] customerFile = customerStream.readAllBytes();
			// Use the translated method name
			List<Customer> customers = OldCustomerImporter.importSheetData("0", true, "A", "B", "D", "F", "G",
					customerFile);
			System.out.println("\nCustomers imported: " + customers.size());
			for (Object c : customers) {
				System.out.println(c);
			}

			InputStream productStream = OriginalMain.class.getClassLoader().getResourceAsStream("products.xlsx");
			byte[] productFile = productStream.readAllBytes();
			List<Product> products = OldProductImporter.importSheetData("0", true, "A", "B", "D", "C",
					productFile);
			System.out.println("\nProducts imported: " + products.size());
			for (Object p : products) {
				System.out.println(p);
			}
			System.out.println();
			
			InputStream supplierStream = OriginalMain.class.getClassLoader().getResourceAsStream("suppliers.xlsx");
			byte[] supplierFile = supplierStream.readAllBytes();
			List<Supplier> suppliers = OldSupplierImporter.importSheetData("0", true, "A", "B", "D", "E", "G",
					supplierFile);

			System.out.println("\nSuppliers imported: " + suppliers.size());
			for (Object s : suppliers) {
				System.out.println(s);
			}

			InputStream employeeStream = OriginalMain.class.getClassLoader().getResourceAsStream("employees.xlsx");
			byte[] employeeFile = employeeStream.readAllBytes();
			List<Employee> employees = OldEmployeeImporter.importSheetData("0", true, "A", "B", "C", "D", "E",
					"F", employeeFile);

			System.out.println("\nEmployees imported: " + employees.size());
			for (Object e : employees) {
				System.out.println(e);
			}

			InputStream orderStream = OriginalMain.class.getClassLoader().getResourceAsStream("orders.xlsx");
			byte[] orderFile = orderStream.readAllBytes();
			List<Order> orders = OldOrderImporter.importSheetData("0", true, "A", "B", "C", "D", "E", orderFile);

			System.out.println("\nOrders imported: " + orders.size());
			for (Object o : orders) {
				System.out.println(o);
			}

			InputStream inventoryStream = OriginalMain.class.getClassLoader().getResourceAsStream("inventory.xlsx");
			byte[] inventoryFile = inventoryStream.readAllBytes();
			List<Inventory> inventory = OldInventoryImporter.importSheetData("0", true, "A", "B", "C", "D",
					inventoryFile);

			System.out.println("\nInventory items imported: " + inventory.size());
			for (Object i : inventory) {
				System.out.println(i);
			}
			InputStream shipmentStream = OriginalMain.class.getClassLoader().getResourceAsStream("shipments.xlsx");
			byte[] shipmentFile = shipmentStream.readAllBytes();
			List<Shipment> shipments = OldShipmentImporter.importSheetData("0", true, "A", "B", "C", "D", "E",
					"F", shipmentFile);

			System.out.println("\nShipments imported: " + shipments.size());
			for (Shipment s : shipments) {
				System.out.println(s);
			}
			InputStream assetStream = OriginalMain.class.getClassLoader().getResourceAsStream("assets.xlsx");
			byte[] assetFile = assetStream.readAllBytes();
			List<CompanyAsset> assets = OldAssetImporter.importSheetData("0", true, "A", "B", "C", "D", "E", "F",
					assetFile);

			System.out.println("\nCompany Assets imported: " + assets.size());
			for (CompanyAsset a : assets) {
				System.out.println(a);
			}

			InputStream taskStream = OriginalMain.class.getClassLoader().getResourceAsStream("tasks.xlsx");
			byte[] taskFile = taskStream.readAllBytes();
			List<Task> tasks = OldTaskImporter.importSheetData("0", true, "A", "B", "C", "D", "E", "F", taskFile);

			System.out.println("\nTasks imported: " + tasks.size());
			for (Task t : tasks) {
				System.out.println(t);
			}
			InputStream warehouseStream = OriginalMain.class.getClassLoader().getResourceAsStream("warehouses.xlsx");
			byte[] warehouseFile = warehouseStream.readAllBytes();
			List<Warehouse> warehouses = OldWarehouseImporter.importSheetData("0", true, "A", "B", "C", "D",
					warehouseFile);

			System.out.println("\nWarehouses imported: " + warehouses.size());
			for (Warehouse w : warehouses) {
				System.out.println(w);
			}

			System.out.println();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}