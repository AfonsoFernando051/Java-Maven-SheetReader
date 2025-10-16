package importable.service;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Consumer;

import org.apache.poi.EncryptedDocumentException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

import importable.config.PlanilhaModel;
import importable.config.TipoPlanilhaImportacaoEnum;
import importable.model.InterfacePlanilhaModel;
import importable.utils.ProcessamentoArquivoException;
import importable.utils.SaveBytesManager;

/**
 * @author Fernando Dias
 * @param <T> - Tipo a ser importado
 */
public abstract class AbstractImportarPlanilhaService<T> {

  /**
   * Dados extraidos do formulario
   */
  PlanilhaImportConfigManager planilhaImportConfigManager;

  /**
   * Classe que gerencia input stream
   */
  protected SaveBytesManager bytesController;

  /**
   * Modelo de Importação
   */
  private PlanilhaModel planilhaAtual;

  /**
   * Total de Eventos importados
   */
  private long totalItensImportados = 0;

  /**
   * Modelo de Importação
   */
  private HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhas = new HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel>();

  /**
   * @param planilhaImportConfigManager - Gerencia dados do form
   * @param bytesController             - Gerencia input stream
   */
  public void configurarDadosPlanilha(PlanilhaImportConfigManager planilhaImportConfigManager,
                                      SaveBytesManager bytesController) {
    setPlanilhaImportConfigManager(planilhaImportConfigManager);
    setBytesController(bytesController);
    configPlanilha(planilhaImportConfigManager);
  }

  /**
   * @param configuracao - configura planilha/planilhas
   */
  private void configPlanilha(PlanilhaImportConfigManager configuracao) {
    if (configuracao.getPlanilhas().size() == 1) {
      setPlanilhaAtual(configuracao.getUniquePlanilha());
      setPlanilhas(configuracao.getPlanilhas());
    } else {
      setPlanilhas(configuracao.getPlanilhas());
    }

  }

  /**
   * @param cell - celula
   * @return true se for invalida(nula ou vazia)
   */
  protected boolean isCelulaInvalida(Cell cell) {
    return cell == null || cell.getCellType() == CellType.BLANK;
  }

