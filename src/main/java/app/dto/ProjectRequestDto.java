package app.dto;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ProjectRequestDto(
        @NotBlank
        @Size(max = 250)
        String name,
        @NotBlank
        @Size(max = 250)
        String description,
        @NotNull
        @FutureOrPresent
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate startDate,
        @NotNull
        @FutureOrPresent
        @JsonFormat(pattern = "dd-MM-yyyy")
        LocalDate endDate
) {
}
