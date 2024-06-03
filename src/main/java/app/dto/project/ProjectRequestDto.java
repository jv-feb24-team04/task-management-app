package app.dto.project;

import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record ProjectRequestDto(
        @NotBlank(message = "Project name must be defined")
        @Size(max = 250, message = "The length of the project name "
                + "must be no more than 250 characters")
        String name,
        @NotBlank(message = "Project description must be defined")
        @Size(max = 250, message = "The length of the project description "
                + "must be no more than 250 characters")
        String description,
        @NotNull(message = "Project start date must be defined")
        @FutureOrPresent(message = "Project start date must be at least the current date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate startDate,
        @NotNull(message = "Project end date must be defined")
        @FutureOrPresent(message = "Project end date must be at least the current date")
        @JsonFormat(pattern = "yyyy-MM-dd")
        LocalDate endDate
) {
}
