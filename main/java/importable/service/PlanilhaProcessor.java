package importable.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import importable.config.SheetModel;

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
  List<T> processar(Workbook workbook, SheetModel planilha)
    throws IOException;


}
