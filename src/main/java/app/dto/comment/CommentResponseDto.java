package app.dto.comment;

import java.time.LocalDateTime;
import lombok.Data;

@Data
public class CommentResponseDto {
    private Long id;
    private Long taskId;
    private Long userId;
    private String text;
    private LocalDateTime timeStamp;
    private LocalDateTime lastEdit;
}
