package importable.model.sheet;

/**
 * @author Fernando Dias
 * @param <T> Kind of value
 */
public abstract class AbstractSheet<T>
  implements ImportableSheet<T> {
	
  /**
   * Value from sheet
   */
  private T value;

  /**
   * Number of Line
   */
  private int lineNumber;

  /**
   * Coluna da planilha.
   */
  private String coluna;

  /**
   * Titulo Coluna da planilha.
   */
  private String tituloColuna;

  /**
   * @return {@link #value}
   */
  @Override
  public T getValue() {
    return value;
  }

  /**
   * @param value atualiza {@link #value}.
   */
  @Override
  public void setValue(T value) {
    this.value = value;
  }

  /**
   * @return {@link #lineNumber}
   */
  public int getLineNumber() {
	return lineNumber;
  }

  /**
   * @return {@link #lineNumber}
   */
  public void setLineNumber(int lineNumber) {
	 this.lineNumber = lineNumber;
  }

  /**
   * @return {@link #coluna}
   */
  public String getColuna() {
    return coluna;
  }

  /**
   * @param coluna atualiza {@link #coluna}.
   */
  public void setColuna(String coluna) {
    this.coluna = coluna;
  }

  /**
   * @return {@link #tituloColuna}
   */
  public String getTituloColuna() {
    return tituloColuna;
  }

  /**
   * @param tituloColuna atualiza {@link #tituloColuna}.
   */
  public void setTituloColuna(String tituloColuna) {
    this.tituloColuna = tituloColuna;
  }

  @Override
  public String toString() {
    return "AbstractSheet [value=" + value + ", numeroLinha=" + lineNumber
        + ", coluna=" + coluna + ", tituloColuna=" + tituloColuna + "]";
  }
}
