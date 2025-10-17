package importable.run;

import java.io.InputStream;
import java.util.List;

import importable.model.customer.Customer;
import importable.model.product.Product;
import importable.old.OldCustomerImporter;
import importable.old.OldProductImporter;

public class Main {
	
	static InputStream customerStream = Main.class.getClassLoader()
            .getResourceAsStream("customers.xlsx");
	
	static InputStream productStream = Main.class.getClassLoader()
             .getResourceAsStream("products.xlsx");
	 
    public static void main(String[] args) {
    	  readDataWithoutDesignPatterns();
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