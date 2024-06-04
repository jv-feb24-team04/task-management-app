package app.service.task;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.dto.task.TaskResponseDto;
import app.dto.task.UpdateTaskRequestDto;
import app.mapper.TaskMapper;
import app.model.Project;
import app.model.Task;
import app.repository.ProjectRepository;
import app.repository.TaskRepository;
import app.service.comment.CommentService;
import app.service.notification.NotificationService;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class TaskServiceImplTest {
    @Mock
    private TaskRepository taskRepository;
    @Mock
    private TaskMapper taskMapper;
    @Mock
    private ProjectRepository projectRepository;
    @Mock
    private CommentService commentService;
    @Mock
    private NotificationService notificationService;

    private TaskService taskService;

    @BeforeEach
    void setUp() {
        taskService = new TaskServiceImpl(
                taskRepository,
                taskMapper,
                projectRepository,
                commentService,
                notificationService);
    }

    @Test
    public void getAllByProjectId_ValidProjectId_ReturnsAllTasks() {
        TaskResponseDto responseDto = new TaskResponseDto();
        responseDto.setName("Task Name");

        List<Task> tasks = List.of(new Task(), new Task());

        when(taskRepository.findAllByProjectId(1L, any(Pageable.class)))
                .thenReturn(tasks);
        when(taskMapper.toDto(any())).thenReturn(responseDto);

        List<TaskResponseDto> result = taskService.getAllByProjectId(1L, Pageable.unpaged());
        assertNotNull(result);
        assertEquals(result.size(), 1);
    }

    @Test
    public void updateTask_ValidData_ReturnsTaskResponseDto() {
        UpdateTaskRequestDto requestDto = new UpdateTaskRequestDto();
        requestDto.setName("Task Name");

        Project project = new Project();
        project.setId(1L);
        Task task = new Task();
        task.setId(1L);
        task.setName("Task Name");
        task.setProject(project);

        TaskResponseDto expected = new TaskResponseDto();
        expected.setId(1L);
        expected.setName("Task Name");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toModelUpdate(requestDto)).thenReturn(task);
        when(projectRepository.findProjectById(1L)).thenReturn(Optional.of(project));
        when(taskRepository.save(task)).thenReturn(task);
        when(taskMapper.toDto(task)).thenReturn(expected);

        TaskResponseDto actual = taskService.updateTask(1L, requestDto);
        assertNotNull(actual);
        assertEquals(actual.getName(), expected.getName());
    }

    @Test
    public void getById_ValidId_ReturnsTaskResponseDto() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Task Name");

        TaskResponseDto responseDto = new TaskResponseDto();
        responseDto.setId(1L);
        responseDto.setName("Task Name");

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(taskMapper.toDto(task)).thenReturn(responseDto);

        TaskResponseDto result = taskService.getById(1L);
        assertNotNull(result);
        assertEquals(result.getName(), responseDto.getName());
    }

    @Test
    public void delete_ValidId_Success() {
        Task task = new Task();
        task.setId(1L);
        task.setName("Task Name");
        task.setComments(Set.of());

        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));

        taskService.delete(1L);
        verify(taskRepository, times(1)).delete(task);
    }
}
