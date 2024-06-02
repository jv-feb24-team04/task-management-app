package app.service.attachment;

import app.dto.attachment.AttachmentResponseDto;
import java.util.List;

public interface AttachmentService {

    AttachmentResponseDto create(String filePath, Long taskId);

    AttachmentResponseDto getById(Long attachmentId);

    List<AttachmentResponseDto> getAllByTaskId(Long taskId);

    void deleteById(Long attachmentId);

    void deleteAllByTaskId(Long taskId);
}
