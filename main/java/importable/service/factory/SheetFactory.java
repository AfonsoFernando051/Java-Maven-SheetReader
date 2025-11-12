package importable.service.factory;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;

import importable.model.sheet.ImportableSheet;
// Assuming SheetData was renamed to SheetDate based on context
import importable.model.sheet.SheetDate; 
import importable.model.sheet.SheetLong;
import importable.model.sheet.SheetString;
// Assuming SheetValor was renamed to SheetValue or SheetBigDecimal
import importable.model.sheet.SheetBigDecimal;

/**
 * Factory for creating different types of {@link ImportableSheet} objects 
 * based on cell content.
 *
 * @author Fernando Dias
 */
public class SheetFactory {

  /**
   * Creates an ImportableSheet based on the cell's type and content.
   *
   * @param cell        - the cell from the context
   * @param title       - the title of the cell (column header)
   * @param lineNumber  - the line number where the cell is located
   * @return An {@link ImportableSheet} implementation, or null if the cell is invalid.
   */
  public static ImportableSheet<?> create(Cell cell, String title,
                                          int lineNumber) {
    if (cell == null)
      return null;

    CellType type = cell.getCellType();
    String cellValue = cell.toString().trim();

    // Handle formulas based on their cached result type
    if (type == CellType.FORMULA) {
      type = cell.getCachedFormulaResultType();
    }

    if (type == CellType.NUMERIC) {
      double value = cell.getNumericCellValue();

      if (isDateCell(cell)) {
        Date excelDate = DateUtil.getJavaDate(value);
        LocalDate date = excelDate.toInstant()
            .atZone(ZoneId.systemDefault()).toLocalDate();
        return createSheetDate(date, cell, title, lineNumber);
      } else {
        try {
          BigDecimal number = new BigDecimal(String.valueOf(value));
          
          // Check if it's an integer value (potential Long)
          if (isIntegerValue(number)) {
            SheetLong sheetLong = new SheetLong();
            sheetLong.setValue(number.longValue());
            sheetLong.setLineNumber(lineNumber);
            sheetLong.setColumn(getColumnLetter(cell.getColumnIndex()));
            sheetLong.setColumnTitle(title);
            return sheetLong;
          }
          
          // Handle percentages
          if (cell.getCellStyle().getDataFormatString().contains("%")) {
            number = number.multiply(BigDecimal.valueOf(100));
          }
          SheetBigDecimal sheetBigDecimal = new SheetBigDecimal();
          sheetBigDecimal.setValue(number);
          sheetBigDecimal.setLineNumber(lineNumber);
          sheetBigDecimal.setColumn(getColumnLetter(cell.getColumnIndex()));
          sheetBigDecimal.setColumnTitle(title);
          return sheetBigDecimal;
        } catch (Exception e) {
          e.getMessage(); // Consider logging this
        }
      }
    }
    
    if (type == CellType.STRING) {
      try {
        // Try to parse as a number first (e.g., "1,234.56" or "1.234,56")
        // Standardizing to use "." as decimal separator
        BigDecimal number = new BigDecimal(cellValue.replace(",", "."));
        
        // Check if it's an integer value (potential Long)
        if (isIntegerValue(number)) {
          SheetLong sheetLong = new SheetLong();
          sheetLong.setValue(number.longValue());
          sheetLong.setLineNumber(lineNumber);
          sheetLong.setColumn(getColumnLetter(cell.getColumnIndex()));
          sheetLong.setColumnTitle(title);
          return sheetLong;
        }
        
        SheetBigDecimal sheetBigDecimal = new SheetBigDecimal();
        sheetBigDecimal.setValue(number);
        sheetBigDecimal.setLineNumber(lineNumber);
        sheetBigDecimal.setColumn(getColumnLetter(cell.getColumnIndex()));
        sheetBigDecimal.setColumnTitle(title);
        return sheetBigDecimal;
      } catch (NumberFormatException e) {
        // If it fails to parse as a number, treat it as a String
        SheetString sheetString = new SheetString();
        sheetString.setValue(cellValue);
        sheetString.setLineNumber(lineNumber);
        sheetString.setColumn(getColumnLetter(cell.getColumnIndex()));
        sheetString.setColumnTitle(title);
        return sheetString;
      }
    }
    
    // Handling for boolean cells that might represent 0/1 (Long)
    if (type == CellType.BOOLEAN) {
      try {
        Long longValue = cell.getBooleanCellValue() ? 1L : 0L;
        SheetLong sheetLong = new SheetLong();
        sheetLong.setValue(longValue);
        sheetLong.setLineNumber(lineNumber);
        sheetLong.setColumn(getColumnLetter(cell.getColumnIndex()));
        sheetLong.setColumnTitle(title);
        return sheetLong;
      } catch (Exception e) {
        // If it fails, return null
      }
    }
    
    // Handling for blank cells
    if (type == CellType.BLANK) {
      // Depending on the context, you might want to return 0 or null
      // Example for returning 0L:
      // SheetLong sheetLong = new SheetLong();
      // sheetLong.setValue(0L);
      // sheetLong.setLineNumber(lineNumber);
      // sheetLong.setColumn(getColumnLetter(cell.getColumnIndex()));
      // sheetLong.setColumnTitle(title);
      // return sheetLong;
    }
    
    return null;
  }

