package app.service.user;

import app.model.User;

import app.dto.user.UserRequestDto;
import app.dto.user.UserResponseDto;
import app.dto.user.UserUpdateInfoDto;
import app.dto.user.UserUpdateRoleDto;
import app.exception.RegistrationException;
import app.model.User;

public interface UserService {
    boolean checkUserRegistered(String email);

    void updateUserChatId(String email, String chatId);

    User getById(Long id);

    void updateUserRole(Long userId, UserUpdateRoleDto updateRoleDto);

    UserResponseDto getUserInfo(User user);

    UserResponseDto updateUserInfo(User user, UserUpdateInfoDto updateInfoDto);

    UserResponseDto registerUser(UserRequestDto userRequestDto) throws RegistrationException;
}
