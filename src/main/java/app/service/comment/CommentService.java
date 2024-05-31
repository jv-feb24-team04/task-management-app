package app.service.comment;

import app.dto.comment.CommentRequestDto;
import app.dto.comment.CommentResponseDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface CommentService {
    CommentResponseDto save(CommentRequestDto commentRequestDto, Long userId, Long taskId);

    List<CommentResponseDto> findByUserId(Long userId, Pageable pageable);

    List<CommentResponseDto> findByTaskId(Long taskId, Pageable pageable);

    CommentResponseDto update(Long commentId, CommentRequestDto commentRequestDto);

    void delete(Long commentId);
}