  /**
   * Checks if the BigDecimal value represents an integer.
   * @param number the BigDecimal value to check
   * @return true if it is an integer value
   */
  private static boolean isIntegerValue(BigDecimal number) {
    try {
      // Checks if the value has a zero fractional part and is within the Long range
      if (number.scale() <= 0 || number.remainder(BigDecimal.ONE).compareTo(BigDecimal.ZERO) == 0) {
        long longValue = number.longValueExact();
        // Checks if the value is within the valid range for IDs or quantities
        return longValue >= 0 && longValue <= Long.MAX_VALUE;
      }
    } catch (ArithmeticException e) {
      // If it's not an exact integer value, it's not a Long
      return false;
    }
    return false;
  }

  /**
   * Converts a 0-based column index to its Excel letter representation.
   *
   * @param columnIndex -> index to search for the column (e.g., 0)
   * @return column letter (e.g., "A")
   */
  public static String getColumnLetter(int columnIndex) {
    StringBuilder columnName = new StringBuilder();
    while (columnIndex >= 0) {
      columnName.insert(0, (char) ('A' + (columnIndex % 26)));
      columnIndex = (columnIndex / 26) - 1;
    }
    return columnName.toString();
  }

  /**
   * @param cell -> the cell
   * @return true if it is a date cell
   */
  private static boolean isDateCell(Cell cell) {
    // 1. Primary check using POI
    if (DateUtil.isCellDateFormatted(cell)) {
      return true;
    }

    double value = cell.getNumericCellValue();

    // 2. Check for problematic ranges
    if (value < 1 || value > 2958465) { // Outside of Excel's date range
      return false;
    }

    // 3. Check the cell style
    CellStyle style = cell.getCellStyle();
    if (style != null) {
      String format = style.getDataFormatString();
      if (format != null) {
        return isExcelDateFormat(format.toLowerCase(), cell);
      }
    }

    // 4. Numeric value check as a last resort
    return looksLikeExcelDate(value);
  }

  /**
   * @param format -> the format string
   * @param cell   -> the cell
   * @return true if it has a date format
   */
  private static boolean isExcelDateFormat(String format, Cell cell) {
    // More specific patterns for date formats
    return format.contains("dd") || format.contains("mm")
        || format.contains("yyyy") || format.contains("hh")
        || format.matches("m/d/yy(?:yy)?");
  }

  /**
   * @param value -> the numeric value
   * @return true if it looks like a date
   */
  private static boolean looksLikeExcelDate(double value) {
    // Checks if it's a "round" number (date without time)
    if (value == Math.floor(value)) {
      return true;
    }

    // Checks the decimal part (hours/minutes/seconds)
    double timePart = value - Math.floor(value);
    return timePart >= 0.000011574 && timePart <= 0.999988426; // 00:00:00 to 23:59:59
  }

  /**
   * Helper factory method to create a SheetDate object.
   *
   * @param date      - imported date
   * @param cell      - cell from the context
   * @param title     - title of the cell
   * @param row       - row of the cell
   * @return A populated {@link SheetDate} object
   */
  private static SheetDate createSheetDate(LocalDate date, Cell cell,
                                           String title, int row) {
    SheetDate sheetDate = new SheetDate();
    sheetDate.setValue(date);
    sheetDate.setLineNumber(row);
    sheetDate.setColumn(getColumnLetter(cell.getColumnIndex()));
    sheetDate.setColumnTitle(title);
    return sheetDate;
  }
}