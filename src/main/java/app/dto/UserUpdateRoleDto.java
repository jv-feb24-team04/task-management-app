package app.dto;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateRoleDto {
    @NotNull
    private String role;
}