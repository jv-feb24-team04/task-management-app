package app.controller;

import app.dto.comment.CommentRequestDto;
import app.dto.comment.CommentResponseDto;
import app.model.User;
import app.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comment management", description = "Endpoints for managing comments")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping("/{taskId}")
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add comment",
            description = "Add a new comment for a task identified by its ID and user id"
    )
    public CommentResponseDto save(
            @AuthenticationPrincipal User user,
            @PathVariable Long taskId,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        return commentService.save(commentRequestDto, user.getId(), taskId);
    }

    @GetMapping("/{taskId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Retrieve all comments by task Id",
            description = "Retrieve all comments associated with a task identified by its ID"
    )
    public List<CommentResponseDto> getCommentsByTaskId(
            @PathVariable Long taskId,
            Pageable pageable
    ) {
        return commentService.getByTaskId(taskId, pageable);
    }

    @GetMapping()
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Retrieve all comments by user ID",
            description = "Retrieve all comments associated with a user identified by its ID"
    )
    public List<CommentResponseDto> getCommentsByUserId(
            @AuthenticationPrincipal User user,
            Pageable pageable
    ) {
        return commentService.getByUserId(user.getId(), pageable);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update comment",
            description = "Update the text of an existing comment by associated"
                    + " with a task and user identified by their ID"
    )
    public CommentResponseDto updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDto commentRequestDto,
            @AuthenticationPrincipal User user
    ) {
        return commentService.update(commentId, commentRequestDto, user.getId());
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete comment",
            description = "Delete a comment by its unique identifier"
    )
    public void deleteComment(
            @AuthenticationPrincipal User user,
            @PathVariable Long commentId
    ) {
        commentService.delete(commentId, user.getId());
    }
}
