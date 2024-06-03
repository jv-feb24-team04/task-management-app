package app.controller;

import app.dto.project.ProjectRequestDto;
import app.dto.project.ProjectResponseDto;
import app.dto.project.ProjectStatusDto;
import app.service.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PatchMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Project management", description = "Endpoints for managing projects")
@RestController
@RequestMapping("/api/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @Operation(summary = "Create a project",
            description = "Create a new project")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDto createProject(
            @RequestBody @Valid ProjectRequestDto projectDto
    ) {
        return projectService.saveProject(projectDto);
    }

    @Operation(summary = "Get all projects",
            description = "Get all available projects with pagination and sorting")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectResponseDto> getAllProjects(
            @ParameterObject
            @PageableDefault(sort = {"name"}, value = 5) Pageable pageable
    ) {
        return projectService.getAllProjects(pageable);
    }

    @Operation(summary = "Get a specific project",
            description = "Get a specific project by ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponseDto getProjectDetailsById(
            @PathVariable @Parameter(description = "Project ID", example = "10") Long id
    ) {
        return projectService.getProjectDetailsById(id);
    }

    @Operation(summary = "Update a specific project",
            description = "Update a specific project by ID")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProjectResponseDto updateProject(
            @PathVariable @Parameter(description = "Project ID", example = "10") Long id,
            @RequestBody @Valid ProjectRequestDto projectDto
    ) {
        return projectService.updateProject(id, projectDto);
    }

    @Operation(summary = "Update a specific project status",
            description = "Update a specific project status by ID")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid ProjectStatusDto statusDto
    ) {
        projectService.updateProjectStatus(id, statusDto);
    }
}