package importable.run;

import java.io.InputStream;
import java.util.List;

import importable.model.customer.Customer;
import importable.model.product.Product;
import importable.old.OldCustomerImporter;
import importable.old.OldProductImporter;

public class Main {

    public static void main(String[] args) {
        try {
            // Importação de Customer via classloader
            InputStream customerStream = Main.class.getClassLoader()
                    .getResourceAsStream("customers.xlsx");
            if (customerStream == null) {
                System.out.println("Arquivo customers.xlsx não encontrado!");
                return;
            }
            byte[] customerFile = customerStream.readAllBytes();
            List<Customer> customers = OldCustomerImporter.importarDadosPlanilha(
                    "0", true, "B", "D", "F", customerFile);
            System.out.println("Clientes importados:");
            for (Object c : customers) {
                System.out.println(c);
            }

            // Importação de Product via classloader
            InputStream productStream = Main.class.getClassLoader()
                    .getResourceAsStream("products.xlsx");
            if (productStream == null) {
                System.out.println("Arquivo products.xlsx não encontrado!");
                return;
            }
            byte[] productFile = productStream.readAllBytes();
            List<Product> products = OldProductImporter.importarDadosPlanilha(
                    "0", true, "B", "D", "C", productFile);
            System.out.println("\nProdutos importados:");
            for (Object p : products) {
                System.out.println(p);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
