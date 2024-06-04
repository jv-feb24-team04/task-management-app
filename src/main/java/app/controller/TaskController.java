package app.controller;

import app.dto.task.CreateTaskRequestDto;
import app.dto.task.TaskResponseDto;
import app.dto.task.UpdateTaskRequestDto;
import app.model.User;
import app.service.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Task management", description = "Endpoints for managing tasks")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/tasks")
public class TaskController {
    private final TaskService taskService;

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Add task",
            description = "Add a new task for a project identified by its ID")
    public TaskResponseDto createTask(
            @RequestParam Long projectId,
            @RequestBody @Valid CreateTaskRequestDto requestDto) {

        return taskService.save(requestDto, projectId);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.ACCEPTED)
    @GetMapping
    @Operation(summary = "Retrieve all tasks by user ID",
            description = "Retrieve all tasks associated with a project identified by its ID")
    public List<TaskResponseDto> getAllByProjectId(
            @RequestParam Long projectId,
            Pageable pageable) {

        return taskService.getAllByProjectId(projectId, pageable);
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Retrieve task by its ID",
            description = "Retrieve all information about the task by its ID")
    public TaskResponseDto getTaskById(@PathVariable Long id) {
        return taskService.getById(id);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @PutMapping("/{id}")
    @Operation(summary = "Update task",
            description = "Update information about the task by its ID")
    public TaskResponseDto updateTask(
            @PathVariable Long id,
            @RequestBody @Valid UpdateTaskRequestDto requestDto) {

        return taskService.updateTask(id, requestDto);
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
=======
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete task",
            description = "Delete a task by its unique identifier")
    public void deleteTask(
            @AuthenticationPrincipal User user,
            @PathVariable Long id
    ) {
        taskService.delete(id, user.getId());
    }
}
