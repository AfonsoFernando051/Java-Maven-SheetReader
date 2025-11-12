package importable.mapper;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.function.Function;

import importable.model.row.RowData;
import importable.model.sheet.AbstractSheet;
import importable.model.sheet.ImportableSheet;
import importable.model.sheet.SheetBigDecimal; // Assuming SheetValor is SheetBigDecimal
import importable.model.sheet.SheetDate;
import importable.model.sheet.SheetString;
import importable.translator.Translator;
import importable.utils.FileProcessingException; // Renamed exception

/**
 * @author Fernando Dias
 * @param <T> -> Type to be changed in subclasses
 */
public abstract class GenericImportMapper<T>
        implements ImportModelValidator<T>, InterfaceSheetMapper<T> { // Renamed interface

    /**
     * The generic type.
     */
    private final Class<T> type;

    /**
     * @param type the type to be handled
     */
    public GenericImportMapper(Class<T> type) {
        this.type = type;
    }

    /**
     * @return a new instance of T
     */
    public T createInstance() { // Renamed from createInstance
        try {
            return type.getDeclaredConstructor().newInstance();
        } catch (Exception e) {
            throw new RuntimeException("Could not instantiate "
                    + type.getName(), e);
        }
    }

    @Override
    public Function<RowData, ArrayList<T>> createModelsByRow() {
        return row -> {
            try {
                ArrayList<T> models = processRow().apply(row); // Renamed from modelos
                for (T model : models) { // Renamed from modelo
                    validate(model, row);
                }

                return models;

            } catch (FileProcessingException e) { // Renamed exception
                String message = String // Renamed from mensagem
                        .format("Processing error on line {}: {}",
                                row.getRowNumber(), e.getMessage());
                throw new FileProcessingException(message, e); // Renamed exception
            } catch (ClassCastException e) {
                String message = String
                        .format("Unexpected type error processing line %d: %s",
                                row.getRowNumber(), e.getMessage());
                throw new FileProcessingException(message, e); // Renamed exception
            } catch (Exception e) {
                String message = String
                        .format("Unexpected error processing line %d: %s",
                                row.getRowNumber(), e.getMessage());
                throw new FileProcessingException(message, e); // Renamed exception
            }
        };
    }

    /**
     * @return implementation for row reading and import
     */
    protected abstract Function<RowData, ArrayList<T>> processRow();

    /**
     * Performs a safe cast of ImportableSheet, throwing a clear exception if the
     * type is not as expected.
     *
     * @param <T>           The expected type
     * @param cell          The cell to be cast
     * @param expectedClass The expected class
     * @return The cast object
     * @throws FileProcessingException if the type is not as expected
     * @author Fernando Dias
     */
    @SuppressWarnings({ "unchecked", "hiding" })
    protected <T extends ImportableSheet<?>> T safeCast(ImportableSheet<?> cell,
            Class<T> expectedClass) {
        if (expectedClass.isInstance(cell)) {
            return (T) cell;
        }

        AbstractSheet<?> abstractSheet = (AbstractSheet<?>) cell;
        // Assuming AbstractSheet has getColumn() and getColumnTitle()
        throw new FileProcessingException(String
                .format("Type error in sheet: expected %s, found %s on line %d, column %s, title %s.",
                        expectedClass.getSimpleName(),
                        cell.getClass().getSimpleName(),
                        abstractSheet.getLineNumber(), abstractSheet.getColumn(),
                        abstractSheet.getColumnTitle()));
    }

    /**
     * Converts any ImportableSheet to String.
     *
     * @param sheet ImportableSheet (SheetBigDecimal or SheetString)
     * @return value as String, or null if empty
     */
    public String sheetToString(ImportableSheet<?> sheet) {

        if (sheet instanceof SheetBigDecimal) { // Renamed from SheetValor
            return safeCast(sheet, SheetBigDecimal.class).getValue().toBigInteger()
                    .toString();
        } else if (sheet instanceof SheetString) {
            return safeCast(sheet, SheetString.class).getValue();
        }
        return null;
    }

    /**
     * Builds a message stating that a reference for the object was not found.
     *
     * @param importableSheet Metadata of the cell/column/row being analyzed
     * @return Formatted message
     */
    @SuppressWarnings("unchecked")
    public String buildNullRefMessage(ImportableSheet<?> importableSheet) { // Renamed from buildNullObjMsg
        AbstractSheet<T> sheet = (AbstractSheet<T>) importableSheet;
        // Assuming AbstractSheet has getColumn() and getColumnTitle()
        return String.format(Translator.REFERENCE_NOT_FOUND_MESSAGE,
                sheet.getLineNumber(),
                sheet.getColumn(),
                sheet.getColumnTitle());
    }

    protected Long getLongValue(RowData row, String columnIdentifier) {
        return row.getCellByIdentifier(columnIdentifier) != null
                ? Long.valueOf(row.getCellByIdentifier(columnIdentifier).getValue().toString())
                : null;
    }

    protected String getStringValue(RowData row, String columnIdentifier) {
        return row.getCellByIdentifier(columnIdentifier) != null
                ? row.getCellByIdentifier(columnIdentifier).getValue().toString()
                : null;
    }

    protected Double getDoubleValue(RowData row, String columnIdentifier) {
        return row.getCellByIdentifier(columnIdentifier) != null
                ? Double.valueOf(row.getCellByIdentifier(columnIdentifier).getValue().toString())
                : null;
    }

    protected Integer getIntegerValue(RowData row, String columnIdentifier) {
        return row.getCellByIdentifier(columnIdentifier) != null
                ? Integer.valueOf(row.getCellByIdentifier(columnIdentifier).getValue().toString())
                : null;
    }

    protected LocalDate getLocalDateValue(RowData row, String columnIdentifier) {
        if (row.getCellByIdentifier(columnIdentifier) == null) {
            return null;
        }

        Object value = row.getCellByIdentifier(columnIdentifier).getValue();

        if (value == null) {
            return null;
        }

        // Assuming 'SheetData' was meant to be 'SheetDate' based on context
        if (value instanceof SheetDate) {
            SheetDate sheetDate = (SheetDate) value;
            return sheetDate.getValue() instanceof LocalDate ? (LocalDate) sheetDate.getValue() : null;
        }

        if (value instanceof LocalDate) {
            return (LocalDate) value;
        }

        return null;
    }

}