package importable.model;

import java.util.ArrayList;
import java.util.function.Function;

import importable.utils.ProcessamentoArquivoException;

/**
 * @author Fernando Dias
 * @param <T> -> Tipo a ser alterado em subclasses
 */
public abstract class GenericImportModel<T>
  implements ImportModelValidator<T>, InterfacePlanilhaModel<T> {

  /**
   * Generico
   */
  private final Class<T> tipo;


  /**
   * @param tipo a ser manipulado
   */
  public GenericImportModel(Class<T> tipo) {
    this.tipo = tipo;
  }

  /**
   * @return instancia de Lancamento
   */
  @Override
  public T criarInstancia() {
    try {
      return tipo.getDeclaredConstructor().newInstance();
    } catch (Exception e) {
      throw new RuntimeException("Não foi possível instanciar "
          + tipo.getName(), e);
    }
  }

  @Override
  public Function<RowData, ArrayList<T>> createModelsByRow() {
    return row -> {
      try {
        ArrayList<T> modelos = processRow().apply(row);
        for (T modelo : modelos) {
          validate(modelo, row);
        }

        return modelos;

      } catch (ProcessamentoArquivoException e) {
//        log.warn("Erro de processamento na linha {}: {}",
//                 row.getNumeroLinha(), e.getMessage());
        throw e;
      } catch (ClassCastException e) {
        String mensagem = String
            .format("Erro de tipo inesperado ao processar linha %d: %s",
                    row.getNumeroLinha(), e.getMessage());
//        log.error(mensagem, e);
        throw new ProcessamentoArquivoException(mensagem, e);
      } catch (Exception e) {
        String mensagem = String
            .format("Erro inesperado ao processar linha %d: %s",
                    row.getNumeroLinha(), e.getMessage());
//        log.error(mensagem, e);
        throw new ProcessamentoArquivoException(mensagem, e);
      }
    };
  }

  /**
   * @return implementação de leitura de linha e importação
   */
  protected abstract Function<RowData, ArrayList<T>> processRow();

  /**
   * Faz cast seguro de ImportableSheet, gerando exceção clara se o tipo não for
   * o esperado.
   *
   * @param <T>           Tipo esperado
   * @param celula        Célula a ser convertida
   * @param expectedClass Classe esperada
   * @return Objeto convertido
   * @throws ProcessamentoPlanilhaException se o tipo não for o esperado
   * @author Fernando Dias
   */
  @SuppressWarnings({ "unchecked", "hiding" })
  protected <T extends ImportableSheet<?>> T safeCast(ImportableSheet<?> celula,
                                                      Class<T> expectedClass) {
    if (expectedClass.isInstance(celula)) {
      return (T) celula;
    }

    AbstractSheet<?> abstractSheet = (AbstractSheet<?>) celula;
    throw new ProcessamentoArquivoException(String
        .format("Erro de tipo na planilha: esperado %s, encontrado %s na linha %d, coluna %s, título %s.",
                expectedClass.getSimpleName(),
                celula.getClass().getSimpleName(),
                abstractSheet.getNumeroLinha(), abstractSheet.getColuna(),
                abstractSheet.getTituloColuna()));
  }

  /**
   * Converte qualquer ImportableSheet para String.
   *
   * @param sheet ImportableSheet (SheetValor ou SheetString)
   * @return valor em String, ou null se vazio
   */
  public String sheetToString(ImportableSheet<?> sheet) {

    if (sheet instanceof SheetValor) {
      return safeCast(sheet, SheetValor.class).getValue().toBigInteger()
          .toString();
    } else if (sheet instanceof SheetString) {
      return safeCast(sheet, SheetString.class).getValor();
    }
    return null;
  }

  /**
   * Monta mensagem informando que não foi encontrada referência para o objeto
   *
   * @param importableSheet Metadados da célula/coluna/linha em análise
   * @return Mensagem formatada
   */
  @SuppressWarnings("unchecked")
  public String buildNullObjMsg(ImportableSheet<?> importableSheet) {
    AbstractSheet<T> sheet = (AbstractSheet<T>) importableSheet;
//    return Translator.REFERENCIA_NAO_ENCONTRADA_MSG,
//                        sheet.getNumeroLinha(), sheet.getColuna(),
//                        sheet.getTituloColuna();
    return "";
  }

}
