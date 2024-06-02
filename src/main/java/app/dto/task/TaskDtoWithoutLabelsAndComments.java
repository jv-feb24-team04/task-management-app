package app.dto.task;

import app.model.Priority;
import app.model.TaskStatus;
import java.time.LocalDate;
import lombok.Data;

@Data
public class TaskDtoWithoutLabelsAndComments {
    private Long id;
    private String name;
    private String description;
    private Priority priority;
    private TaskStatus status;
    private LocalDate dueDate;
    private Long projectId;
    private Long assigneeId;
}
