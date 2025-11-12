package importable.service;

import java.math.BigDecimal;
import java.time.LocalDate;

import importable.model.sheet.SheetDate; 
import importable.model.sheet.SheetBigDecimal; 

/**
 * Utility class for creating sheet data objects.
 *
 * @author Fernando Dias
 */
public class SheetDataUtils {
    /**
     * @param date the event date
     * @return SheetDate
     */
    public static SheetDate generateSheetDate(LocalDate date) {
        SheetDate cellData = new SheetDate(); // Renamed from celula
        cellData.setValue(date);
        cellData.setLineNumber(0);
        cellData.setColumn(""); // Renamed from setColuna
        cellData.setColumnTitle(""); // Renamed from setTituloColuna
        return cellData;
    }

    /**
     * @param value the cell value
     * @return SheetBigDecimal
     */
    public static SheetBigDecimal generateSheetBigDecimal(BigDecimal value) {
        SheetBigDecimal cellData = new SheetBigDecimal(); // Renamed from celula
        cellData.setValue(value);
        cellData.setLineNumber(0);
        cellData.setColumn(""); // Renamed from setColuna
        cellData.setColumnTitle(""); // Renamed from setTituloColuna
        return cellData;
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