package importable.mapper;

import java.util.ArrayList;
import java.util.function.Function;

import importable.model.row.RowData;

/**
 * @author Fernando Dias
 * @param <T> -> Tipo genérico
 */
public interface InterfacePlanilhaMapper<T> {

  /**
   * @return instancia concreta de modelo de planilha
   */
  T criarInstancia();

  /**
   * @return mapeamento de colunas
   */
  // Map<String, Consumer<RowData>> getMapeamentoColunas();

  /**
   * @return mapeamento de colunas
   */
  Function<RowData, ArrayList<T>> createModelsByRow();

}
