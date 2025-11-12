package importable.model.sheet;

/**
 * Abstract base class for an importable sheet cell.
 *
 * @author Fernando Dias
 * @param <T> Kind of value
 */
public abstract class AbstractSheet<T>
        implements ImportableSheet<T> {

    /**
     * Value from the sheet cell.
     */
    private T value;

    /**
     * Row number of the cell.
     */
    private int lineNumber;

    /**
     * The column identifier (e.g., "A", "B").
     */
    private String column;

    /**
     * The title/header of the column.
     */
    private String columnTitle;

    /**
     * @return {@link #value}
     */
    @Override
    public T getValue() {
        return value;
    }

    /**
     * @param value updates {@link #value}.
     */
    @Override
    public void setValue(T value) {
        this.value = value;
    }

    /**
     * @return {@link #lineNumber}
     */
    public int getLineNumber() {
        return lineNumber;
    }

    /**
     * @param lineNumber updates {@link #lineNumber}.
     */
    public void setLineNumber(int lineNumber) {
        this.lineNumber = lineNumber;
    }

    /**
     * @return {@link #column}
     */
    public String getColumn() {
        return column;
    }

    /**
     * @param column updates {@link #column}.
     */
    public void setColumn(String column) {
        this.column = column;
    }

    /**
     * @return {@link #columnTitle}
     */
    public String getColumnTitle() {
        return columnTitle;
    }

    /**
     * @param columnTitle updates {@link #columnTitle}.
     */
    public void setColumnTitle(String columnTitle) {
        this.columnTitle = columnTitle;
    }

    @Override
    public String toString() {
        return "AbstractSheet [value=" + value + ", lineNumber=" + lineNumber
                + ", column=" + column + ", columnTitle=" + columnTitle + "]";
    }
}