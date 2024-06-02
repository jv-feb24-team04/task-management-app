package app.service;

public interface UserService {
    boolean checkUserRegistered(String email);

    void updateUserChatId(String email, String chatId);
}
