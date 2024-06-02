package app.dto.task;

import app.model.Priority;
import app.model.TaskStatus;
import com.fasterxml.jackson.annotation.JsonFormat;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;
import lombok.Data;
import org.springframework.format.annotation.DateTimeFormat;

@Data
public class CreateTaskRequestDto {
    @NotBlank(message = "Task name must be defined")
    private String name;
    private String description;
    @NotNull(message = "Priority for the task must be defined")
    private Priority priority;
    @NotNull(message = "Task status must be defined")
    private TaskStatus status;
    @NotNull(message = "Task due date must be defined")
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate dueDate;
    @NotNull(message = "Assignee for the task must be defined")
    private Long assigneeId;
}
