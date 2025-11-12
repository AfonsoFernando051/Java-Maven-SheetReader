package importable.config;

import java.util.HashMap;
import java.util.Map;

/**
 * @author Fernando Dias
 */
public class SheetModel {

    /**
     * The identifier name of the sheet.
     */
    String sheetName;

    /**
     * A map linking column names (keys) to their values.
     */
    Map<String, String> columnValueMap = new HashMap<>();

    /**
     * A map linking row identifiers (keys) to their values.
     */
    Map<String, String> rowValueMap = new HashMap<>();

    /**
     * A map for configuration settings (keys) and their values.
     */
    Map<String, String> configurationMap = new HashMap<>();

    /**
     * Logical component type related to the import.
     * (Assuming TipeSheetEnum was a typo for SheetTypeEnum)
     */
    private SheetTypeEnum logicalType;

    /**
     * The row number of the header.
     */
    private int headerRow;

    /**
     * @return {@link #sheetName}
     */
    public String getSheetName() {
        return sheetName;
    }

    /**
     * @param sheetName updates {@link #sheetName}.
     */
    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }

    /**
     * @return {@link #columnValueMap}
     */
    public Map<String, String> getColumnValueMap() {
        return columnValueMap;
    }

    /**
     * @param columnValueMap updates {@link #columnValueMap}.
     */
    public void setColumnValueMap(Map<String, String> columnValueMap) {
        this.columnValueMap = columnValueMap;
    }

    /**
     * Adds a new column and its value to the {@link #columnValueMap}.
     *
     * @param column - the column name (key)
     * @param value  - the value associated with the column
     */
    public void addColumnValue(String column, String value) {
        if (this.columnValueMap == null) {
            this.columnValueMap = new HashMap<>();
        }
        this.columnValueMap.put(column, value);
    }

    /**
     * Adds a new entry to the {@link #rowValueMap}.
     *
     * @param key   - the key (e.g., row identifier)
     * @param value - the value associated with the key
     */
    public void addRowValue(String key, String value) {
        if (this.rowValueMap == null) {
            this.rowValueMap = new HashMap<>();
        }
        this.rowValueMap.put(key, value);
    }

    /**
     * Adds a new configuration setting to the
     * {@link #configurationMap}.
     *
     * @param key   - the configuration name (key)
     * @param value - the value associated with the key
     */
    public void addConfiguration(String key, String value) {
        if (this.configurationMap == null) {
            this.configurationMap = new HashMap<>();
        }
        this.configurationMap.put(key, value);

    }

    /**
     * @return {@link #logicalType}
     */
    public SheetTypeEnum getLogicalType() {
        return logicalType;
    }

    /**
     * @param logicalType updates {@link #logicalType}.
     */
    public void setLogicalType(SheetTypeEnum logicalType) {
        this.logicalType = logicalType;
    }

    /**
     * @return {@link #rowValueMap}
     */
    public Map<String, String> getRowValueMap() {
        return rowValueMap;
    }

    /**
     * @param rowValueMap updates {@link #rowValueMap}.
     */
    public void setRowValueMap(Map<String, String> rowValueMap) {
        this.rowValueMap = rowValueMap;
    }

    /**
     * @return {@link #headerRow}
     */
    public int getHeaderRow() {
        return headerRow;
    }

    /**
     * @param headerRow updates {@link #headerRow}.
     */
    public void setHeaderRow(int headerRow) {
        this.headerRow = headerRow;
    }

    /**
     * @return {@link #configurationMap}
     */
    public Map<String, String> getConfigurationMap() {
        return configurationMap;
    }
}