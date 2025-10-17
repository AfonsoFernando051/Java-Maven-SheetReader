package importable.service;


import importable.config.TipoPlanilhaImportacaoEnum;

/**
 * Factory for import services
 */
public class ImportServiceFactory {
  
  @SuppressWarnings("unchecked")
  public static <T> ImportService<T> getService(Class<T> entityClass) {
    if (entityClass.equals(importable.model.product.Product.class)) {
      return (ImportService<T>) ImportSheetProductsService.getInstance();
    } else if (entityClass.equals(importable.model.customer.Customer.class)) {
      return (ImportService<T>) ImportSheetCustomerService.getInstance();
    }
    throw new IllegalArgumentException("Serviço não encontrado para a classe: " + entityClass);
  }
  
  public static ImportService<?> getServiceByType(TipoPlanilhaImportacaoEnum tipo) {
    switch (tipo) {
      case PRODUCTS:
        return (ImportService<?>) ImportSheetProductsService.getInstance();
      case CUSTOMERS:
        return (ImportService<?>) ImportSheetCustomerService.getInstance();
      default:
        throw new IllegalArgumentException("Serviço não encontrado para o tipo: " + tipo);
    }
  }
}