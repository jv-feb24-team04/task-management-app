package app.service.user;

import app.model.User;
import app.repository.UserRepository;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;

    @Override
    public boolean checkUserRegistered(String email) {
        return userRepository.findUserByEmail(email).isPresent();
    }

    @Override
    public void updateUserChatId(String email, String chatId) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        if (optionalUser.isPresent()) {
            User user = optionalUser.get();
            user.setChatId(chatId);
            userRepository.save(user);
        }
    }
}
