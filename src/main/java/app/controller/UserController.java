package app.controller;

import app.dto.user.UserResponseDto;
import app.dto.user.UserUpdateInfoDto;
import app.dto.user.UserUpdateRoleDto;
import app.model.User;
import app.service.user.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/users")
public class UserController {
    private final UserService userService;

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/{id}/role")
    public void updateUserRole(@PathVariable Long id,
                               @RequestBody UserUpdateRoleDto updateRoleDto) {
        userService.updateUserRole(id, updateRoleDto);
    }

    @ResponseStatus(HttpStatus.OK)
    @GetMapping("/me")
    public UserResponseDto getUserInfo(@AuthenticationPrincipal User user) {
        return userService.getUserInfo(user);
    }

    @ResponseStatus(HttpStatus.OK)
    @PutMapping("/me")
    public UserResponseDto updateUserInfo(@AuthenticationPrincipal User user,
                                          @RequestBody UserUpdateInfoDto updateInfoDto) {
        return userService.updateUserInfo(user, updateInfoDto);
    }
}
