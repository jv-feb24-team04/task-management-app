package app.controller;

import app.dto.project.ProjectRequestDto;
import app.dto.project.ProjectResponseDto;
import app.dto.project.ProjectStatusDto;
import app.model.User;
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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
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

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Add project",
            description = "Add a new project")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDto createProject(
            @RequestBody @Valid ProjectRequestDto projectDto
    ) {
        return projectService.saveProject(projectDto);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Retrieve all projects",
            description = "Retrieve all available projects")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<ProjectResponseDto> getAllProjects(
            @ParameterObject
            @PageableDefault(sort = {"name"}, value = 5) Pageable pageable,
            @AuthenticationPrincipal User user
    ) {
        return projectService.getAllProjects(pageable, user);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @Operation(summary = "Retrieve project by its id",
            description = "Retrieve all information about the project by its ID")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    public ProjectResponseDto getProjectDetailsById(
            @PathVariable @Parameter(description = "Project ID", example = "10") Long id,
            @AuthenticationPrincipal User user
    ) {
        return projectService.getProjectDetailsById(id, user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update project",
            description = "Update the project by its unique identifier")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public ProjectResponseDto updateProject(
            @PathVariable @Parameter(description = "Project ID", example = "10") Long id,
            @RequestBody @Valid ProjectRequestDto projectDto,
            @AuthenticationPrincipal User user
    ) {
        return projectService.updateProject(id, projectDto, user);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @Operation(summary = "Update project status",
            description = "Update the project status by its unique identifier")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    public void updateStatus(
            @PathVariable Long id,
            @RequestBody @Valid ProjectStatusDto statusDto,
            @AuthenticationPrincipal User user
    ) {
        projectService.updateProjectStatus(id, statusDto, user);
    }

    @DeleteMapping("/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(summary = "Delete project",
            description = "Delete a project by its unique identifier")
    public void deleteProjectById(
            @PathVariable @Parameter(description = "Project ID") Long id,
            @AuthenticationPrincipal User user
    ) {
        projectService.deleteProject(id, user);
    }
}
