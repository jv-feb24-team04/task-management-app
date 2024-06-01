package app.exception;

public class NotUniqueValueException extends RuntimeException {
    public NotUniqueValueException(String message) {
        super(message);
    }
}
