package importable.service;


import importable.config.TipoPlanilhaImportacaoEnum;
import importable.model.AnaliseProjecaoLiberacaoModel;
import importable.model.AnaliseProjecaoModelTest;
import importable.model.InterfacePlanilhaModel;

/**
 * @author Fernando Dias
 */
public class ImportacaoPlanilhaAnaliseTesteService
  extends AbstractImportarPlanilhaService<AnaliseProjecaoModelTest> {

  /**
   * Singleton
   */
  private static ImportacaoPlanilhaAnaliseTesteService instance;

  /**
   * @return instance
   */
  public static synchronized ImportacaoPlanilhaAnaliseTesteService getInstance() {
    if (instance == null) {
      instance = new ImportacaoPlanilhaAnaliseTesteService();
    }
    return instance;
  }

  @Override
  protected InterfacePlanilhaModel<AnaliseProjecaoModelTest> getModelConfig(TipoPlanilhaImportacaoEnum tipo) {
    switch (tipo) {
    case LIBERACAO:
      return new AnaliseProjecaoLiberacaoModel();
    default:
      throw new IllegalArgumentException("Tipo de planilha não suportado: "
          + tipo);
    }
  }

}
