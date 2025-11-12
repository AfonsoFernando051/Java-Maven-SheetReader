package importable.service;

import java.io.IOException;
import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.DateUtil;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;

import importable.config.SheetModel;
import importable.mapper.InterfaceSheetMapper;
import importable.model.row.RowData;
import importable.model.sheet.DetailedSheet;
import importable.model.sheet.ImportableSheet;
import importable.service.factory.SheetFactory;
import importable.translator.Translator;
import importable.utils.FileProcessingException;

/**
 * Class created to provide generic processing for spreadsheets
 * using a configuration model generated from an import form.
 *
 * @author Fernando Dias
 * @param <T> -> Generic type, the child class must implement it.
 */
public class GenericSheetProcessor<T> implements SheetProcessor<T> {

    /**
     * Import configuration model.
     * (Assuming InterfacePlanilhaMapper will be renamed to InterfaceSheetMapper)
     */
    private final InterfaceSheetMapper<T> modelConfig;

    /**
     * Map containing column titles (headers) by their index.
     */
    private Map<Integer, String> headerTitlesByIndex = new HashMap<>();

    /**
     * @param modelConfig - import configuration model
     */
    public GenericSheetProcessor(InterfaceSheetMapper<T> modelConfig) {
        this.modelConfig = modelConfig;
    }

    public ArrayList<T> process(Workbook workbook, SheetModel sheetConfig)
            throws IOException {
        ArrayList<T> results = new ArrayList<>();
        int sheetIndex = Character
                .getNumericValue(sheetConfig.getSheetName().charAt(0));
        Sheet sheet = workbook.getSheetAt(sheetIndex);

        for (Row row : sheet) {
            if (isInvalidImportRow(row, sheetConfig)) {
                continue;
            }

            results.addAll(processRow(row, sheetConfig));
        }

        return results;
    }

    /**
     * @param row         -> the row
     * @param sheetConfig -> from the context
     * @return list processed from the row
     */
    private ArrayList<T> processRow(Row row, SheetModel sheetConfig) {
        ArrayList<T> rowResults = new ArrayList<>();
        try {
            // Process data rows
            RowData rowData = new RowData();
            for (Map.Entry<String, String> entry : sheetConfig.getColumnValueMap()
                    .entrySet()) {

                String columnKey = entry.getKey();
                int columnIndex = columnToIndex(sheetConfig, columnKey);
                Cell cell = row.getCell(columnIndex);
                String headerTitle = headerTitlesByIndex.get(columnIndex);

                if (cell != null && !isCellInvalid(cell)
                        && doesCellMatchIndex(cell, columnIndex)) {
                    ImportableSheet<?> sheet = SheetFactory
                            .create(cell, headerTitle, row.getRowNum() + 1);
                    DetailedSheet detailedCell = new DetailedSheet(columnKey, sheet);
                    rowData.setRowNumber(row.getRowNum() + 1);
                    rowData.addCell(detailedCell);
                }
            }
            if (!rowData.getCells().isEmpty()) {
                rowResults = createModelsByRows(rowData);
            }
        } catch (Exception e) {
            e.getMessage(); // Consider logging this exception
        }
        return rowResults;
    }

    /**
     * Converts a cell's value to String, handling all possible types.
     *
     * @param cell - Cell to be converted
     * @return String with the formatted cell value
     * @throws IllegalStateException if the cell type is not supported
     */
    private String cellToString(Cell cell) {
        if (cell == null) {
            return "";
        }

        switch (cell.getCellType()) {
            case STRING:
                return cell.getStringCellValue().trim();

            case NUMERIC:
                if (DateUtil.isCellDateFormatted(cell)) {
                    return cell.getLocalDateTimeCellValue().toString();
                }
                // Removes .0 from integers
                double num = cell.getNumericCellValue();
                if (num == (long) num) {
                    return String.valueOf((long) num);
                }
                return String.valueOf(num);

            case BOOLEAN:
                return String.valueOf(cell.getBooleanCellValue());

            case FORMULA:
                switch (cell.getCachedFormulaResultType()) {
                    case STRING:
                        return cell.getStringCellValue().trim();
                    case NUMERIC:
                        return String.valueOf(cell.getNumericCellValue());
                    case BOOLEAN:
                        return String.valueOf(cell.getBooleanCellValue());
                    default:
                        return cell.getCellFormula();
                }

            case BLANK:
                return "";

            default:
                // Translated exception
                throw new IllegalStateException("Unsupported cell type: "
                        + cell.getCellType());
        }
    }

