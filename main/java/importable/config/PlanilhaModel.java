package importable.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fernando Dias
 */
public class PlanilhaModel {

  /**
   * Nome da planilha
   */
  String nomeIdentificador;

  /**
   * Coluna da planilha e valor
   */
  HashMap<String, String> colunaEValor = new HashMap<String, String>();

  /**
   * Linha da planilha e valor
   */
  HashMap<String, String> linhaEValor = new HashMap<String, String>();

  /**
   * Configuração da planilha e valor
   */
  HashMap<String, String> configuracaoEValor = new HashMap<String, String>();

  /**
   * Componente relacionado à importação
   */
  private TipoPlanilhaImportacaoEnum tipoLogico;

  /**
   * Linha de cabecalho
   */
  private int cabecalho;

  /**
   * @return {@link #nomeIdentificador}
   */
  public String getNomeIdentificador() {
    return nomeIdentificador;
  }

  /**
   * @param nomeIdentificador atualiza {@link #nomeIdentificador}.
   */
  public void setNomeIdentificador(String nomeIdentificador) {
    this.nomeIdentificador = nomeIdentificador;
  }

  /**
   * @return {@link #colunaEValor}
   */
  public HashMap<String, String> getColunaEValor() {
    return colunaEValor;
  }

  /**
   * @param colunaEValor atualiza {@link #colunaEValor}.
   */
  public void setColunaEValor(HashMap<String, String> colunaEValor) {
    this.colunaEValor = colunaEValor;
  }

  /**
   * Adiciona uma nova coluna e seu valor ao mapa {@link #colunaEValor}.
   *
   * @param coluna - nome da coluna (chave)
   * @param valor  - valor associado à coluna
   */
  public void addColunaEValor(String coluna, String valor) {
    if (this.colunaEValor == null) {
      this.colunaEValor = new HashMap<>();
    }
    this.colunaEValor.put(coluna, valor);
  }

  /**
   * Adiciona uma nova coluna e seu valor ao mapa {@link #colunaEValor}.
   *
   * @param coluna - nome da coluna (chave)
   * @param valor  - valor associado à coluna
   */
  public void addLinhaEValor(String coluna, String valor) {
    if (this.linhaEValor == null) {
      this.linhaEValor = new HashMap<>();
    }
    this.linhaEValor.put(coluna, valor);
  }

  /**
   * Adiciona uma nova configuração e seu valor ao mapa
   * {@link #configuracaoEValor}.
   *
   * @param chave - nome da configuracao (chave)
   * @param valor - valor associado à coluna
   */
  public void addConfiguracaoEValor(String chave, String valor) {
    if (this.configuracaoEValor == null) {
      this.configuracaoEValor = new HashMap<>();
    }
    this.configuracaoEValor.put(chave, valor);

  }

  /**
   * @return {@link #tipoLogico}
   */
  public TipoPlanilhaImportacaoEnum getTipoLogico() {
    return tipoLogico;
  }

  /**
   * @param tipoLogico atualiza {@link #tipoLogico}.
   */
  public void setTipoLogico(TipoPlanilhaImportacaoEnum tipoLogico) {
    this.tipoLogico = tipoLogico;
  }

  /**
   * @return {@link #linhaEValor}
   */
  public HashMap<String, String> getLinhaEValor() {
    return linhaEValor;
  }

  /**
   * @param linhaEValor atualiza {@link #linhaEValor}.
   */
  public void setLinhaEValor(HashMap<String, String> linhaEValor) {
    this.linhaEValor = linhaEValor;
  }

  /**
   * @return {@link #cabecalho}
   */
  public int getCabecalho() {
    return cabecalho;
  }

  /**
   * @param cabecalho atualiza {@link #cabecalho}.
   */
  public void setCabecalho(int cabecalho) {
    this.cabecalho = cabecalho;
  }

  /**
   * @return {@link #configuracaoEValor}
   */
  public Map<String, String> getConfiguracaoEValor() {
    return configuracaoEValor;
  }
}
