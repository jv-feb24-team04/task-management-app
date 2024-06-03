package app.dto.user;

import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class UserUpdateRoleDto {
    @NotNull
    private String role;
}