    /**
     * Checks if the cell's column index matches the expected index.
     *
     * @param cell          - Cell to be validated
     * @param expectedIndex - The expected column index (e.g., 0 for 'A')
     * @return true if the cell's index matches the expectedIndex, false otherwise
     */
    private boolean doesCellMatchIndex(Cell cell, int expectedIndex) {
        if (cell == null) {
            return false;
        }
        return cell.getColumnIndex() == expectedIndex;
    }

    /**
     * @param cell -> cell to validate
     * @return true -> if the cell is null or blank
     */
    private boolean isCellInvalid(Cell cell) {
        return cell == null || cell.getCellType() == CellType.BLANK;
    }

    /**
     * @param rowData row being processed
     * @return models created from the row
     */
    @SuppressWarnings("unchecked")
    protected ArrayList<T> createModelsByRows(RowData rowData) {
        T model = (T) modelConfig.createModelsByRow().apply(rowData);
        if (model != null) {
            // This cast is potentially unsafe.
            // The factory method createModelsByRow should ideally return List<T>
            return (ArrayList<T>) model;
        }
        return new ArrayList<>();
    }

    /**
     * @param row         -> Row to check
     * @param sheetConfig -> Current sheet configuration
     * @return true if the row is invalid for import
     */
    public boolean isInvalidImportRow(Row row, SheetModel sheetConfig) {
        if (row == null) {
            return true;
        }
        int rowNum = row.getRowNum() + 1;
        int startPosition = getRowIndex(sheetConfig, Translator.LINE_START);

        if (!sheetConfig.getRowValueMap()
                .containsKey(Translator.HEADER)) {
            boolean isHeaderRow = row.getRowNum() == 0;
            if (isHeaderRow) {
                sheetConfig.addRowValue(Translator.HEADER, "0");
                sheetConfig.setHeaderRow(0);
                populateHeaderMap(row, sheetConfig);
                return true;
            }
        } else {
            int headerPosition = getRowIndex(sheetConfig, Translator.HEADER);
            sheetConfig.setHeaderRow(headerPosition);
            boolean isHeaderRow = row.getRowNum() + 1 == headerPosition;

            if (isHeaderRow) {
                populateHeaderMap(row, sheetConfig);
                return true;
            }
            if (rowNum < headerPosition) {
                return true;
            }
        }

        if (sheetConfig.getRowValueMap().containsKey(Translator.END_LINE)) {
            int endPosition = getRowIndex(sheetConfig, Translator.END_LINE);
            return rowNum < startPosition || rowNum > endPosition;
        }

        return rowNum < startPosition;
    }

    /**
     * Populates the header map based on the header row.
     *
     * @param row         -> the header row
     * @param sheetConfig -> the context configuration
     */
    protected void populateHeaderMap(Row row, SheetModel sheetConfig) {
        if (sheetConfig.getHeaderRow() == row.getRowNum()) {
            for (Map.Entry<String, String> entry : sheetConfig.getColumnValueMap()
                    .entrySet()) {
                if (entry.getValue() == null || (entry.getValue() != null
                        && entry.getValue().matches("\\d+"))) {
                    continue;
                }
                String columnKey = entry.getKey();
                int columnIndex = columnToIndex(sheetConfig, columnKey);
                Cell cell = row.getCell(columnIndex);

                if (cell != null && !isCellInvalid(cell)) {
                    headerTitlesByIndex.put(columnIndex, cellToString(cell));
                }
            }
        }
    }

    /**
     * Returns the column index, handling numeric or Excel-style letter values.
     *
     * @param sheetConfig the sheet with column mappings
     * @param columnKey   the column key (e.g., "A", "NAME")
     * @return column index as an integer
     */
    public int columnToIndex(SheetModel sheetConfig, String columnKey) {
        String value = sheetConfig.getColumnValueMap().get(columnKey);
        if (value != null) {
            value = value.trim();
            // Check if it's numeric
            if (value.matches("\\d+")) {
                return Integer.parseInt(value);
            }
            // Check if it's alphabetic (Excel-style: A, AB, ZY...)
            if (value.matches("[A-Za-z]+")) {
                return excelColumnToIndex(value.toUpperCase());
            }
        }
        return 0;
    }

