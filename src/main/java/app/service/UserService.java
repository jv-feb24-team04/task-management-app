package app.service;

import app.dto.UserRequestDto;
import app.dto.UserResponseDto;
import app.dto.UserUpdateInfoDto;
import app.dto.UserUpdateRoleDto;
import app.exception.RegistrationException;
import app.model.User;

public interface UserService {
    void updateUserRole(Long userId, UserUpdateRoleDto updateRoleDto);
    UserResponseDto getUserInfo(User user);
    UserResponseDto updateUserInfo(User user, UserUpdateInfoDto updateInfoDto);
    UserResponseDto registerUser(UserRequestDto userRequestDto) throws RegistrationException;

}
