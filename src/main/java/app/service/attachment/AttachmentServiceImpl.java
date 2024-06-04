package app.service.attachment;

import app.dto.attachment.AttachmentResponseDto;
import app.exception.EntityNotFoundException;
import app.exception.NotUniqueValueException;
import app.mapper.AttachmentMapper;
import app.model.Attachment;
import app.model.Task;
import app.repository.AttachmentRepository;
import app.service.dropbox.DropboxService;
import app.service.task.TaskService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final TaskService taskService;
    private final AttachmentMapper attachmentMapper;
    private final DropboxService dropboxService;

    @Override
    public AttachmentResponseDto create(String filePath, Long taskId, Long userId) {
        checkTaskOwnership(taskId, userId);
        String fileName = getFileName(filePath);
        if (attachmentRepository.existsByTaskIdAndFileName(taskId, fileName)) {
            throw new NotUniqueValueException("File name must be unique");
        }
        Attachment attachment = saveNewAttachment(filePath, taskId);
        AttachmentResponseDto responseDto = attachmentMapper.toDto(attachment);
        responseDto.setFilePublicLink(getFilePublicLink(attachment.getDropboxFileId()));
        return responseDto;
    }

    @Override
    public AttachmentResponseDto getById(Long attachmentId, Long userId) {
        Attachment attachment = getAttachment(attachmentId);
        checkTaskOwnership(attachment.getTask().getId(), userId);
        return mapToDtoAndSetFileLink(attachment);
    }

    @Override
    public List<AttachmentResponseDto> getAllByTaskId(Long taskId, Long userId) {
        checkTaskOwnership(taskId, userId);
        if (attachmentRepository.existsByTaskId(taskId)) {
            return attachmentRepository.findAllByTaskId(taskId).stream()
                    .map(this::mapToDtoAndSetFileLink)
                    .toList();
        }
        throw new EntityNotFoundException("Failed to find Attachment by Task id="
                + taskId);
    }

    @Override
    public void deleteById(Long attachmentId, Long userId) {
        Long taskId = getAttachment(attachmentId).getTask().getId();
        checkTaskOwnership(taskId, userId);
        String dropBoxFileId = getDropBoxFileId(attachmentId);
        dropboxService.deleteById(dropBoxFileId);
        attachmentRepository.deleteById(attachmentId);
    }

    @Override
    @Transactional
    public void deleteAllByTaskId(Long taskId, Long userId) {
        checkTaskOwnership(taskId, userId);
        dropboxService.deleteAllByTaskId(taskId);
        attachmentRepository.deleteAllByTaskId(taskId);
    }

    private String getFilePublicLink(String dropBoxFileId) {
        return dropboxService.getFilePublicLink(dropBoxFileId);
    }

    private String getFileName(String filePath) {
        Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }

    private String getDropBoxFileId(Long attachmentId) {
        Attachment attachment = getAttachment(attachmentId);
        return attachment.getDropboxFileId();
    }

    private Attachment saveNewAttachment(String filePath, Long taskId) {
        Attachment attachment = new Attachment();
        attachment.setTask(getTask(taskId));
        attachment.setDropboxFileId(dropboxService.uploadFile(filePath, taskId));
        attachment.setFileName(getFileName(filePath));
        attachment.setUploadDate(LocalDateTime.now());
        return attachmentRepository.save(attachment);
    }

    private AttachmentResponseDto mapToDtoAndSetFileLink(Attachment attachment) {
        AttachmentResponseDto responseDto = attachmentMapper.toDto(attachment);
        responseDto.setFilePublicLink(getFilePublicLink(attachment.getDropboxFileId()));
        return responseDto;
    }

    private void checkTaskOwnership(Long taskId, Long userId) {
        Task task = getTask(taskId);
        if (!task.getAssignee().getId().equals(userId)) {
            throw new EntityNotFoundException("Failed to find Task by id="
                    + taskId);
        }
    }

    private Attachment getAttachment(Long attachmentId) {
        return attachmentRepository.findById(attachmentId).orElseThrow(()
                -> new EntityNotFoundException("Failed to find Attachment by id="
                + attachmentId));
    }

    private Task getTask(Long taskId) {
        return taskService.getTaskById(taskId).orElseThrow(()
                -> new EntityNotFoundException("Failed to find Task by id="
                + taskId));
    }
}
