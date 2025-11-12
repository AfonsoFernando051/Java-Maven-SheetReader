package importable.model.sheet;

/**
 * A container that stores a spreadsheet cell along with its associated metadata.
 * <p>
 * This is used during sheet processing to maintain the complete context
 * of each cell, including its identifier and the cell data itself.
 *
 * @author Fernando Dias
 */
public class DetailedSheet {

    /**
     * The cell's identifier (e.g., column name or key).
     */
    private final String identifier;

    /**
     * The actual cell data.
     */
    private ImportableSheet<?> sheetData;

    /**
     * Constructs a new cell container with all its metadata.
     *
     * @param identifier identifies the cell using a map key/value
     * @param sheetData  the spreadsheet cell data (cannot be null)
     * @throws IllegalArgumentException if sheetData is null
     */
    public DetailedSheet(String identifier, ImportableSheet<?> sheetData) {
        if (sheetData == null) {
            // Translated error message
            throw new IllegalArgumentException("Sheet data cannot be null");
        }
        this.identifier = identifier;
        this.setSheetData(sheetData);
    }

    /**
     * @return {@link #identifier}
     */
    public String getIdentifier() {
        return identifier;
    }

    /**
     * @return {@link #sheetData}
     */
    public ImportableSheet<?> getSheetData() {
        return sheetData;
    }

    /**
     * @param sheetData updates {@link #sheetData}.
     */
    public void setSheetData(ImportableSheet<?> sheetData) {
        this.sheetData = sheetData;
    }

    @Override
    public String toString() {
        String id = identifier != null ? identifier : "";
        String value = (sheetData != null
                && sheetData.getValue() != null) ? sheetData.getValue().toString()
                : "";
        return id + " - " + value;
    }
}