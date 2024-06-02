package app.service;

import static app.model.Role.RoleName.ADMIN;

import java.util.Collections;
import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import app.dto.UserRequestDto;
import app.dto.UserResponseDto;
import app.dto.UserUpdateInfoDto;
import app.dto.UserUpdateRoleDto;
import app.exception.RegistrationException;
import app.mapper.UserMapper;
import app.model.Role;
import app.model.User;
import app.repository.RoleRepository;
import app.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final RoleRepository roleRepository;
    private final UserMapper userMapper;
    private final PasswordEncoder passwordEncoder;

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
    public UserResponseDto registerUser(UserRequestDto userRequestDto) throws RegistrationException {
        if (userRepository.findByEmail(userRequestDto.getEmail()).isPresent()) {
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
