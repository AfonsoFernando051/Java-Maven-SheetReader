package importable.service;

import importable.config.TipoPlanilhaImportacaoEnum;
import importable.model.CustomerImportationModel;
import importable.model.InterfacePlanilhaModel;
import importable.model.customer.Customer;

/**
 * Service class for importing Customer data from Excel spreadsheets
 * @author Fernando Dias
 */
public class ImportSheetCustomerService
  extends AbstractImportSheetService<Customer> {

  /**
   * Singleton
   */
  private static ImportSheetCustomerService instance;

  /**
   * @return instance
   */
  public static synchronized ImportSheetCustomerService getInstance() {
    if (instance == null) {
      instance = new ImportSheetCustomerService();
    }
    return instance;
  }

  @Override
  protected InterfacePlanilhaModel<Customer> getModelConfig(TipoPlanilhaImportacaoEnum tipo) {
	  return new CustomerImportationModel(Customer.class);
  }
}
