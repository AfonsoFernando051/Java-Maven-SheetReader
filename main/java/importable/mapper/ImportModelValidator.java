package importable.mapper;

import importable.model.row.RowData;
// Assuming 'File' was a typo and should be the previously defined exception
import importable.utils.FileProcessingException; 

/**
 * Interface that defines a validation contract for import models.
 *
 * @param <T> The type of the object being validated
 * @author Fernando Dias
 */
public interface ImportModelValidator<T> {

    /**
     * Validates the provided object.
     *
     * @param object The object to be validated
     * @param row    The row being validated
     * @throws FileProcessingException if the object is invalid
     */
    void validate(T object, RowData row)
            throws FileProcessingException; 
}