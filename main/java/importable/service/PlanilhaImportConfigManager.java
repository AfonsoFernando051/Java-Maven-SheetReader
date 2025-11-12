package importable.service;

import java.util.HashMap;

import importable.config.SheetModel;
import importable.config.SheetTypeEnum;

/**
 * @author Fernando Dias
 */
public class PlanilhaImportConfigManager {

  /**
   * Lista de planilhas a serem importadas
   */
  private HashMap<SheetTypeEnum, SheetModel> planilhas = new HashMap<SheetTypeEnum, SheetModel>();

  /**
   * Retorna a única planilha cadastrada, se houver exatamente uma.
   *
   * @return a única {@link SheetModel} presente no mapa
   * @throws IllegalStateException se não houver nenhuma ou houver mais de uma
   *                               planilha
   */
  public SheetModel getUniquePlanilha() {
    if (planilhas == null || planilhas.isEmpty()) {
      throw new IllegalStateException("Nenhuma planilha foi registrada.");
    }
    if (planilhas.size() > 1) {
      throw new IllegalStateException("Mais de uma planilha foi registrada. Use o tipo específico para recuperar.");
    }
    return planilhas.values().iterator().next();
  }

  /**
   * @param tipo     da planilha
   * @param planilha atualiza {@link #planilhas}.
   */
  public void addPlanilhas(SheetTypeEnum tipo,
                           SheetModel planilha) {
    if (null != this.planilhas) {
      this.planilhas.put(tipo, planilha);
    } else {
      this.planilhas = new HashMap<SheetTypeEnum, SheetModel>();
      this.planilhas.put(tipo, planilha);
    }
  }

  /**
   * @return {@link #planilhas}
   */
  public HashMap<SheetTypeEnum, SheetModel> getPlanilhas() {
    return planilhas;
  }

  /**
   * @return {@link #planilhas}
   */
  public SheetModel getPlanilhaByTipo(SheetTypeEnum tipo) {
    return this.planilhas.get(tipo);
  }

  /**
   * @param planilhas atualiza {@link #planilhas}.
   */
  public void setPlanilhas(HashMap<SheetTypeEnum, SheetModel> planilhas) {
    this.planilhas = planilhas;
  }

}
