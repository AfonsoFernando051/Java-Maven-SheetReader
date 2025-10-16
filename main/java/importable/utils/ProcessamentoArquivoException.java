package importable.utils;

/**
 * @author Fernando Dias
 */
public class ProcessamentoArquivoException
  extends RuntimeException {

  /**
   * Serial ID
   */
  private static final long serialVersionUID = 1L;

  /**
   * Cria uma nova exceção com a mensagem especificada.
   *
   * @param message Mensagem detalhando o erro.
   */
  public ProcessamentoArquivoException(String message) {
    super(message);
  }

  /**
   * Cria uma nova exceção com a mensagem e a causa especificadas.
   *
   * @param message Mensagem detalhando o erro.
   * @param cause   Causa original do erro.
   */
  public ProcessamentoArquivoException(String message, Throwable cause) {
    super(message, cause);
  }
}
