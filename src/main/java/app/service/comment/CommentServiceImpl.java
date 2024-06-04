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
import java.util.Optional;
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
                        () -> new EntityNotFoundException("Failed to find User by id=" + userId));

        Task task = taskRepository.findById(taskId)
                .orElseThrow(
                        () -> new EntityNotFoundException("Failed to find Task by id=" + taskId));

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
            throw new EntityNotFoundException("Failed to find User by id=" + userId);
        }
        return commentRepository.getAllByUserId(userId, pageable)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public List<CommentResponseDto> getByTaskId(Long taskId, Pageable pageable) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Failed to find Task by id=" + taskId);
        }
        return commentRepository.getAllByTaskId(taskId, pageable)
                .stream()
                .map(commentMapper::toDto)
                .toList();
    }

    @Override
    public CommentResponseDto update(
            Long commentId,
            CommentRequestDto commentRequestDto,
            Long userId
    ) {
        Optional<Comment> commentOptional = getUserComment(commentId, userId);

        if (commentOptional.isPresent()) {
            Comment comment = commentOptional.get();
            comment.setText(commentRequestDto.getText());
            comment.setLastEdit(LocalDateTime.now());

            return commentMapper.toDto(commentRepository.save(comment));
        } else {
            throw new RuntimeException("Comment not found for this user");
        }
    }

    @Override
    public void delete(Long commentId, Long userId) {
        Optional<Comment> commentOptional = getUserComment(commentId, userId);

        if (commentOptional.isPresent()) {
            commentRepository.delete(commentOptional.get());
        } else {
            throw new RuntimeException("Comment not found for this user");
        }
    }

    private Optional<Comment> getUserComment(Long commentId, Long userId) {
        return commentRepository.findByIdAndUserId(commentId, userId);
    }
}
