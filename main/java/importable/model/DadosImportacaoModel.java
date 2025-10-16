package importable.model;

import java.util.HashMap;
import java.util.Map;

/**
 * Classe abstrata para representar os dados de um formulário de importação de
 * planilhas, suportando múltiplas abas e múltiplas colunas usando Mapas. Cada
 * aba pode ter suas próprias configurações, como a presença de cabeçalho e suas
 * colunas específicas.
 *
 * @author Fernando Dias
 */
public class DadosImportacaoModel {

  /**
   * Título do formulário.
   */
  private String titulo;

  /**
   * Mapa contendo as abas da planilha. A chave representa um identificador
   * único para a aba e o valor é o nome real da aba.
   */
  private Map<String, String> abasPlanilha = new HashMap<>();

  /**
   * Mapa indicando se cada aba contém cabeçalho. A chave representa o
   * identificador da aba e o valor é um booleano (true = tem cabeçalho).
   */
  private Map<String, Boolean> possuiCabecalho = new HashMap<>();

  /**
   * Mapa indicando em que linha iniciar contagem
   */
  private Map<String, Integer> iniciarConferenciaLinha = new HashMap<>();

  /**
   * Mapa contendo as colunas de data de cada aba. A chave é o identificador da
   * aba, e o valor é um outro mapa (chave: ID da coluna, valor: nome da
   * coluna).
   */
  private Map<String, Map<String, String>> colunasData = new HashMap<>();

  /**
   * Mapa contendo as colunas de valores de cada aba. A chave é o identificador
   * da aba, e o valor é um outro mapa (chave: ID da coluna, valor: nome da
   * coluna).
   */
  private Map<String, Map<String, String>> colunasValor = new HashMap<>();

  /**
   * @return {@link #titulo}
   */
  public String getTitulo() {
    return titulo;
  }

  /**
   * @param titulo atualiza {@link #titulo}.
   */
  public void setTitulo(String titulo) {
    this.titulo = titulo;
  }

  /**
   * @return {@link #abasPlanilha}
   */
  public Map<String, String> getAbasPlanilha() {
    return abasPlanilha;
  }

  /**
   * @param abasPlanilha atualiza {@link #abasPlanilha}.
   */
  public void setAbasPlanilha(Map<String, String> abasPlanilha) {
    this.abasPlanilha = abasPlanilha;
  }

  /**
   * @return {@link #possuiCabecalho}
   */
  public Map<String, Boolean> getPossuiCabecalho() {
    return possuiCabecalho;
  }

  /**
   * @param possuiCabecalho atualiza {@link #possuiCabecalho}.
   */
  public void setPossuiCabecalho(Map<String, Boolean> possuiCabecalho) {
    this.possuiCabecalho = possuiCabecalho;
  }

  /**
   * @param colunasData atualiza {@link #colunasData}.
   */
  public void setColunasData(Map<String, Map<String, String>> colunasData) {
    this.colunasData = colunasData;
  }

  /**
   * @param colunasValor atualiza {@link #colunasValor}.
   */
  public void setColunasValor(Map<String, Map<String, String>> colunasValor) {
    this.colunasValor = colunasValor;
  }

  /**
   * Adiciona uma aba ao mapa de abas e inicializa suas estruturas.
   *
   * @param key          Chave identificadora da aba.
   * @param value        Nome real da aba na planilha.
   * @param temCabecalho Indica se essa aba possui cabeçalho.
   */
  public void addAbaPlanilha(String key, String value,
                             boolean temCabecalho) {
    abasPlanilha.put(key, value);
    possuiCabecalho.put(key, temCabecalho);
    colunasData.put(key, new HashMap<>());  // Inicializa o mapa de colunas de
                                            // data para essa aba
    colunasValor.put(key, new HashMap<>()); // Inicializa o mapa de colunas de
                                            // valor para essa aba
  }

  /**
   * Adiciona uma aba ao mapa de abas e inicializa suas estruturas.
   *
   * @param key   Chave identificadora da aba.
   * @param value Nome real da aba na planilha.
   */
  public void addAbaPlanilha(String key, String value) {
    abasPlanilha.put(key, value);
    colunasData.put(key, new HashMap<>());  // Inicializa o mapa de colunas de
                                            // data para essa aba
    colunasValor.put(key, new HashMap<>()); // Inicializa o mapa de colunas de
                                            // valor para essa aba
  }

  /**
   * Adiciona uma aba ao mapa de abas e inicializa suas estruturas.
   *
   * @param key           Chave identificadora da aba.
   * @param value         Nome real da aba na planilha.
   * @param linhaContagem Indica linha de Contagem
   */
  public void addAbaPlanilha(String key, String value,
                             Integer linhaContagem) {
    abasPlanilha.put(key, value);
    iniciarConferenciaLinha.put(key, linhaContagem);
    colunasData.put(key, new HashMap<>());  // Inicializa o mapa de colunas de
                                            // data para essa aba
    colunasValor.put(key, new HashMap<>()); // Inicializa o mapa de colunas de
                                            // valor para essa aba
  }

  /**
   * @param key   do mapa
   * @param linha de Contagem
   */
  public void addLinhaInicialConferencia(String key, int linha) {
    iniciarConferenciaLinha.put(key, linha);
  }

  /**
   * Adiciona uma coluna de data para uma aba específica.
   *
   * @param abaKey     Chave identificadora da aba.
   * @param colunaKey  Chave identificadora da coluna de data.
   * @param colunaNome Nome real da coluna na planilha.
   */
  public void addColunaData(String abaKey, String colunaKey,
                            String colunaNome) {
    if (colunasData.containsKey(abaKey)) {
      colunasData.get(abaKey).put(colunaKey, colunaNome);
    }
  }

  /**
   * Adiciona uma coluna de valor para uma aba específica.
   *
   * @param abaKey     Chave identificadora da aba.
   * @param colunaKey  Chave identificadora da coluna de valor.
   * @param colunaNome Nome real da coluna na planilha.
   */
  public void addColunaValor(String abaKey, String colunaKey,
                             String colunaNome) {
    if (colunasValor.containsKey(abaKey)) {
      colunasValor.get(abaKey).put(colunaKey, colunaNome);
    }
  }

  /**
   * @return {@link #iniciarConferenciaLinha}
   */
  public Map<String, Integer> getIniciarConferenciaLinha() {
    return iniciarConferenciaLinha;
  }

  /**
   * @param iniciarConferenciaLinha atualiza {@link #iniciarConferenciaLinha}.
   */
  public void setIniciarConferenciaLinha(Map<String, Integer> iniciarConferenciaLinha) {
    this.iniciarConferenciaLinha = iniciarConferenciaLinha;
  }

  /**
   * @return {@link #colunasData}
   */
  public Map<String, Map<String, String>> getColunasData() {
    return colunasData;
  }

  /**
   * @return {@link #colunasValor}
   */
  public Map<String, Map<String, String>> getColunasValor() {
    return colunasValor;
  }
}
