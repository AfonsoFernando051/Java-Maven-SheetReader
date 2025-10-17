package importable.service;

import importable.config.TipoPlanilhaImportacaoEnum;
import importable.model.InterfacePlanilhaModel;
import importable.model.ProductImportationModel;
import importable.model.product.Product;

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
}
