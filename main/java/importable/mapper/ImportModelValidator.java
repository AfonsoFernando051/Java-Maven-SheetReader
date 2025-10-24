package importable.mapper;

import importable.model.row.RowData;
import importable.utils.ProcessamentoArquivoException;

/**
 * Interface que define um contrato de validação para modelos de importação.
 *
 * @param <T> Tipo do objeto validado
 * @author Fernando Dias
 */
public interface ImportModelValidator<T> {

  /**
   * Valida o objeto fornecido.
   *
   * @param object Objeto a ser validado
   * @param row    -> linha a ser validada
   * @throws ProcessamentoPlanilhaException se inválido
   */
  void validate(T object, RowData row)
    throws ProcessamentoArquivoException;

}
