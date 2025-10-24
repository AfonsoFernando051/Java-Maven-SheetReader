package importable.service;


import importable.config.TipoPlanilhaImportacaoEnum;
import importable.model.customer.Customer;
import importable.model.product.Product;

/**
 * Factory for import services
 */
public class ImportServiceFactory {
  
  @SuppressWarnings("unchecked")
  public static <T> ImportService<T> getService(Class<T> entityClass) {
    if (entityClass.equals(importable.model.product.Product.class)) {
    	return (ImportService<T>) new ImportSheetService<Product>();
    } else if (entityClass.equals(importable.model.customer.Customer.class)) {
    	return (ImportService<T>) new ImportSheetService<Customer>();
    }
    throw new IllegalArgumentException("Serviço não encontrado para a classe: " + entityClass);
  }
  
  public static ImportService<?> getServiceByType(TipoPlanilhaImportacaoEnum tipo) {
    switch (tipo) {
      case PRODUCTS:
        return (ImportService<?>) new ImportSheetService<Product>();
      case CUSTOMERS:
          return (ImportService<?>) new ImportSheetService<Customer>();
      default:
        throw new IllegalArgumentException("Serviço não encontrado para o tipo: " + tipo);
    }
  }
}