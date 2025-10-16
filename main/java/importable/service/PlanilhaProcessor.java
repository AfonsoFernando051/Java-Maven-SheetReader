package importable.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Workbook;

import importable.config.PlanilhaModel;
import importable.model.AnaliseProjecaoModelTest;
import importable.model.CelulaData;

/**
 * @author Fernando Dias
 * @param <T> - Tipo generico
 */
public interface PlanilhaProcessor<T> {
  /**
   * Processa linhas da planilha e retorna lista com valores
   *
   * @param workbook de importação
   * @param planilha a ser processada
   * @return valores importados
   * @throws IOException Exception de stream
   */
  List<T> processar(Workbook workbook, PlanilhaModel planilha)
    throws IOException;

  /**
   * @param row
   * @param data
   * @param valor
   * @param natureza
   * @param celula
   * @return
   */
  AnaliseProjecaoModelTest criarModelo(Row row, LocalDate data,
                                       BigDecimal valor, String natureza,
                                       CelulaData celula);

}
