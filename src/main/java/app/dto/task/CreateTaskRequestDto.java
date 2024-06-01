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
    @NotBlank
    private String name;
    private String description;
    @NotNull
    private Priority priority;
    @NotNull
    private TaskStatus status;
    @NotNull
    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE)
    @JsonFormat(pattern = "YYYY-MM-dd")
    private LocalDate dueDate;
    @NotNull
    private Long assigneeId;
}
