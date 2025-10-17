package importable.service;

import java.io.InputStream;
import java.util.HashMap;

import importable.config.PlanilhaModel;
import importable.config.TipoPlanilhaImportacaoEnum;
import importable.model.InterfacePlanilhaModel;
import importable.model.ProductImportationModel;
import importable.model.product.Product;
import importable.run.Main;
import importable.translator.Translator;

/**
 * Service class for importing Customer data from Excel spreadsheets
 * @author Fernando Dias
 */
public class ImportSheetProductsService
  extends AbstractImportSheetService<Product> {

  /**
   * Singleton
   */
  private static ImportSheetProductsService instance;

  /**
   * @return instance
   */
  public static synchronized ImportSheetProductsService getInstance() {
    if (instance == null) {
      instance = new ImportSheetProductsService();
    }
    return instance;
  }

  @Override
  protected InterfacePlanilhaModel<Product> getModelConfig(TipoPlanilhaImportacaoEnum tipo) {
	  return new ProductImportationModel(Product.class);
  }
  

  public HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> generatePlanilhaModel() {
    HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhaMap = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();
    PlanilhaModel planilha = new PlanilhaModel();
    planilha.setNomeIdentificador("0 - "
        + TipoPlanilhaImportacaoEnum.PRODUCTS.name());
    planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.PRODUCTS);
    planilha.setNomeIdentificador("0 - " + TipoPlanilhaImportacaoEnum.PRODUCTS.name());
    planilha.setTipoLogico(TipoPlanilhaImportacaoEnum.PRODUCTS);
    
    planilha.addColunaEValor(Translator.ID, "A");
    planilha.addColunaEValor(Translator.NAME, "B");
    planilha.addColunaEValor(Translator.CATEGORY, "C");
    planilha.addColunaEValor(Translator.PRICE, "D");
    planilha.addColunaEValor(Translator.QUANTITY, "E");
    planilha.addColunaEValor(Translator.REGISTRATION_DATE, "F");

    planilhaMap.put(TipoPlanilhaImportacaoEnum.PRODUCTS, planilha);
    return planilhaMap;
  }

  @Override
	protected InputStream getStream() {
		return Main.class.getClassLoader()
	             .getResourceAsStream("products.xlsx");
	}
}
