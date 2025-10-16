package importable.model;

import java.util.ArrayList;
import java.util.function.Function;


import importable.translator.Translator;

/**
 * @author fernando.dias
 */
public class AnaliseProjecaoLiberacaoModel
  implements InterfacePlanilhaModel<AnaliseProjecaoModelTest> {

  @Override
  public AnaliseProjecaoModelTest criarInstancia() {
    AnaliseProjecaoModelTest modelo = new AnaliseProjecaoModelTest.Builder()
        .build();
    return modelo;
  }

  @Override
  public Function<RowData, ArrayList<AnaliseProjecaoModelTest>> createModelsByRow() {
    ArrayList<AnaliseProjecaoModelTest> modelos = new ArrayList<AnaliseProjecaoModelTest>();
    return (row) -> {
      AnaliseProjecaoModelTest model = criarInstancia();
      model.setDataSheet((SheetData) row
          .getCelulaByIdentificador(Translator.DATA));
      model.setValorSheet((SheetValor) row
          .getCelulaByIdentificador(Translator.VALUE));
      if (model.getDataSheet() != null && model.getValorSheet() != null) {
        modelos.add(model);
      }
      return modelos;
    };
  }

}
