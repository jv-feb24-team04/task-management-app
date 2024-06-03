package app.dto.project;

import app.model.ProjectStatus;
import com.fasterxml.jackson.annotation.JsonProperty;

public record ProjectStatusDto(
        @JsonProperty(value = "projectStatus", required = true)
        ProjectStatus projectStatus
) {
}
