package app.controller;

import app.dto.user.UserLoginRequestDto;
import app.dto.user.UserLoginResponseDto;
import app.dto.user.UserRequestDto;
import app.dto.user.UserResponseDto;
import app.exception.RegistrationException;
import app.security.AuthenticationService;
import app.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/auth")
public class AuthController {
    private final UserService userService;

    private final AuthenticationService authenticationService;

    @PostMapping("/login")
    @Operation(summary = "Login User",
            description = "Login an existing user.")
    public UserLoginResponseDto login(@RequestBody UserLoginRequestDto loginRequestDto) {
        return authenticationService.authenticate(loginRequestDto);
    }

    @PostMapping("/registration")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(summary = "Register user",
            description = "Register a new user")
    public UserResponseDto registerUser(@RequestBody @Valid UserRequestDto requestDto)
            throws RegistrationException {
        return userService.registerUser(requestDto);
    }
}
