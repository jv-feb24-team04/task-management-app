package app.mapper;

import app.config.MapperConfig;
import app.dto.comment.CommentRequestDto;
import app.dto.comment.CommentResponseDto;
import app.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "userId", source = "user.id")
    CommentResponseDto toDto(Comment comment);

    @Mapping(target = "id", ignore = true)
    @Mapping(target = "task", ignore = true)
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "timeStamp", ignore = true)
    @Mapping(target = "lastEdit", ignore = true)
    Comment toEntity(CommentRequestDto requestDto);
  
    @Named("setComments")
    default Set<CommentResponseDto> setComments(Set<Comment> comments) {
        return comments.stream()
                .map(this::toDto)
                .collect(Collectors.toSet());
    }
}
