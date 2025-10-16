package importable.model;

/**
 * @author Fernando Dias
 */
public class SheetString
  extends AbstractSheet<String> {

  /**
   * Valor da análise.
   */
  private String valor;

  /**
   * @return {@link #valor}
   */
  public String getValor() {
    return valor;
  }

  /**
   * @param valor atualiza {@link #valor}.
   */
  public void setValor(String valor) {
    this.valor = valor;
  }

}
