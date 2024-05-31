package app.dto.project;

import jakarta.validation.constraints.Future;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import org.springframework.format.annotation.DateTimeFormat;
import java.time.LocalDate;

public record ProjectRequestDto(
        @NotBlank
        @Size(max = 250)
        String name,
        @NotBlank
        @Size(max = 250)
        String description,
        @NotNull
        @Future
        @DateTimeFormat(pattern = "dd-MM-yyyy")
        LocalDate startDate,
        @NotNull
        @Future
        @DateTimeFormat(pattern = "dd-MM-yyyy")
        LocalDate endDate
) {
}
