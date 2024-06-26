package app.controller;

import app.dto.user.UserResponseDto;
import app.dto.user.UserUpdateInfoDto;
import app.dto.user.UserUpdateRoleDto;
import app.model.User;
import app.service.user.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    private final UserService userService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/role")
    @Operation(summary = "Update user role",
            description = "Update the user role by its unique identifier")
    public void updateUserRole(@PathVariable Long id,
                               @RequestBody UserUpdateRoleDto updateRoleDto) {
        userService.updateUserRole(id, updateRoleDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    @Operation(summary = "Retrieve authenticated user",
            description = "Retrieve all information about the currently authenticated user")
    public UserResponseDto getUserInfo(@AuthenticationPrincipal User user) {
        return userService.getUserInfo(user);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/me")
    @Operation(summary = "Update user",
            description = "Update information for the currently authenticated user")
    public UserResponseDto updateUserInfo(@AuthenticationPrincipal User user,
                                          @RequestBody UserUpdateInfoDto updateInfoDto) {
        return userService.updateUserInfo(user, updateInfoDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    @DeleteMapping
    @Operation(summary = "Delete user",
            description = "Delete user by Id")
    public void deleteUserById(@RequestParam Long id) {
        userService.deleteUserById(id);
    }
}
