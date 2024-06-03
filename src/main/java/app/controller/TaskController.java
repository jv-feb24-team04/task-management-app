package app.controller;

import app.dto.task.CreateTaskRequestDto;
import app.dto.task.TaskDtoWithoutLabelsAndComments;
import app.dto.task.TaskResponseDto;
import app.service.task.TaskService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Task management", description = "Endpoints for managing tasks")
@RequiredArgsConstructor
@RestController
@RequestMapping("/api/projects/{projectId}/tasks")
public class TaskController {
    private final TaskService taskService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping
    @Operation(summary = "Create a new task",
            description = "Create a new task for a project with project ID")
    public TaskDtoWithoutLabelsAndComments createTask(
            @PathVariable Long projectId,
            @RequestBody @Valid CreateTaskRequestDto requestDto) {

        return taskService.save(requestDto, projectId);
    }

    @GetMapping
    @Operation(summary = "Get all tasks",
            description = "Get all tasks by project ID")
    public List<TaskResponseDto> getAllByProjectId(@PathVariable Long projectId) {
        return taskService.getAllByProjectId(projectId);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Get task by ID",
            description = "Get all information about the task by ID")
    public TaskResponseDto getTaskById(@PathVariable Long projectId, @PathVariable Long id) {
        return taskService.getById(projectId, id);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Update task by ID",
            description = "Update all information about the task by ID")
    public TaskResponseDto updateTask(
            @PathVariable Long projectId,
            @PathVariable Long id,
            @RequestBody @Valid CreateTaskRequestDto requestDto) {

        return taskService.updateStatus(projectId, id, requestDto);
    }

    @ResponseStatus(HttpStatus.NO_CONTENT)
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete a task by ID",
            description = "Delete a task by ID, if there is one")
    public void deleteTask(@PathVariable Long projectId, @PathVariable Long id) {
        taskService.delete(projectId, id);
    }
}