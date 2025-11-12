package importable.model.row;

import java.util.ArrayList;
import java.util.List; // Added List interface
import importable.model.sheet.DetailedSheet;
import importable.model.sheet.ImportableSheet;

/**
 * Represents the abstraction of a spreadsheet row.
 * <p>
 * This class groups a list of cells, allowing the developer to
 * handle them, abstract information, and build import models.
 *
 * @author Fernando Dias
 */
public class RowData {

    /**
     * The row number (1-based).
     */
    private int rowNumber;

    /**
     * The list of cells in this row.
     */
    List<DetailedSheet> cells = new ArrayList<>(); // Use List interface

    /**
     * Gets the row number in the sheet (1-based).
     *
     * @return the row number
     */
    public int getRowNumber() {
        return rowNumber;
    }

    /**
     * Sets the row number.
     *
     * @param rowNumber the number that identifies the row
     */
    public void setRowNumber(int rowNumber) {
        this.rowNumber = rowNumber;
    }

    /**
     * Adds a cell to the row's cell list.
     *
     * @param cell The {@link DetailedSheet} to be added.
     */
    public void addCell(DetailedSheet cell) {
        this.cells.add(cell);
    }

    /**
     * @return {@link #cells}
     */
    public List<DetailedSheet> getCells() {
        return cells;
    }

    /**
     * @param cells updates {@link #cells}.
     */
    public void setCells(List<DetailedSheet> cells) {
        this.cells = cells;
    }

    /**
     * Gets the cell's data by its identifier.
     * <p>
     * (Note: Assumes {@link DetailedSheet#getIdentificador()} will be refactored to {@code getIdentifier()})
     *
     * @param identifier The identifier of the cell
     * @return the {@link ImportableSheet} data for the cell, or null if not found.
     */
    public ImportableSheet<?> getCellByIdentifier(String identifier) {
        for (DetailedSheet cellData : cells) {
            // Assuming getIdentificador() will be refactored to getIdentifier()
            if (cellData.getIdentifier().equals(identifier)) {
                return cellData.getSheetData();
            }
        }
        return null;
    }

    /**
     * Checks if a cell with the specified identifier exists in the row.
     * <p>
     * (Note: Assumes {@link DetailedSheet#getIdentificador()} will be refactored to {@code getIdentifier()})
     *
     * @param identifier The column identifier to check for.
     * @return true if the row contains a cell with the identifier, false otherwise.
     */
    public boolean containsIdentifier(String identifier) {
        if (identifier == null || cells == null || cells.isEmpty()) {
            return false;
        }

        for (DetailedSheet cell : cells) {
            // Assuming getIdentificador() will be refactored to getIdentifier()
            if (identifier.equals(cell.getIdentifier())) {
                return true;
            }
        }
        return false;
    }
}