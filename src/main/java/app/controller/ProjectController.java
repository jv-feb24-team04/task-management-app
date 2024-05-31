package app.controller;

import app.dto.project.ProjectRequestDto;
import app.dto.project.ProjectResponseDto;
import app.model.User;
import app.service.project.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/projects")
@RequiredArgsConstructor
public class ProjectController {
    private final ProjectService projectService;

    @PostMapping
    @PreAuthorize("hasAnyRole('USER','ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    public ProjectResponseDto createProject(
            @RequestBody ProjectRequestDto requestDto,
            @AuthenticationPrincipal User user
    ) {
        return projectService.saveProject(requestDto, user);
    }
}
