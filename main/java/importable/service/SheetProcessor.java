package importable.service;

import java.io.IOException;
import java.util.List;

import org.apache.poi.ss.usermodel.Workbook;

import importable.config.SheetModel;

/**
 * Interface for processing a spreadsheet.
 *
 * @author Fernando Dias
 * @param <T> - The generic type
 */
public interface SheetProcessor<T> { // Renamed from PlanilhaProcessor

    /**
     * Processes the sheet rows and returns a list of values.
     *
     * @param workbook   The import workbook.
     * @param sheetModel The sheet configuration to be processed. // Renamed from planilha
     * @return A list of imported values.
     * @throws IOException If a stream exception occurs.
     */
    List<T> process(Workbook workbook, SheetModel sheetModel) // Renamed from processar
            throws IOException;

}