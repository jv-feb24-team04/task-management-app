package app.dto.project;

import app.model.ProjectStatus;
import io.swagger.v3.oas.annotations.media.Schema;
import java.time.LocalDate;
import java.util.List;

public record ProjectResponseDto(
        @Schema(example = "10")
        Long id,
        @Schema(example = "Project name example")
        String name,
        @Schema(example = "Project description example")
        String description,
        @Schema(example = "2024-01-01")
        LocalDate startDate,
        @Schema(example = "2024-01-01")
        LocalDate endDate,
        @Schema(example = "INITIATED")
        ProjectStatus status,
        @Schema(example = "[1, 3, 5]")
        List<Long> taskIds
) {
}
