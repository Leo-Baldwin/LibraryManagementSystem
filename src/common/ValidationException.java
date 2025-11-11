package common;

/**
 * Used to indicate that a business rule or validation constraint within the system has been violated.
 */
public class ValidationException extends RuntimeException {

    /**
     * Constructs a new ValidationException with the specified message.
     *
     * @param message the message explanation of what validation failed
     */
    public ValidationException(String message){
        super(message);
    }

    /**
     * Constructs a new ValidationException with a specified message and cause.
     *
     * @param message the message explanation of what validation failed
     * @param cause the cause of the rule or constraint violation
     */
    public ValidationException(String message, Throwable cause){
        super(message, cause);
    }
}
