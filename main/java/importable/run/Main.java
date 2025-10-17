package importable.run;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner; // 1. Import the Scanner class

import importable.config.PlanilhaModel;
import importable.config.TipoPlanilhaImportacaoEnum;
import importable.model.customer.Customer;
import importable.model.product.Product;
import importable.old.OldCustomerImporter;
import importable.old.OldProductImporter;
import importable.service.ImportService;
import importable.service.ImportServiceFactory;
import importable.service.PlanilhaImportConfigManager;

public class Main {
	
	public static void main(String[] args) {
		try (Scanner scanner = new Scanner(System.in)) {
			while (true) {
				System.out.println("=============================================");
				System.out.println("Choose the import method:");
				System.out.println("1 - WITH Design Patterns (Flexible & Maintainable) ✨");
				System.out.println("2 - WITHOUT Design Patterns (Rigid & Hardcoded) ⚙️");
				System.out.println("0 - Exit");
				System.out.print("Enter your choice: ");

				String choice = scanner.nextLine(); // 4. Read user input

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
						return; // Exit the main method
					default:
						System.out.println("Invalid option! Please try again.");
						break;
				}
				System.out.println("=============================================\n");
			}
		}
	}

	private static void readDataWithDesignPatterns() {
		System.out.println("--- Using Factory and Strategy patterns to decouple the import logic ---");
		for (TipoPlanilhaImportacaoEnum sheet : TipoPlanilhaImportacaoEnum.values()) {
			PlanilhaImportConfigManager planilhasManager = new PlanilhaImportConfigManager();
			ImportService<?> service = ImportServiceFactory.getServiceByType(sheet);
			HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaModel = service.generatePlanilhaModel();
			planilhasManager.setPlanilhas(planilhaModel);
			service.importBringInsertDataManySheet(planilhasManager,
					 service.getBytesManager(),
					 t ->{
						 ArrayList<?> arrayList = t.get(sheet);
						 System.out.println("--- Imported " + arrayList.size() + " records from " + sheet.name() + " ---");
						 for (Object object : arrayList) {
							 System.out.println(object.toString());
						 }
						 System.out.println();
					});
		}
	}

	private static void readDataWithoutDesignPatterns() {
		System.out.println("--- Using static methods with hardcoded values. Very rigid. ---");
		try {
			InputStream customerStream = Main.class.getClassLoader()
					.getResourceAsStream("customers.xlsx");
			  byte[] customerFile = customerStream.readAllBytes();
			  List<Customer> customers = OldCustomerImporter.importarDadosPlanilha(
					  "0", true, "A","B", "D", "F","G", customerFile);
			  System.out.println("\nCustomers imported: " + customers.size());
			  for (Object c : customers) {
				  System.out.println(c);
			  }
			  
			  InputStream productStream = Main.class.getClassLoader()
					   .getResourceAsStream("products.xlsx");
			  byte[] productFile = productStream.readAllBytes();
			  List<Product> products = OldProductImporter.importarDadosPlanilha(
					  "0", true,"A", "B", "D", "C", productFile);
			  System.out.println("\nProducts imported: " + products.size());
			  for (Object p : products) {
				  System.out.println(p);
			  }
			  System.out.println();

		  } catch (Exception e) {
			  e.printStackTrace();
		  }
	}
}