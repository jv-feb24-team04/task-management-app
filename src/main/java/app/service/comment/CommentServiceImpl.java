package app.service.comment;

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
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class CommentServiceImpl implements CommentService {
    private final CommentRepository commentRepository;
    private final CommentMapper commentMapper;
    private final UserRepository userRepository;
    private final TaskRepository taskRepository;

    @Override
    public CommentResponseDto save(
            CommentRequestDto commentRequestDto,
            Long userId,
            Long taskId
    ) {
        User user = userRepository.findById(userId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can not find user by id: " + userId));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Can not find task by id: " + taskId));

        Comment comment = commentMapper.toEntity(commentRequestDto);
        comment.setUser(user);
        comment.setTask(task);
        comment.setTimeStamp(LocalDateTime.now());
        comment.setLastEdit(LocalDateTime.now());

        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public List<CommentResponseDto> getByUserId(Long userId, Pageable pageable) {
        if (!userRepository.existsById(userId)) {
            throw new EntityNotFoundException("Can not find user with ID: " + userId);
        }
        return commentRepository.getAllByUserId(userId, pageable)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public List<CommentResponseDto> getByTaskId(Long taskId, Pageable pageable) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Can not find task with ID: " + taskId);
        }
        return commentRepository.getAllByTaskId(taskId, pageable)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public CommentResponseDto update(Long commentId, CommentRequestDto commentRequestDto) {
        Comment comment = getCommentById(commentId);

        comment.setText(commentRequestDto.getText());
        comment.setLastEdit(LocalDateTime.now());

        return commentMapper.toDto(commentRepository.save(comment));
    }

    @Override
    public void delete(Long commentId) {
        Comment comment = getCommentById(commentId);
        commentRepository.delete(comment);
    }

    private Comment getCommentById(Long commentId) {
        return commentRepository.findById(commentId)
                .orElseThrow(() -> new EntityNotFoundException(
                        "Cannot find comment by id: " + commentId)
                );
    }
}
