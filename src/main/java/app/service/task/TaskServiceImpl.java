package app.service.task;

import app.dto.task.CreateTaskRequestDto;
import app.dto.task.TaskDtoWithoutLabelsAndComments;
import app.dto.task.TaskResponseDto;
import app.exception.EntityNotFoundException;
import app.mapper.TaskMapper;
import app.model.Project;
import app.model.Task;
import app.repository.ProjectRepository;
import app.repository.TaskRepository;
import app.service.comment.CommentService;
import app.service.notification.NotificationService;
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
    private final NotificationService notificationService;

    @Override
    public TaskDtoWithoutLabelsAndComments save(CreateTaskRequestDto requestDto, Long projectId) {
        Project project = getProjectById(projectId);
        Task task = taskMapper.toModel(requestDto);
        task.setProject(project);
        task.setComments(Set.of());
        task.setLabels(List.of());
        project.getTasks().add(task);

        TaskDtoWithoutLabelsAndComments dto = taskMapper.toDtoWithoutLabelsAndComments(
                                                                    taskRepository.save(task));
        notificationService.notifyAssigneeOnTaskCreation(task.getAssignee().getChatId(), task);
        return dto;
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
    public TaskResponseDto getById(Long projectId, Long id) {
        Task task = getTask(projectId, id);
        return taskMapper.toDto(task);
    }

    @Override
    public TaskResponseDto update(Long projectId, Long id, CreateTaskRequestDto requestDto) {
        if (taskRepository.findByIdAndProjectId(id, projectId).isPresent()) {
            Task task = taskMapper.toModel(requestDto);
            task.setId(id);
            TaskResponseDto responseDto = taskMapper.toDto(taskRepository.save(task));
            notificationService.notifyAssigneeOnTaskUpdate(task.getAssignee().getId(),task);
            return responseDto;
        }
        throw new EntityNotFoundException("Can't find task with id " + id);
    }

    @Override
    public void delete(Long projectId, Long id) {
        Task task = getTask(projectId, id);
        task.getComments().forEach(comment -> commentService.delete(comment.getId()));
        taskRepository.delete(task);
    }

    private Task getTask(Long projectId, Long id) {
        return taskRepository
                .findByIdAndProjectId(id, projectId)
                .orElseThrow(() -> new EntityNotFoundException("Can't find task with id " + id));
    }

    private Project getProjectById(Long id) {
        return projectRepository
                .findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Can't find project by id: " + id));
    }
}
