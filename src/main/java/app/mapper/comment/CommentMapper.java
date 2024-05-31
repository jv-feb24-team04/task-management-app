package app.mapper.comment;

import app.config.MapperConfig;
import app.dto.comment.CommentResponseDto;
import app.model.Comment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface CommentMapper {
    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "userId", source = "user.id")
    CommentResponseDto toDto(Comment comment);
}
