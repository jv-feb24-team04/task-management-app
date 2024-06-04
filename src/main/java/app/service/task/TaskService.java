package app.service.task;

import app.dto.task.CreateTaskRequestDto;
import app.dto.task.TaskResponseDto;
import app.dto.task.UpdateTaskRequestDto;
import app.model.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponseDto save(CreateTaskRequestDto requestDto, Long projectId);

    List<TaskResponseDto> getAllByProjectId(Long projectId, Pageable pageable);

    TaskResponseDto getById(Long id);

    Optional<Task> getTaskById(Long id);

    TaskResponseDto updateTask(Long id, UpdateTaskRequestDto requestDto);

    void delete(Long id);
}
