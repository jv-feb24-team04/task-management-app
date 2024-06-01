package app.mapper;

import app.config.MapperConfig;
import app.dto.comment.CommentResponseDto;
import app.model.Comment;
import java.util.Set;
import java.util.stream.Collectors;
import org.mapstruct.Mapper;
import org.mapstruct.Named;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {
    CommentResponseDto toDto(Comment comment);

    @Named("setComments")
    default Set<CommentResponseDto> setComments(Set<Comment> comments) {
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}
