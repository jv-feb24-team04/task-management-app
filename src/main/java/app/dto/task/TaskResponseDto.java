package app.dto.task;

import app.dto.comment.CommentResponseDto;
import app.dto.label.LabelResponseDto;
import app.model.Task.Priority;
import app.model.TaskStatus;
import java.time.LocalDate;
import java.util.List;
import java.util.Set;
import lombok.Data;

@Data
public class TaskResponseDto {
    private Long id;
    private String name;
    private String description;
    private Priority priority;
    private TaskStatus status;
    private LocalDate dueDate;
    private Long projectId;
    private Long assigneeId;
    private List<LabelResponseDto> labels;
    private Set<CommentResponseDto> comments;
}
