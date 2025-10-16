package importable.model;

/**
 * @author Fernando Dias
 */
public abstract class AbstractModelSheetValueTest {

  /**
   * Data do teste
   */
  private SheetData data;

  /**
   * Valor associado ao teste
   */
  private SheetValor valor;

  /**
   * Valor associado ao teste
   */
  private SheetValor valorMoedaLocal;

  /**
   * Construtor padrão da classe ModelTeste.
   */
  public AbstractModelSheetValueTest() {
  }

  /**
   * Construtor da classe ModelTeste com parâmetros.
   *
   * @param data            A data do teste.
   * @param valor           O valor associado ao teste.
   * @param valorMoedaLocal O valor associado ao teste.
   * @param numeroLinha     da planila
   * @param coluna          da planila
   * @param tituloColuna    da planila
   */
  public AbstractModelSheetValueTest(SheetData data, SheetValor valor,
                                     SheetValor valorMoedaLocal,
                                     int numeroLinha, String coluna,
                                     String tituloColuna) {
    this.data = data;
    this.valor = valor;
    this.valorMoedaLocal = valorMoedaLocal;
  }

  /**
   * Retorna a data do teste.
   *
   * @return A data do teste.
   */
  public SheetData getDataSheet() {
    return data;
  }

  /**
   * Define a data do teste.
   *
   * @param data A data do teste.
   */
  public void setDataSheet(SheetData data) {
    this.data = data;
  }

  /**
   * Retorna o valor associado ao teste.
   *
   * @return O valor associado ao teste.
   */
  public SheetValor getValorSheet() {
    return valor;
  }

  /**
   * Define o valor associado ao teste.
   *
   * @param valor O valor associado ao teste.
   */
  public void setValorSheet(SheetValor valor) {
    this.valor = valor;
  }

  /**
   * Retorna o valor associado ao teste.
   *
   * @return O valor associado ao teste.
   */
  public SheetValor getValorMoedaLocalSheet() {
    return valorMoedaLocal;
  }

  /**
   * Define o valor associado ao teste.
   *
   * @param valorMoedaLocal O valor associado ao teste.
   */
  public void setValorMoedaLocalSheet(SheetValor valorMoedaLocal) {
    this.valorMoedaLocal = valorMoedaLocal;
  }

  /**
   * Retorna uma representação em string do objeto ModelTeste.
   *
   * @return Uma string no formato: "ModelTeste{data='...', valor=...}".
   */
  @Override
  public String toString() {
    return "ModelTeste{" + "data='" + data + '\'' + ", valor=" + valor
        + '}';
  }
}
