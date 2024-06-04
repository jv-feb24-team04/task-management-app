package app.controller;

import app.dto.attachment.AttachmentResponseDto;
import app.model.User;
import app.service.attachment.AttachmentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Attachment management", description = "Endpoints for managing attachments")
@RestController
@RequestMapping("/api/attachments")
@RequiredArgsConstructor
public class AttachmentController {
    private final AttachmentService attachmentService;

    @PreAuthorize("hasRole('ROLE_USER')")
    @ResponseStatus(HttpStatus.CREATED)
    @PostMapping(consumes = "multipart/form-data")
    @Operation(summary = "Upload Attachment",
            description = "Create a new attachment for a task identified by its ID")
    public AttachmentResponseDto createAttachment(@RequestParam("filePath") String filePath,
                                                  @RequestParam("taskId") Long taskId,
                                                  Authentication authentication) {
        return attachmentService.create(filePath, taskId, getUserId(authentication));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}")
    @Operation(summary = "Get attachment by ID",
            description = "Get all information about the attachment by ID")
    public AttachmentResponseDto getAttachmentById(@PathVariable Long id,
                                                   Authentication authentication) {
        return attachmentService.getById(id, getUserId(authentication));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @GetMapping("/{id}/all")
    @Operation(summary = "Retrieve All Attachments",
            description = "Get all attachments associated with a task identified by its ID")
    public List<AttachmentResponseDto> getAllByTaskId(@PathVariable Long id,
                                                      Authentication authentication) {
        return attachmentService.getAllByTaskId(id, getUserId(authentication));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete Attachment",
            description = "Delete an attachment by its unique identifier")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAttachmentById(@PathVariable Long id,
                                     Authentication authentication) {
        attachmentService.deleteById(id, getUserId(authentication));
    }

    @PreAuthorize("hasRole('ROLE_USER')")
    @DeleteMapping("/{id}/all")
    @Operation(
            summary = "Delete All Attachments",
            description = "Delete all attachments associated with a task identified by its ID"
    )
    public void deleteAllAttachmentsByTaskId(@PathVariable Long id,
                                             Authentication authentication) {
        attachmentService.deleteAllByTaskId(id, getUserId(authentication));
    }

    private Long getUserId(Authentication authentication) {
        User user = (User) authentication.getPrincipal();
        return user.getId();
    }
}
