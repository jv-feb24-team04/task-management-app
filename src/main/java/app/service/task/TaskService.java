package app.service.task;

import app.dto.task.CreateTaskRequestDto;
import app.dto.task.TaskDtoWithoutLabelsAndComments;
import app.dto.task.TaskResponseDto;
import java.util.List;

public interface TaskService {
    TaskDtoWithoutLabelsAndComments save(CreateTaskRequestDto requestDto, Long projectId);

    List<TaskResponseDto> getAllByProjectId(Long projectId);

    TaskResponseDto getById(Long projectId, Long id);

    TaskResponseDto updateStatus(Long projectId, Long id, CreateTaskRequestDto requestDto);

    void delete(Long projectId, Long id);
}
