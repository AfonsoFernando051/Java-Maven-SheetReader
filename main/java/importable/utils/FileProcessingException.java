package importable.utils;

/**
 * Exception thrown when an error occurs during file processing.
 *
 * @author Fernando Dias
 */
public class FileProcessingException extends RuntimeException {

    /**
     * Serial ID
     */
    private static final long serialVersionUID = 1L;

    /**
     * Creates a new exception with the specified message.
     *
     * @param message The detail message.
     */
    public FileProcessingException(String message) {
        super(message);
    }

    /**
     * Creates a new exception with the specified message and cause.
     *
     * @param message The detail message.
     * @param cause   The original cause of the error.
     */
    public FileProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}