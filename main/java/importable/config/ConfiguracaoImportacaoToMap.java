package importable.config;

import java.lang.reflect.Field;
import java.util.HashMap;

/**
 * @author Fernando Dias
 */
public interface ConfiguracaoImportacaoToMap {

  /**
   * @return mapeamento de coluna valor de acordo com fields
   */
  public HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> getMappedFields();

  /**
   * @param tipoPlanilha -> a ter mapeamento de coluna e valor adicionados
   * @return modelo de planilha com mapeamento de acordo com tipoPlanilha
   */
  default PlanilhaModel createSheetMappedByFields(TipoPlanilhaImportacaoEnum tipoPlanilha) {
    PlanilhaModel planilhaModel = new PlanilhaModel();
    try {
      Field[] campos = this.getClass().getDeclaredFields();

      for (Field campo : campos) {
        TipoFieldPlanilha anotacaoTipo = campo
            .getAnnotation(TipoFieldPlanilha.class);

        if (anotacaoTipo != null
            && (anotacaoTipo.value() == TipoPlanilhaImportacaoEnum.GERAL
                || anotacaoTipo.value() == tipoPlanilha)) {

          campo.setAccessible(true);

          PlanilhaColuna anotacaoColuna = campo
              .getAnnotation(PlanilhaColuna.class);

          if (anotacaoColuna != null) {
            String nomeCampo = anotacaoColuna.value();
            Object valor = campo.get(this);

            if (valor != null) {
              planilhaModel.addColunaEValor(nomeCampo, valor.toString());
            }
          }

          PlanilhaLinha anotacaoLinha = campo
              .getAnnotation(PlanilhaLinha.class);
          if (anotacaoLinha != null) {
            String nomeCampo = anotacaoLinha.value();
            Object valor = campo.get(this);

            if (valor != null) {
              planilhaModel.addLinhaEValor(nomeCampo, valor.toString());
            }
          }

          PlanilhaConfiguracao configuracaoPlanilha = campo
              .getAnnotation(PlanilhaConfiguracao.class);
          if (configuracaoPlanilha != null) {
            String nomeCampo = configuracaoPlanilha.value();
            Object valor = campo.get(this);
            if (valor != null) {
              planilhaModel.addConfiguracaoEValor(nomeCampo,
                                                  valor.toString());
            }
          }
        }
      }
    } catch (Exception e) {
      throw new RuntimeException("Erro ao Mapear Valores de Configuração: ",
                                 e);
    }
    return planilhaModel;
  }
}
