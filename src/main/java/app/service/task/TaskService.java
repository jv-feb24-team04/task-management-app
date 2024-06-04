package app.service.task;

import app.dto.task.CreateTaskRequestDto;
import app.dto.task.TaskResponseDto;
import app.dto.task.UpdateTaskRequestDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface TaskService {
    TaskResponseDto save(CreateTaskRequestDto requestDto, Long projectId);

    List<TaskResponseDto> getAllByProjectId(Long projectId, Pageable pageable);

    TaskResponseDto getById(Long id);

    TaskResponseDto updateTask(Long id, UpdateTaskRequestDto requestDto);

    void delete(Long id);
}
