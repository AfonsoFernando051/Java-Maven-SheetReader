package importable.mapper;

import java.util.ArrayList;
import java.util.function.Function;

import importable.model.row.RowData;

/**
 * Interface for a sheet model mapper.
 *
 * @author Fernando Dias
 * @param <T> -> Generic type
 */
public interface InterfaceSheetMapper<T> { 

    /**
     * @return a concrete instance of the sheet model
     */
    T createInstance(); // Renamed from createInstance

    /**
     * @return a function that creates a list of models from a row
     */
    Function<RowData, ArrayList<T>> createModelsByRow();

}