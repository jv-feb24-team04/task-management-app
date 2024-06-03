package app.service.user;

import app.dto.UserRequestDto;
import app.dto.UserResponseDto;
import app.dto.UserUpdateInfoDto;
import app.dto.UserUpdateRoleDto;
import app.exception.RegistrationException;
import app.mapper.UserMapper;
import app.model.Role;
import app.model.User;
import app.repository.RoleRepository;
import app.repository.user.UserRepository;
import java.util.Collections;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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

    @Override
    @Transactional
    public void updateUserRole(Long userId, UserUpdateRoleDto updateRoleDto) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        long roleId = 1L;
        if ((updateRoleDto.getRole()).equals("ADMIN")) {
            roleId = 2L;
        }
        Role role = roleRepository.findById(roleId)
                .orElseThrow(() -> new RuntimeException("Role not found"));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public UserResponseDto getUserInfo(User user) {
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateUserInfo(User user, UserUpdateInfoDto updateInfoDto) {
        if (user == null) {
            throw new RuntimeException("User with username not found.");
        }

        user.setFirstName(updateInfoDto.getFirstName());
        user.setLastName(updateInfoDto.getLastName());
        user.setPassword(passwordEncoder.encode(updateInfoDto.getPassword()));

        User updatedUser = userRepository.save(user);

        return userMapper.toDto(updatedUser);
    }

    @Override
    @Transactional
    public UserResponseDto registerUser(UserRequestDto userRequestDto)
            throws RegistrationException {
        if (userRepository.findUserByEmail(userRequestDto.getEmail()).isPresent()) {
            throw new RegistrationException("Can't find register user");
        }
        User user = userMapper.toModel(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Role defaultRole = roleRepository.findByRole(Role.RoleName.USER);
        user.setRoles(Collections.singleton(defaultRole));

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }
}
