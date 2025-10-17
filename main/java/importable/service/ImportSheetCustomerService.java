package importable.service;

import java.io.InputStream;
import java.util.HashMap;

import importable.config.PlanilhaModel;
import importable.config.TipoPlanilhaImportacaoEnum;
import importable.model.CustomerImportationModel;
import importable.model.InterfacePlanilhaModel;
import importable.model.customer.Customer;
import importable.run.Main;
import importable.translator.Translator;

/**
 * Service class for importing Customer data from Excel spreadsheets
 * @author Fernando Dias
 */
public class ImportSheetCustomerService
  extends AbstractImportSheetService<Customer> {

  /**
   * Singleton
   */
  private  static ImportSheetCustomerService instance;

  /**
   * @return instance
   */
  public  static synchronized ImportSheetCustomerService getInstance() {
    if (instance == null) {
      instance = new ImportSheetCustomerService();
    }
    return instance;
  }

  @Override
  protected InterfacePlanilhaModel<Customer> getModelConfig(TipoPlanilhaImportacaoEnum tipo) {
	  return new CustomerImportationModel(Customer.class);
  }

	@Override
	public HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> generatePlanilhaModel() {
	    HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
	    PlanilhaModel planilha = new PlanilhaModel();
	    planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.CUSTOMERS.name());
	    planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.CUSTOMERS);
	    
	    planilha.addColunaEValor(Translator.ID, "A");
	    planilha.addColunaEValor(Translator.NAME, "B");
	    planilha.addColunaEValor(Translator.CPF, "C");
	    planilha.addColunaEValor(Translator.EMAIL, "D");
	    planilha.addColunaEValor(Translator.BIRTH_DATE, "E");
	    planilha.addColunaEValor(Translator.CITY, "F");
	    planilha.addColunaEValor(Translator.STATE, "G");

	    planilhaMap.put(TipoPlanilhaImportacaoEnum.CUSTOMERS, planilha);
	    return planilhaMap;
	}

	@Override
	protected InputStream getStream() {
		return Main.class.getClassLoader()
	             .getResourceAsStream("customers.xlsx");
	}
}