  /**
   * Importação de dados da planilha
   *
   * @param planilhaImportConfigManager - Gerencia dados do form
   * @param bytesController             - Gerencia input stream
   * @param callback                    - ato de refresh em grid
   */
  public void importBringDataManySheet(PlanilhaImportConfigManager planilhaImportConfigManager,
                                       SaveBytesManager bytesController,
                                       Consumer<HashMap<TipoPlanilhaImportacaoEnum, ArrayList<T>>> callback) {
    try {
      configurarDadosPlanilha(planilhaImportConfigManager, bytesController);
      HashMap<TipoPlanilhaImportacaoEnum, ArrayList<T>> dados = getDadosByPlanilhas();
      Set<TipoPlanilhaImportacaoEnum> keySet = dados.keySet();
      for (TipoPlanilhaImportacaoEnum tipoPlanilhaImportacaoEnum : keySet) {
        totalItensImportados += dados.get(tipoPlanilhaImportacaoEnum)
            .stream().count();
      }
      bytesController.closeFileData();
      callback.accept(dados);
    } catch (EncryptedDocumentException | IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * Importação de dados da planilha
   *
   * @param planilhaImportConfigManager - Gerencia dados do form
   * @param bytesController             - Gerencia input stream
   * @param callback                    - ato de refresh em grid
   */
  public void importBringInsertDataManySheet(PlanilhaImportConfigManager planilhaImportConfigManager,
                                             SaveBytesManager bytesController,
                                             Consumer<HashMap<TipoPlanilhaImportacaoEnum, ArrayList<T>>> callback) {
    try {
      configurarDadosPlanilha(planilhaImportConfigManager, bytesController);
      HashMap<TipoPlanilhaImportacaoEnum, ArrayList<T>> dados = getDadosByPlanilhas();
      Set<TipoPlanilhaImportacaoEnum> keySet = dados.keySet();
      for (TipoPlanilhaImportacaoEnum tipoPlanilhaImportacaoEnum : keySet) {
        insertDadosPlanilha(dados.get(tipoPlanilhaImportacaoEnum));
      }
      bytesController.closeFileData();
      callback.accept(dados);
    } catch (EncryptedDocumentException | IOException e) {
      e.printStackTrace();
    }

  }

  /**
   * @return {@link #totalItensImportados}
   */
  public long getTotalItensImportados() {
    return totalItensImportados;
  }

  /**
   * Zera qtd itens importados
   */
  public void zerarTotalItensImportados() {
    this.totalItensImportados = 0;
  }

  /**
   * @param totalItensImportados atualiza {@link #totalItensImportados}.
   */
  public void setTotalItensImportados(long totalItensImportados) {
    this.totalItensImportados = totalItensImportados;
  }

  /**
   * Incrementa os itens importados
   */
  public void incrementarItensImportados() {
    this.totalItensImportados += 1;
  }

  /**
   * @throws IOException exceção de InputStream
   * @return planilhas mapeadas por tipos
   */
  protected HashMap<TipoPlanilhaImportacaoEnum, ArrayList<T>> getDadosByPlanilhas()
    throws IOException {
    HashMap<TipoPlanilhaImportacaoEnum, ArrayList<T>> resultados = new HashMap<>();
    for (Map.Entry<TipoPlanilhaImportacaoEnum, PlanilhaModel> entry : getPlanilhas()
        .entrySet()) {
      TipoPlanilhaImportacaoEnum tipo = entry.getKey();
      setPlanilhaAtual(entry.getValue());

      try (Workbook workbook = generateWorkbook()) {
        InterfacePlanilhaModel<T> modelConfig = getModelConfig(tipo);
        GenericPlanilhaProcessor<T> processor = new GenericPlanilhaProcessor<>(modelConfig);
        resultados.put(tipo,
                       processor.processar(workbook, getPlanilhaAtual()));
      } catch (ProcessamentoArquivoException e) {
        throw new IOException(e.getMessage());
      }
    }

    return resultados;
  }

  /**
   * @param tipo de planilha
   * @return configuração de modelo
   */
  protected abstract InterfacePlanilhaModel<T> getModelConfig(TipoPlanilhaImportacaoEnum tipo);

  /**
   * @param dados - lista de elementos
   */
  protected void insertDadosPlanilha(List<T> dados) {

  };

  /**
   * Valida se o evento a ser inserido já existe
   *
   * @param dados - a serem validados
   * @return lista com dados validados
   */
  protected List<T> validateEventosExistentes(List<T> dados) {
    return dados;
  }

  /**
   * @return Workbook de acordo com arquivo
   * @throws IOException erro de streaming
   */
  protected Workbook generateWorkbook()
    throws IOException {
    ByteArrayInputStream inputStream = bytesController
        .getNewInputWithBytes();
    Workbook workXssf = WorkbookFactory.create(inputStream);
    return workXssf;
  }

  /**
   * @return {@link #planilhaImportConfigManager}
   */
  public PlanilhaImportConfigManager getPlanilhaImportConfigManager() {
    return planilhaImportConfigManager;
  }

  /**
   * @param planilhaImportConfigManager atualiza
   *                                    {@link #planilhaImportConfigManager}.
   */
  public void setPlanilhaImportConfigManager(PlanilhaImportConfigManager planilhaImportConfigManager) {
    this.planilhaImportConfigManager = planilhaImportConfigManager;
  }

  /**
   * @return {@link #bytesController}
   */
  public SaveBytesManager getBytesController() {
    return bytesController;
  }

  /**
   * @param bytesController atualiza {@link #bytesController}.
   */
  public void setBytesController(SaveBytesManager bytesController) {
    this.bytesController = bytesController;
  }

  /**
   * @return {@link #planilhaAtual}
   */
  public PlanilhaModel getPlanilhaAtual() {
    return planilhaAtual;
  }

  /**
   * @param planilhaAtual atualiza {@link #planilhaAtual}.
   */
  public void setPlanilhaAtual(PlanilhaModel planilhaAtual) {
    this.planilhaAtual = planilhaAtual;
  }

  /**
   * @return {@link #planilhas}
   */
  public HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> getPlanilhas() {
    return planilhas;
  }

  /**
   * @param planilhas atualiza {@link #planilhas}.
   */
  public void setPlanilhas(HashMap<TipoPlanilhaImportacaoEnum, PlanilhaModel> planilhas) {
    this.planilhas = planilhas;
  }

}
