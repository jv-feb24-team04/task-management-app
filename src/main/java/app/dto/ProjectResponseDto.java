package app.dto;

import app.model.ProjectStatus;
import java.time.LocalDate;

public record ProjectResponseDto(
        Long id,
        String name,
        String description,
        LocalDate startDate,
        LocalDate endDate,
        ProjectStatus status
) {
}
