package app.service.comment;

import app.dto.comment.CommentRequestDto;
import app.dto.comment.CommentResponseDto;
import app.exception.EntityNotFoundException;
import app.mapper.CommentMapper;
import app.model.Comment;
import app.model.Task;
import app.model.User;
import app.repository.comment.CommentRepository;
import app.repository.task.TaskRepository;
import app.repository.user.UserRepository;
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
    public List<CommentResponseDto> findByUserId(Long userId, Pageable pageable) {
        return commentRepository.findAllByUserId(userId, pageable)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public List<CommentResponseDto> findByTaskId(Long taskId, Pageable pageable) {
        return commentRepository.findAllByTaskId(taskId, pageable)
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
