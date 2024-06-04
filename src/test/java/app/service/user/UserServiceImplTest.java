package app.service.user;

import static app.model.Role.RoleName.USER;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.dto.user.UserRequestDto;
import app.dto.user.UserResponseDto;
import app.dto.user.UserUpdateInfoDto;
import app.dto.user.UserUpdateRoleDto;
import app.mapper.UserMapper;
import app.model.Role;
import app.model.User;
import app.repository.RoleRepository;
import app.repository.UserRepository;
import java.util.Optional;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.crypto.password.PasswordEncoder;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {
    @Mock
    private UserRepository userRepository;
    @Mock
    private RoleRepository roleRepository;
    @Mock
    private UserMapper userMapper;
    @Mock
    private PasswordEncoder passwordEncoder;

    private UserService userService;

    @BeforeEach
    void setUp() {
        userService = new UserServiceImpl(
                userRepository,
                roleRepository,
                userMapper,
                passwordEncoder);
    }

    @Test
    public void checkUserRegistered_ValidEmail_ReturnTrue() {
        User user = new User();
        user.setEmail("test@gmail.com");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));

        Assertions.assertTrue(userService.checkUserRegistered(user.getEmail()));
    }

    @Test
    public void updateUserChatId_ValidData_Success() {
        User user = new User();
        user.setEmail("test@gmail.com");

        when(userRepository.findUserByEmail(user.getEmail())).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);

        userService.updateUserChatId(user.getEmail(), "test");
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void updateUserRole_ValidData_Success() {
        UserUpdateRoleDto requestDto = new UserUpdateRoleDto();
        requestDto.setRole("USER");

        User user = new User();
        user.setId(1L);
        user.setEmail("test@gmail.com");

        Role role = new Role();
        role.setRole(USER);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(roleRepository.findByRole(USER)).thenReturn(role);
        when(userRepository.save(user)).thenReturn(user);

        userService.updateUserRole(1L, requestDto);
        verify(userRepository, times(1)).save(user);
    }

    @Test
    public void getUserInfo_ValidUser_ReturnUserResponseDto() {
        User user = new User();
        user.setEmail("test@gmail.com");

        UserResponseDto responseDto = new UserResponseDto();
        user.setEmail("test@gmail.com");

        when(userMapper.toDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.getUserInfo(user);
        assertNotNull(result);
        assertEquals(responseDto, result);
    }

    @Test
    public void updateUserInfo_ValidData_ReturnUserResponseDto() {
        UserUpdateInfoDto requestDto = new UserUpdateInfoDto();
        requestDto.setFirstName("Updated Name");
        requestDto.setLastName("Updated LastName");
        requestDto.setPassword("Updated Password");

        User user = new User();
        user.setId(1L);
        user.setPassword("Updated Password");
        user.setFirstName("Updated Name");
        user.setLastName("Updated LastName");

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setId(1L);
        responseDto.setFirstName("Updated Name");
        responseDto.setLastName("Updated LastName");

        when(userRepository.existsById(1L)).thenReturn(true);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.updateUserInfo(user, requestDto);
        assertNotNull(result);
        assertEquals(responseDto, result);
    }

    @Test
    public void registerUser_ValidUserRequestDto_ReturnUserResponseDto() {
        UserRequestDto requestDto = new UserRequestDto();
        requestDto.setEmail("test@gmail.com");
        requestDto.setFirstName("Name");
        requestDto.setPassword("Password");

        Role role = new Role();
        role.setRole(USER);

        User user = new User();
        user.setFirstName("Name");
        user.setPassword("Password");

        UserResponseDto responseDto = new UserResponseDto();
        responseDto.setFirstName("Name");

        when(userMapper.toModel(requestDto)).thenReturn(user);
        when(roleRepository.findByRole(USER)).thenReturn(role);
        when(userRepository.save(user)).thenReturn(user);
        when(userMapper.toDto(user)).thenReturn(responseDto);

        UserResponseDto result = userService.registerUser(requestDto);
        assertNotNull(result);
        assertEquals(responseDto, result);
    }
}
