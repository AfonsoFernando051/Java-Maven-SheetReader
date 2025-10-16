package importable.model;

/**
 * @author Fernando Dias
 */
public class DadosImportacaoAnalise
  extends DadosImportacaoModel {

  /**
   * Habilitar teste de valores importados
   */
  private boolean habilitarTeste = false;

  /**
   * linha de Inicio de Conferencia de Encargos
   */
  private int linhaInicioConferenciaEncargos;

  /**
   * linha de Inicio de Conferencia de Liberacoes
   */
  private int linhaInicioConferenciaLiberacoes;

  /**
   * @return {@link #habilitarTeste}
   */
  public boolean isHabilitarTeste() {
    return habilitarTeste;
  }

  /**
   * @param habilitarTeste atualiza {@link #habilitarTeste}.
   */
  public void setHabilitarTeste(boolean habilitarTeste) {
    this.habilitarTeste = habilitarTeste;
  }

  /**
   * @return {@link #linhaInicioConferenciaEncargos}
   */
  public int getLinhaInicioConferenciaEncargos() {
    return linhaInicioConferenciaEncargos;
  }

  /**
   * @param linhaInicioConferenciaEncargos atualiza
   *                                       {@link #linhaInicioConferenciaEncargos}.
   */
  public void setLinhaInicioConferenciaEncargos(int linhaInicioConferenciaEncargos) {
    this.linhaInicioConferenciaEncargos = linhaInicioConferenciaEncargos;
  }

  /**
   * @return {@link #linhaInicioConferenciaLiberacoes}
   */
  public int getLinhaInicioConferenciaLiberacoes() {
    return linhaInicioConferenciaLiberacoes;
  }

  /**
   * @param linhaInicioConferenciaLiberacoes atualiza
   *                                         {@link #linhaInicioConferenciaLiberacoes}.
   */
  public void setLinhaInicioConferenciaLiberacoes(int linhaInicioConferenciaLiberacoes) {
    this.linhaInicioConferenciaLiberacoes = linhaInicioConferenciaLiberacoes;
  }

}
