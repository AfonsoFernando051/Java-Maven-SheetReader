package importable.service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import importable.model.ImportableSheet;
import importable.model.SheetData;
import importable.model.SheetLong;
import importable.model.SheetString;
import importable.model.SheetValor;

/**
 * @author Fernando Dias
 */
public class SheetFactory {

  /**
   * @param cell        - celula do contexto
   * @param titulo      - titulo da celula
   * @param numeroLinha - em que a celula esta
   * @return AbstractSheet
   */
  public static ImportableSheet<?> create(Cell cell, String titulo,
                                          int numeroLinha) {
    if (cell == null)
      return null;

    CellType tipo = cell.getCellType();
    String cellValue = cell.toString().trim();

    // Trata fórmulas conforme o tipo de resultado
    if (tipo == CellType.FORMULA) {
      tipo = cell.getCachedFormulaResultType();
    }

    if (tipo == CellType.NUMERIC) {
      double value = cell.getNumericCellValue();

      if (isDateCell(cell)) {
        Date dataExcel = DateUtil.getJavaDate(value);
        LocalDate date = dataExcel.toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate();
        return criarSheetData(date, cell, titulo, numeroLinha);
      } else {
        try {
          BigDecimal number = new BigDecimal(String.valueOf(value));
          
          // Verificar se é um valor inteiro (potencial Long)
          if (isIntegerValue(number)) {
            SheetLong sheetLong = new SheetLong();
            sheetLong.setValue(number.longValue());
            sheetLong.setLineNumber(numeroLinha);
            sheetLong.setColuna(getColunaLetra(cell.getColumnIndex()));
            sheetLong.setTituloColuna(titulo);
            return sheetLong;
          }
          
          if (cell.getCellStyle().getDataFormatString().contains("%")) {
            number = number.multiply(BigDecimal.valueOf(100));
          }
          SheetValor sheetValor = new SheetValor();
          sheetValor.setValue(number);
          sheetValor.setLineNumber(numeroLinha);
          sheetValor.setColuna(getColunaLetra(cell.getColumnIndex()));
          sheetValor.setTituloColuna(titulo);
          return sheetValor;
        } catch (Exception e) {
          e.getMessage();
        }
      }
    }
    
    if (tipo == CellType.STRING) {
      try {
        BigDecimal number = new BigDecimal(cellValue.replace(",", "."));
        
        // Verificar se é um valor inteiro (potencial Long)
        if (isIntegerValue(number)) {
          SheetLong sheetLong = new SheetLong();
          sheetLong.setValue(number.longValue());
          sheetLong.setLineNumber(numeroLinha);
          sheetLong.setColuna(getColunaLetra(cell.getColumnIndex()));
          sheetLong.setTituloColuna(titulo);
          return sheetLong;
        }
        
        SheetValor sheetValor = new SheetValor();
        sheetValor.setValue(number);
        sheetValor.setLineNumber(numeroLinha);
        sheetValor.setColuna(getColunaLetra(cell.getColumnIndex()));
        sheetValor.setTituloColuna(titulo);
        return sheetValor;
      } catch (NumberFormatException e) {
        SheetString sheetString = new SheetString();
        sheetString.setValue(cellValue);
        sheetString.setLineNumber(numeroLinha);
        sheetString.setColuna(getColunaLetra(cell.getColumnIndex()));
        sheetString.setTituloColuna(titulo);
        return sheetString;
      }
    }
    
    // Tratamento para células booleanas que podem representar 0/1 (Long)
    if (tipo == CellType.BOOLEAN) {
      try {
        Long longValue = cell.getBooleanCellValue() ? 1L : 0L;
        SheetLong sheetLong = new SheetLong();
        sheetLong.setValue(longValue);
        sheetLong.setLineNumber(numeroLinha);
        sheetLong.setColuna(getColunaLetra(cell.getColumnIndex()));
        sheetLong.setTituloColuna(titulo);
        return sheetLong;
      } catch (Exception e) {
        // Se falhar, retorna null
      }
    }
    
    // Tratamento para células em branco que podem ter valor padrão 0
    if (tipo == CellType.BLANK) {
      // Dependendo do contexto, você pode querer retornar 0 ou null
      // SheetLong sheetLong = new SheetLong();
      // sheetLong.setValue(0L);
      // sheetLong.setLineNumber(numeroLinha);
      // sheetLong.setColuna(getColunaLetra(cell.getColumnIndex()));
      // sheetLong.setTituloColuna(titulo);
      // return sheetLong;
    }
    
    return null;
  }

