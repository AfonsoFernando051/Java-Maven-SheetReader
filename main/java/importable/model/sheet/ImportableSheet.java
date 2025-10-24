package importable.model.sheet;

/**
 * @author Fernando Dias
 * @param <T> Tipo do valor armazenado
 */
public interface ImportableSheet<T> {

  /**
   * @return Valor
   */
  public T getValue();

  /**
   * @param value - Valor
   */
  public void setValue(T value);
}
