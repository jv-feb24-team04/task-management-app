package app.service.user;

import app.model.User;

public interface UserService {
    boolean checkUserRegistered(String email);

    void updateUserChatId(String email, String chatId);

    User getById(Long id);
}
