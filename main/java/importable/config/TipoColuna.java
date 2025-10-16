package importable.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Define o tipo lógico de uma planilha associada a um componente.
 *
 * @author Fernando Dias
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface TipoColuna {

  /**
   * Valor do tipo lógico de uma planilha
   */
  TipoPlanilhaImportacaoEnum value();
}