  /**
   * Verifica se o valor BigDecimal representa um número inteiro
   * @param number o valor BigDecimal a verificar
   * @return true se for um valor inteiro
   */
  private static boolean isIntegerValue(BigDecimal number) {
    try {
      // Verifica se o valor tem parte decimal zero e está dentro do range do Long
      if (number.scale() <= 0 || number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
        long longValue = number.longValueExact();
        // Verifica se o valor está dentro do range válido para IDs ou quantidades
        return longValue >= 0 && longValue <= Long.MAX_VALUE;
      }
    } catch (ArithmeticException e) {
      // Se não for um valor inteiro exato, não é Long
      return false;
    }
    return false;
  }

  /**
   * @param columnIndex -> index a pesquisar para coluna
   * @return index da coluna
   */
  public static String getColunaLetra(int columnIndex) {
    StringBuilder coluna = new StringBuilder();
    while (columnIndex >= 0) {
      coluna.insert(0, (char) ('A' + (columnIndex % 26)));
      columnIndex = (columnIndex / 26) - 1;
    }
    return coluna.toString();
  }

  /**
   * @param cell -> celula
   * @return true se for data
   */
  private static boolean isDateCell(Cell cell) {
    // 1. Verificação primária usando POI
    if (DateUtil.isCellDateFormatted(cell)) {
      return true;
    }

    double value = cell.getNumericCellValue();

    // 2. Verificação de faixas problemáticas
    if (value < 1 || value > 2958465) { // Fora do range de datas do Excel
      return false;
    }

    // 3. Verificação do estilo da célula
    CellStyle style = cell.getCellStyle();
    if (style != null) {
      String format = style.getDataFormatString();
      if (format != null) {
        return isExcelDateFormat(format.toLowerCase(), cell);
      }
    }

    // 4. Verificação de valor numérico como último recurso
    return looksLikeExcelDate(value);
  }

  /**
   * @param format -> formato
   * @param cell   -> celula
   * @return true se possui formato de data
   */
  private static boolean isExcelDateFormat(String format, Cell cell) {
    // Padrões mais específicos para formatos de data
    return format.contains("dd") || format.contains("mm")
        || format.contains("yyyy") || format.contains("hh")
        || format.matches("m/d/yy(?:yy)?");
  }

  /**
   * @param value -> valor
   * @return true se for data
   */
  private static boolean looksLikeExcelDate(double value) {
    // Verifica se é um número "redondo" (data sem horas)
    if (value == Math.floor(value)) {
      return true;
    }

    // Verifica a parte decimal (horas/minutos/segundos)
    double timePart = value - Math.floor(value);
    return timePart >= 0.000011574 && timePart <= 0.999988426; // 00:00:00 a
                                                               // 23:59:59
  }

  /**
   * @param data   - importada
   * @param cell   - celula do contexto
   * @param titulo - titulo da celula
   * @param linha  - da celula
   * @return AbstractSheet
   */
  private static SheetData criarSheetData(LocalDate data, Cell cell,
                                          String titulo, int linha) {
    SheetData sheetData = new SheetData();
    sheetData.setValue(data);
    sheetData.setLineNumber(linha);
    sheetData.setColuna(getColunaLetra(cell.getColumnIndex()));
    sheetData.setTituloColuna(titulo);
    return sheetData;
  }
}