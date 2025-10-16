package importable.model;

/**
 * Classe que representa a análise de projeção de um evento.
 *
 * @author Fernando Dias
 */
public class AnaliseProjecaoModelTest
  extends AbstractModelSheetValueTest {

  /**
   * Natureza do evento (Amortização, Juros, Encargos).
   */
  private String natureza;

  /**
   * Nome da tranche associada ao evento.
   */
  private String nomeTranche;

  /**
   * Construtor privado utilizando o padrão Builder.
   *
   * @param builder Objeto do Builder com os valores a serem atribuídos.
   */
  private AnaliseProjecaoModelTest(Builder builder) {
    super(builder.data, builder.valor, builder.valorMoedaLocal,
          builder.numeroLinha, builder.coluna, builder.tituloColuna);
    this.natureza = builder.natureza;
    this.nomeTranche = builder.nomeTranche;
  }

  /**
   * @return {@link #natureza}
   */
  public String getNatureza() {
    return natureza;
  }

  /**
   * @param natureza atualiza {@link #natureza}.
   */
  public void setNatureza(String natureza) {
    this.natureza = natureza;
  }

  /**
   * @return {@link #nomeTranche}
   */
  public String getNomeTranche() {
    return nomeTranche;
  }

  /**
   * @param nomeTranche atualiza {@link #nomeTranche}.
   */
  public void setNomeTranche(String nomeTranche) {
    this.nomeTranche = nomeTranche;
  }

  @Override
  public String toString() {
    return "AnaliseProjecaoModelTest {" + " data='"
        + getDataSheet().getValue() + '\'' + ", valor="
        + getValorSheet().getValue() + '}' + " natureza=" + natureza + "]";
  }

  /**
   * Classe Builder para a construção de instâncias de
   * {@link AnaliseProjecaoModelTest}.
   */
  public static class Builder {

    /**
     * Nome da tranche associada ao evento.
     */
    private String nomeTranche;

    /**
     * Natureza do evento.
     */
    private String natureza;

    /**
     * Data da análise.
     */
    private SheetData data;

    /**
     * Valor do evento.
     */
    private SheetValor valor;

    /**
     * Valor do evento em moeda.
     */
    private SheetValor valorMoedaLocal;

    /**
     * Número da linha da planilha.
     */
    private int numeroLinha;

    /**
     * Coluna da planilha.
     */
    private String coluna;

    /**
     * Titulo Coluna da planilha.
     */
    private String tituloColuna;

    /**
     * Define a data da análise.
     *
     * @param data A data do evento.
     * @return A própria instância do Builder para encadeamento de chamadas.
     */
    public Builder data(SheetData data) {
      this.data = data;
      return this;
    }

    /**
     * Define o valor do evento.
     *
     * @param valor O valor do evento.
     * @return A própria instância do Builder para encadeamento de chamadas.
     */
    public Builder valor(SheetValor valor) {
      this.valor = valor;
      return this;
    }

    /**
     * Define o valor do evento.
     *
     * @param valorMoedaLocal O valor do evento.
     * @return A própria instância do Builder para encadeamento de chamadas.
     */
    public Builder valorMoedaLocal(SheetValor valorMoedaLocal) {
      this.valorMoedaLocal = valorMoedaLocal;
      return this;
    }

    /**
     * Define a natureza do evento.
     *
     * @param natureza A natureza do evento.
     * @return A própria instância do Builder para encadeamento de chamadas.
     */
    public Builder natureza(String natureza) {
      this.natureza = natureza;
      return this;
    }

    /**
     * Define o nome da tranche associada ao evento.
     *
     * @param nomeTranche O nome da tranche.
     * @return A própria instância do Builder para encadeamento de chamadas.
     */
    public Builder nomeTranche(String nomeTranche) {
      this.nomeTranche = nomeTranche;
      return this;
    }

    /**
     * Define o número da linha da planilha.
     *
     * @param numeroLinha O número da linha.
     * @return A própria instância do Builder para encadeamento de chamadas.
     */
    public Builder numeroLinha(int numeroLinha) {
      this.numeroLinha = numeroLinha;
      return this;
    }

    /**
     * Define o número da coluna da planilha.
     *
     * @param coluna A coluna da planilha
     * @return A própria instância do Builder para encadeamento de chamadas.
     */
    public Builder coluna(String coluna) {
      this.coluna = coluna;
      return this;
    }

    /**
     * Define o titulo da coluna da planilha.
     *
     * @param tituloColuna A coluna da planilha
     * @return A própria instância do Builder para encadeamento de chamadas.
     */
    public Builder tituloColuna(String tituloColuna) {
      this.tituloColuna = tituloColuna;
      return this;
    }

    /**
     * Constrói uma nova instância de {@link AnaliseProjecaoModelTest} com os
     * valores fornecidos.
     *
     * @return Nova instância de AnaliseProjecaoModelTest.
     */
    public AnaliseProjecaoModelTest build() {
      return new AnaliseProjecaoModelTest(this);
    }

  }
}
