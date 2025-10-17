package importable.run;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

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
	
	static InputStream customerStream = Main.class.getClassLoader()
            .getResourceAsStream("customers.xlsx");
	
	static InputStream productStream = Main.class.getClassLoader()
             .getResourceAsStream("products.xlsx");
	 
    public static void main(String[] args) {
    	
//    	  readDataWithoutDesignPatterns();
    	  
    	  readDataWithtDesignPatterns();
    }

	private static void readDataWithtDesignPatterns() {

		for (TipoPlanilhaImportacaoEnum sheet : TipoPlanilhaImportacaoEnum.values()) {
			PlanilhaImportConfigManager planilhasManager = new PlanilhaImportConfigManager();
			ImportService<?> service = ImportServiceFactory.getServiceByType(sheet);
			HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaModel = service.generatePlanilhaModel();
			planilhasManager.setPlanilhas(planilhaModel);
			service.importBringInsertDataManySheet(planilhasManager,
					 service.getBytesManager(),
	        		t ->{
	        			ArrayList<?> arrayList = t.get(sheet);
	        			for (Object object : arrayList) {
	        				System.out.println(object.toString());
	        			}
	        			System.out.println("\n");
	        		});
		}
		
      }

	private static void readDataWithoutDesignPatterns() {
		try {
              byte[] customerFile = customerStream.readAllBytes();
              List<Customer> customers = OldCustomerImporter.importarDadosPlanilha(
                      "0", true, "A","B", "D", "F","G", customerFile);
              System.out.println("Clientes importados:");
              for (Object c : customers) {
                  System.out.println(c);
              }
              
              byte[] productFile = productStream.readAllBytes();
              List<Product> products = OldProductImporter.importarDadosPlanilha(
                      "0", true,"A", "B", "D", "C", productFile);
              System.out.println("\nProdutos importados:");
              for (Object p : products) {
                  System.out.println(p);
              }

          } catch (Exception e) {
              e.printStackTrace();
          }
	}
}