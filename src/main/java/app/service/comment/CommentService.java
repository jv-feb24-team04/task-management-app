package app.service.comment;

import app.dto.comment.CommentRequestDto;
import app.dto.comment.CommentResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentResponseDto save(CommentRequestDto commentRequestDto, Long userId, Long taskId);

    List<CommentResponseDto> getByUserId(Long userId, Pageable pageable);

    List<CommentResponseDto> getByTaskId(Long taskId, Pageable pageable);

    CommentResponseDto update(Long commentId, CommentRequestDto commentRequestDto, Long userId);

    void delete(Long commentId, Long userId);
}
