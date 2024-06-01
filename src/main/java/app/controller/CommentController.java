package app.controller;

import app.dto.comment.CommentRequestDto;
import app.dto.comment.CommentResponseDto;
import app.service.comment.CommentService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "Comment management", description = "Endpoints for managing comments")
@RestController
@RequestMapping("/api/comments")
@RequiredArgsConstructor
public class CommentController {
    private final CommentService commentService;

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Add a new comment",
            description = "Add a new comment to a task by a user"
    )
    public CommentResponseDto save(
            @RequestParam Long userId,
            @RequestParam Long taskId,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        return commentService.save(commentRequestDto, userId, taskId);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Retrieve comments by task ID",
            description = "Retrieve comments associated with a specific task"
    )
    public List<CommentResponseDto> getCommentsByTaskId(
            @RequestParam Long taskId,
            Pageable pageable
    ) {
        return commentService.findByTaskId(taskId, pageable);
    }

    @GetMapping("/user/{userId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Retrieve comments by user ID",
            description = "Retrieve comments made by a specific user"
    )
    public List<CommentResponseDto> getCommentsByUserId(
            @PathVariable Long userId,
            Pageable pageable
    ) {
        return commentService.findByUserId(userId, pageable);
    }

    @PutMapping("/{commentId}")
    @ResponseStatus(HttpStatus.OK)
    @Operation(
            summary = "Update a comment",
            description = "Update the text of an existing comment"
    )
    public CommentResponseDto updateComment(
            @PathVariable Long commentId,
            @RequestBody @Valid CommentRequestDto commentRequestDto
    ) {
        return commentService.update(commentId, commentRequestDto);
    }

    @DeleteMapping("/{commentId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    @Operation(
            summary = "Delete a comment",
            description = "Delete an existing comment by its ID"
    )
    public void deleteComment(@PathVariable Long commentId) {
        commentService.delete(commentId);
    }
}
