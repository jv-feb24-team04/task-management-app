package app.service.attachment;

import app.dto.attachment.AttachmentResponseDto;
import java.util.List;

public interface AttachmentService {

    AttachmentResponseDto create(String filePath, Long taskId, Long userId);

    AttachmentResponseDto getById(Long attachmentId, Long userId);

    List<AttachmentResponseDto> getAllByTaskId(Long taskId, Long userId);

    void deleteById(Long attachmentId, Long userId);

    void deleteAllByTaskId(Long taskId, Long userId);
}
