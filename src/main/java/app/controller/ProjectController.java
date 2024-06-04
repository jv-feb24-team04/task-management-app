package app.controller;

import app.dto.project.ProjectRequestDto;
import app.dto.project.ProjectResponseDto;
import app.dto.project.ProjectStatusDto;
import app.model.User;
import app.service.project.ProjectService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ProblemDetail;
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

    @Operation(summary = "Create a project",
            description = "Create a new project")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "Successfully created"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Not enough access rights",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    public ProjectResponseDto createProject(
            @RequestBody @Valid ProjectRequestDto projectDto
    ) {
        return projectService.saveProject(projectDto);
    }

    @Operation(summary = "Get all projects",
            description = "Get all available projects with pagination and sorting")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    public List<ProjectResponseDto> getAllProjects(
            @ParameterObject
            @PageableDefault(sort = {"name"}, value = 5) Pageable pageable,
            @AuthenticationPrincipal User user
    ) {
        return projectService.getAllProjects(pageable, user);
    }

    @Operation(summary = "Get a specific project",
            description = "Get a specific project by ID")
    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    @ResponseStatus(HttpStatus.OK)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Project with this id not exist",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public ProjectResponseDto getProjectDetailsById(
            @PathVariable @Parameter(description = "Project ID", example = "10") Long id,
            @AuthenticationPrincipal User user
    ) {
        return projectService.getProjectDetailsById(id, user);
    }

    @Operation(summary = "Update a specific project",
            description = "Update a specific project by ID")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Not enough access rights",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
    })
    public ProjectResponseDto updateProject(
            @PathVariable @Parameter(description = "Project ID", example = "10") Long id,
            @RequestBody @Valid ProjectRequestDto projectDto,
            @AuthenticationPrincipal User user
    ) {
        return projectService.updateProject(id, projectDto, user);
    }

    @Operation(summary = "Update a specific project status",
            description = "Update a specific project status by ID")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PatchMapping("/{id}")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @ApiResponses(value = {
            @ApiResponse(responseCode = "202", description = "Successfully updated"),
            @ApiResponse(responseCode = "400", description = "Invalid request body",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Not enough access rights",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
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
    @Operation(summary = "Delete the project by id", description = "Delete a book by id if exist")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "204", description = "No content - successfully deleted"),
            @ApiResponse(responseCode = "401", description = "Required authorization",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "403", description = "Not enough access rights",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class))),
            @ApiResponse(responseCode = "404", description = "Not found - wrong id",
                    content = @Content(schema = @Schema(implementation = ProblemDetail.class)))
    })
    public void deleteProjectById(
            @PathVariable @Parameter(description = "Project ID") Long id,
            @AuthenticationPrincipal User user
    ) {
        projectService.deleteProject(id, user);
    }
}
