package importable.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import importable.model.sheet.SheetData;
import importable.model.sheet.SheetValor;

/**
 * @author Fernando Dias
 */
public class SheetDataUtils {
  /**
   * @param data do evento
   * @return SheetData
   */
  public static SheetData generateSheetData(LocalDate data) {
    SheetData celula = new SheetData();
    celula.setValue(data);
    celula.setLineNumber(0);
    celula.setColuna("");
    celula.setTituloColuna("");
    return celula;
  }

  /**
   * @param valor da celula
   * @return SheetData
   */
  public static SheetValor generateSheetValor(BigDecimal valor) {
    SheetValor celula = new SheetValor();
    celula.setValue(valor);
    celula.setLineNumber(0);
    celula.setColuna("");
    celula.setTituloColuna("");
    return celula;
  }
  
  /**
   * Converts Excel serial date to LocalDate
   * Excel dates are represented as number of days since January 1, 1900
   * 
   * @param excelSerialDate the Excel serial date value
   * @return LocalDate representation
   */
  public static LocalDate excelSerialToLocalDate(Double excelSerialDate) {
      if (excelSerialDate == null) {
          return null;
      }
      
      // Excel base date is January 1, 1900
      // Adjust for Excel's date system (1900 is incorrectly treated as leap year)
      long daysSinceEpoch = excelSerialDate.longValue() - 2;
      
      // Excel date 1 = January 1, 1900
      LocalDate baseDate = LocalDate.of(1900, 1, 1);
      return baseDate.plusDays(daysSinceEpoch);
  }
  
  /**
   * Alternative method that handles the Excel 1900 leap year bug more accurately
   */
  public static LocalDate excelSerialToLocalDateAccurate(Double excelSerialDate) {
      if (excelSerialDate == null) {
          return null;
      }
      
      // More accurate conversion considering Excel's 1900 leap year bug
      // Excel incorrectly treats 1900 as a leap year
      double serial = excelSerialDate;
      
      // Adjust for Excel's bug (Feb 29, 1900 doesn't exist but Excel thinks it does)
      if (serial >= 61) {
          serial -= 1;
      }
      
      LocalDate baseDate = LocalDate.of(1899, 12, 30);
      return baseDate.plusDays((long) serial);
  }
}
