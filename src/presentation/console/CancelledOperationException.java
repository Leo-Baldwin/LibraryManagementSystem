package presentation.console;

public class CancelledOperationException extends RuntimeException {
    public CancelledOperationException() {
        super("Operation cancelled, returning to menu...");
    }
}