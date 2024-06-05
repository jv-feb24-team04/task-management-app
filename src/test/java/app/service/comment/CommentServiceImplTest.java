package app.service.comment;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import app.dto.comment.CommentRequestDto;
import app.dto.comment.CommentResponseDto;
import app.exception.EntityNotFoundException;
import app.mapper.CommentMapper;
import app.model.Comment;
import app.model.Task;
import app.model.User;
import app.repository.CommentRepository;
import app.repository.TaskRepository;
import app.repository.UserRepository;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

@ExtendWith(MockitoExtension.class)
class CommentServiceImplTest {
    @Mock
    private CommentRepository commentRepository;
    @Mock
    private CommentMapper commentMapper;
    @Mock
    private UserRepository userRepository;
    @Mock
    private TaskRepository taskRepository;

    private CommentServiceImpl commentService;

    @BeforeEach
    void setUp() {
        commentService = new CommentServiceImpl(
                commentRepository,
                commentMapper,
                userRepository,
                taskRepository
        );
    }

    @Test
    void save_ValidRequest_ReturnsCommentResponseDto() {
        CommentRequestDto requestDto = new CommentRequestDto();
        User user = new User();
        Task task = new Task();
        Comment comment = new Comment();
        CommentResponseDto responseDto = new CommentResponseDto();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.findById(1L)).thenReturn(Optional.of(task));
        when(commentMapper.toEntity(requestDto)).thenReturn(comment);
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(responseDto);

        CommentResponseDto result = commentService.save(requestDto, 1L, 1L);

        assertNotNull(result);
        assertEquals(responseDto, result);
    }

    @Test
    void save_UserNotFound_ThrowsException() {
        CommentRequestDto requestDto = new CommentRequestDto();

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> commentService.save(requestDto, 1L, 1L));
    }

    @Test
    void save_TaskNotFound_ThrowsException() {
        CommentRequestDto requestDto = new CommentRequestDto();
        User user = new User();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(taskRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> commentService.save(requestDto, 1L, 1L));
    }

    @Test
    void getByUserId_ValidUserId_ReturnsListOfCommentResponseDto() {
        Comment comment = new Comment();
        CommentResponseDto responseDto = new CommentResponseDto();
        Pageable pageable = PageRequest.of(0, 10);
        when(userRepository.existsById(1L)).thenReturn(true);
        when(commentRepository.getAllByUserId(1L, pageable)).thenReturn(List.of(comment));
        when(commentMapper.toDto(comment)).thenReturn(responseDto);

        List<CommentResponseDto> result = commentService.getByUserId(1L, pageable);

        assertNotNull(result);
        assertEquals(List.of(responseDto), result);
    }

    @Test
    void getByUserId_UserNotFound_ThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);

        when(userRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> commentService.getByUserId(1L, pageable));
    }

    @Test
    void getByTaskId_ValidTaskId_ReturnsListOfCommentResponseDto() {
        Comment comment = new Comment();
        CommentResponseDto responseDto = new CommentResponseDto();
        Pageable pageable = PageRequest.of(0, 10);
        when(taskRepository.existsById(1L)).thenReturn(true);
        when(commentRepository.getAllByTaskId(1L, pageable)).thenReturn(List.of(comment));
        when(commentMapper.toDto(comment)).thenReturn(responseDto);

        List<CommentResponseDto> result = commentService.getByTaskId(1L, pageable);

        assertNotNull(result);
        assertEquals(List.of(responseDto), result);
    }

    @Test
    void getByTaskId_TaskNotFound_ThrowsException() {
        Pageable pageable = PageRequest.of(0, 10);

        when(taskRepository.existsById(1L)).thenReturn(false);

        assertThrows(EntityNotFoundException.class,
                () -> commentService.getByTaskId(1L, pageable));
    }

    @Test
    void update_ValidData_ReturnsCommentResponseDto() {
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setText("Updated Comment");
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setText("Original Comment");
        CommentResponseDto responseDto = new CommentResponseDto();
        responseDto.setText("Updated Comment");

        when(commentRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(comment));
        when(commentRepository.save(comment)).thenReturn(comment);
        when(commentMapper.toDto(comment)).thenReturn(responseDto);

        CommentResponseDto result = commentService.update(1L, requestDto, 1L);

        assertNotNull(result);
        assertEquals(responseDto.getText(), result.getText());
    }

    @Test
    void update_CommentNotFound_ThrowsException() {
        CommentRequestDto requestDto = new CommentRequestDto();
        requestDto.setText("Updated Comment");

        when(commentRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class,
                () -> commentService.update(1L, requestDto, 1L));
    }

    @Test
    void delete_ValidId_Success() {
        Comment comment = new Comment();
        comment.setId(1L);
        comment.setUser(new User());

        when(commentRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.of(comment));

        commentService.delete(1L, 1L);

        verify(commentRepository, times(1)).delete(comment);
    }

    @Test
    void delete_CommentNotFound_ThrowsException() {
        when(commentRepository.findByIdAndUserId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(EntityNotFoundException.class, () -> commentService.delete(1L, 1L));
    }
}
