package app.dto.comment;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

@Data
public class CommentRequestDto {
    @NotBlank(message = "Text can not be null")
    @Size(max = 255, message = "Text length can not be longer than 255 characters")
    private String text;
}