    /**
     * Returns the row index, handling numeric values.
     *
     * @param sheetConfig the sheet with row mappings
     * @param rowKey      the row key (e.g., "HEADER", "START_LINE")
     * @return row index as an integer
     */
    public int getRowIndex(SheetModel sheetConfig, String rowKey) {
        String value = sheetConfig.getRowValueMap().get(rowKey);

        try {
            if (value != null) {
                value = value.trim();
                if (value.matches("\\d+")) {
                    return Integer.parseInt(value);
                }
            }
        } catch (Exception e) {
            throw new FileProcessingException("Error processing row index");
        }
        return 0;
    }

    /**
     * Converts an Excel-style column (e.g., A, Z, AA, AB...) to a 0-based index.
     *
     * @param columnName (e.g., "A", "AB")
     * @return corresponding index (e.g., "A" = 0, "Z" = 25, "AA" = 26)
     */
    private int excelColumnToIndex(String columnName) {
        int result = 0;
        for (int i = 0; i < columnName.length(); i++) {
            char c = columnName.charAt(i);
            result = result * 26 + (c - 'A' + 1);
        }
        return result - 1; // Adjust to 0-based index
    }

    /**
     * Validates and converts a cell to LocalDate
     *
     * @param cell -> Cell from the context
     * @return converted date
     */
    protected LocalDate validateAndConvertDate(Cell cell) {
        validateCellNotNull(cell, "DATE");

        if (cell.getCellType() != CellType.NUMERIC
                || !DateUtil.isCellDateFormatted(cell)) {
            throw new IllegalArgumentException(formatErrorMessage(cell,
                    "must be a formatted date"));
        }

        LocalDateTime dateTime = cell.getLocalDateTimeCellValue();
        if (dateTime == null) {
            throw new IllegalArgumentException(formatErrorMessage(cell,
                    "contains an invalid date"));
        }

        LocalDate date = dateTime.toLocalDate();
        if (date.isAfter(LocalDate.now())) {
            throw new IllegalArgumentException(formatErrorMessage(cell,
                    "cannot be a future date"));
        }

        return date;
    }

    /**
     * Validates and converts a cell to BigDecimal
     *
     * @param cell -> cell
     * @param type -> of sheet/data (e.g., "Value", "Amount")
     * @param min  Minimum allowed value (inclusive)
     * @param max  Maximum allowed value (inclusive, null for no limit)
     * @return converted value
     */
    protected BigDecimal validateAndConvertValue(Cell cell, String type,
                                                 double min, Double max) {
        validateCellNotNull(cell, type);

        if (cell.getCellType() != CellType.NUMERIC) {
            throw new IllegalArgumentException(formatErrorMessage(cell,
                    "must contain a numeric value"));
        }

        double value = cell.getNumericCellValue();

        if (value < min) {
            throw new IllegalArgumentException(formatErrorMessage(cell, String
                    .format("cannot be less than %.2f", min)));
        }

        if (max != null && value > max) {
            throw new IllegalArgumentException(formatErrorMessage(cell, String
                    .format("cannot exceed %.2f", max)));
        }

        return BigDecimal.valueOf(value);
    }

    /**
     * Validates that a cell is not null
     *
     * @param cell -> cell
     * @param type -> of sheet/data (e.g., "Date", "Value")
     */
    protected void validateCellNotNull(Cell cell, String type) {
        if (cell == null) {
            throw new IllegalArgumentException(String
                    .format("%s cell cannot be null", type));
        }
    }

    /**
     * Formats an error message with the column reference
     *
     * @param cell    -> cell
     * @param message -> error message
     * @return formatted message
     */
    protected String formatErrorMessage(Cell cell, String message) {
        return String.format("Error in column %s: %s",
                getColumnLetter(cell.getColumnIndex()), message);
    }

    /**
     * @param columnIndex -> index to search for the column
     * @return column letter (e.g., 0 -> "A")
     */
    public static String getColumnLetter(int columnIndex) {
        StringBuilder columnName = new StringBuilder();
        while (columnIndex >= 0) {
            columnName.insert(0, (char) ('A' + (columnIndex % 26)));
            columnIndex = (columnIndex / 26) - 1;
        }
        return columnName.toString();
    }
}