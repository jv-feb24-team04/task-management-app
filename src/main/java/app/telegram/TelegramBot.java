package app.telegram;

import app.repository.UserRepository;
import app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.apache.commons.validator.routines.EmailValidator;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@Component
@RequiredArgsConstructor
public class TelegramBot extends TelegramLongPollingBot {
    private final UserService userService;
    private final EmailValidator validator;
    private final UserRepository userRepository;

    @Value("${telegram.bot.username}")
    private String botUsername;
    @Value("${telegram.bot.token}")
    private String botToken;
    @Value("${telegram.bot.message.restriction}")
    private String restrictionMessage;
    @Value("${telegram.bot.message.success}")
    private String successMessage;

    @Override
    public String getBotUsername() {
        return botUsername;
    }

    @Override
    public String getBotToken() {
        return botToken;
    }

    @Override
    public void onUpdateReceived(Update update) {
        if (update.hasMessage() && update.getMessage().hasText()) {
            String messageText = update.getMessage().getText();
            String chatId = update.getMessage().getChatId().toString();

            if (messageText.equals("/start")) {
                handleStartCommand(chatId);
            } else if (isValidEmail(messageText)) {
                handleEmail(chatId, messageText);
            } else {
                sendRestrictionMessage(chatId);
            }
        }
    }

    private void handleStartCommand(String chatId) {
        sendMessage(chatId, "Please enter your email:");
    }

    private void handleEmail(String chatId, String email) {
        if (userService.checkUserRegistered(email)) {
            sendSuccessMessage(chatId);
            userService.updateUserChatId(email, chatId);
        } else {
            sendRestrictionMessage(chatId);
        }
    }

    private boolean isValidEmail(String email) {
        return validator.isValid(email);
    }

    private void sendRestrictionMessage(String chatId) {
        sendMessage(chatId, restrictionMessage);
    }

    private void sendSuccessMessage(String chatId) {
        sendMessage(chatId, successMessage);
    }

    private void sendMessage(String chatId, String text) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        try {
            execute(message);
        } catch (TelegramApiException e) {
            throw new RuntimeException("Error sending message to chatId: " + chatId, e);
        }
    }
}
