package app.exception;

public class TelegramBotInitializationException extends RuntimeException {
    public TelegramBotInitializationException(String message, Throwable cause) {
        super(message, cause);
    }
}
