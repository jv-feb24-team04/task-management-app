package app.exception;

public class TelegramBotProcessingException extends RuntimeException {
    public TelegramBotProcessingException(String message, Throwable cause) {
        super(message, cause);
    }
}
