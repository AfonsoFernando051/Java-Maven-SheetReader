package importable.model.sheet;

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
  public String getValue() {
    return valor;
  }

  /**
   * @param valor atualiza {@link #valor}.
   */
  public void setValue(String valor) {
    this.valor = valor;
  }

}
