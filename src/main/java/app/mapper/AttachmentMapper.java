package app.mapper;

import app.config.MapperConfig;
import app.dto.attachment.AttachmentResponseDto;
import app.model.Attachment;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class)
public interface AttachmentMapper {

    @Mapping(target = "taskId", source = "task.id")
    @Mapping(target = "taskName", source = "task.name")
    @Mapping(target = "filePublicLink", ignore = true)
    AttachmentResponseDto toDto(Attachment attachment);
}
