package app.controller;

import app.dto.attachment.AttachmentResponseDto;
import app.service.attachment.AttachmentService;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "multipart/form-data")
    public AttachmentResponseDto createAttachment(@RequestParam("filePath") String filePath,
                                                  @RequestParam("taskId") Long taskId) {
        return attachmentService.create(filePath, taskId);
    }

    @GetMapping("/{id}")
    public AttachmentResponseDto getAttachmentById(@PathVariable Long id) {
        return attachmentService.getById(id);
    }

    @GetMapping("/{id}/all")
    public List<AttachmentResponseDto> getAllByTaskId(@PathVariable Long id) {
        return attachmentService.getAllByTaskId(id);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttachmentById(@PathVariable Long id) {
        attachmentService.deleteById(id);
    }

    @DeleteMapping("/{id}/all")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAllAttachmentsByTaskId(@PathVariable Long id) {
        attachmentService.deleteAllByTaskId(id);
    }
}
