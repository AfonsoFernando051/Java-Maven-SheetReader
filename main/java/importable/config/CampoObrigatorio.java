package importable.config;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Indica que o campo do formulário é obrigatório.
 *
 * @author Fernando Dias
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.FIELD)
public @interface CampoObrigatorio {

  /**
   * Mensagem enviada quando campo é vazio
   */
  String mensagem() default "Campo Obrigatório";
}
