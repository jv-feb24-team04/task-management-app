package app.service.user;

import app.dto.user.UserRequestDto;
import app.dto.user.UserResponseDto;
import app.dto.user.UserUpdateInfoDto;
import app.dto.user.UserUpdateRoleDto;
import app.exception.EntityNotFoundException;
import app.exception.RegistrationException;
import app.mapper.UserMapper;
import app.model.Role;
import app.model.User;
import app.repository.RoleRepository;
import app.repository.UserRepository;
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
        User user = userRepository.findById(userId).orElseThrow(() ->
                new EntityNotFoundException("Failed to find User by id="
                        + userId));

        if (roleRepository.findByRole(Role.RoleName.valueOf(updateRoleDto.getRole())) == null) {
            throw new EntityNotFoundException("Role " + updateRoleDto.getRole() + " not found");
        }
        Role role = roleRepository.findByRole(Role.RoleName.valueOf(updateRoleDto.getRole()));

        user.getRoles().add(role);
        userRepository.save(user);
    }

    @Override
    public UserResponseDto getUserInfo(User user) {
        return userMapper.toDto(user);
    }

    @Override
    public UserResponseDto updateUserInfo(User user, UserUpdateInfoDto updateInfoDto) {
        if (!userRepository.existsById(user.getId())) {
            throw new EntityNotFoundException("Failed to find User by id=" + user.getId());
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
            throw new RegistrationException("The user has already been registered with eail="
                                                                    + userRequestDto.getEmail());
        }
        User user = userMapper.toModel(userRequestDto);
        user.setPassword(passwordEncoder.encode(userRequestDto.getPassword()));
        Role defaultRole = roleRepository.findByRole(Role.RoleName.USER);
        user.setRoles(Collections.singleton(defaultRole));

        User savedUser = userRepository.save(user);
        return userMapper.toDto(savedUser);
    }

    public String getUserChatId(Long id) {
        Optional<User> optionalUser = userRepository.findById(id);
        if (optionalUser.isPresent()) {
            return optionalUser.get().getChatId();
        }
        throw new EntityNotFoundException("Failed to find User by id=" + id);
    }
}
