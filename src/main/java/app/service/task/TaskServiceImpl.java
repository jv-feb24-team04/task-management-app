package app.service.task;

import app.dto.task.CreateTaskRequestDto;
import app.dto.task.TaskResponseDto;
import app.dto.task.UpdateTaskRequestDto;
import app.exception.EntityNotFoundException;
import app.mapper.TaskMapper;
import app.model.Project;
import app.model.Task;
import app.model.TaskStatus;
import app.repository.ProjectRepository;
import app.repository.TaskRepository;
import app.service.comment.CommentService;
import java.util.List;
import java.util.Set;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TaskServiceImpl implements TaskService {
    private final TaskRepository taskRepository;
    private final TaskMapper taskMapper;
    private final ProjectRepository projectRepository;
    private final CommentService commentService;

    @Override
    public TaskResponseDto save(CreateTaskRequestDto requestDto, Long projectId) {
        Project project = getProjectById(projectId);
        Task task = taskMapper.toModelCreate(requestDto);
        task.setProject(project);
        task.setStatus(TaskStatus.NOT_STARTED);
        task.setLabels(List.of());
        task.setComments(Set.of());
        project.getTasks().add(task);

        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public List<TaskResponseDto> getAllByProjectId(Long projectId, Pageable pageable) {
        List<Task> tasks = taskRepository.findAllByProjectId(projectId, pageable);
        if (tasks.isEmpty()) {
            throw new EntityNotFoundException("No task found for project " + projectId);
        }

        return tasks.stream()
                .map(taskMapper::toDto)
                .toList();
    }

    @Override
    public TaskResponseDto getById(Long id) {
        Task task = getTask(id);
        return taskMapper.toDto(task);
    }

    @Override
    public TaskResponseDto updateTask(Long id, UpdateTaskRequestDto requestDto) {
        Task taskFromDb = getTask(id);
        Task task = taskMapper.toModelUpdate(requestDto);
        task.setProject(getProjectById(taskFromDb.getProject().getId()));
        task.setId(id);
        return taskMapper.toDto(taskRepository.save(task));
    }

    @Override
    public void delete(Long id) {
        Task task = getTask(id);
        task.getComments().forEach(comment -> commentService.delete(comment.getId()));
        taskRepository.delete(task);
    }

    private Task getTask(Long id) {
        return taskRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find task with id " + id));
    }

    private Project getProjectById(Long id) {
        return projectRepository
                .findProjectById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find project by id: " + id));
    }
}
