package app.service.impl;

import app.dto.attachment.AttachmentResponseDto;
import app.exception.EntityNotFoundException;
import app.exception.NotUniqueValueException;
import app.mapper.AttachmentMapper;
import app.model.Attachment;
import app.repository.AttachmentRepository;
import app.repository.TaskRepository;
import app.service.AttachmentService;
import app.service.DropboxService;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AttachmentServiceImpl implements AttachmentService {
    private final AttachmentRepository attachmentRepository;
    private final TaskRepository taskRepository;
    private final AttachmentMapper attachmentMapper;
    private final DropboxService dropboxService;

    @Override
    public AttachmentResponseDto create(String filePath, Long taskId) {
        String fileName = getFileName(filePath);
        if (attachmentRepository.existsByTaskIdAndFileName(taskId, fileName)) {
            throw new NotUniqueValueException("File name must be unique");
        }
        createFolderIfNotExists(taskId);
        Attachment attachment = saveNewAttachment(filePath, taskId);
        AttachmentResponseDto responseDto = attachmentMapper.toDto(attachment);
        responseDto.setFilePublicLink(getFilePublicLink(attachment.getDropboxFileId()));
        return responseDto;
    }

    @Override
    public AttachmentResponseDto getById(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(()
                -> new EntityNotFoundException("Can't find attachment by id: " + attachmentId));
        return mapToDtoAndSetFileLink(attachment);
    }

    @Override
    public List<AttachmentResponseDto> getAllByTaskId(Long taskId) {
        if (!attachmentRepository.existsByTaskId(taskId)) {
            throw new EntityNotFoundException("Can't find any attachment by task id: " + taskId);
        }
        return attachmentRepository.findAllByTaskId(taskId).stream()
                .map(this::mapToDtoAndSetFileLink)
                .toList();
    }

    @Override
    public void deleteById(Long attachmentId) {
        String dropBoxFileId = getDropBoxFileId(attachmentId);
        dropboxService.deleteById(dropBoxFileId);
        attachmentRepository.deleteById(attachmentId);
    }

    @Override
    public void deleteAllByTaskId(Long taskId) {
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

    private void createFolderIfNotExists(Long taskId) {
        if (!taskRepository.existsById(taskId)) {
            throw new EntityNotFoundException("Can't find task by id: " + taskId);
        }
        if (!attachmentRepository.existsByTaskId(taskId)) {
            dropboxService.createFolder(taskId);
        }
    }

    private String getDropBoxFileId(Long attachmentId) {
        Attachment attachment = attachmentRepository.findById(attachmentId).orElseThrow(()
                -> new EntityNotFoundException("Can't find attachment by id: " + attachmentId));
        return attachment.getDropboxFileId();
    }

    private Attachment saveNewAttachment(String filePath, Long taskId) {
        Attachment attachment = new Attachment();
        attachment.setTask(taskRepository.findById(taskId).orElseThrow(()
                        -> new EntityNotFoundException("Can't find task by id: " + taskId)));
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
}
