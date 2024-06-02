package app.mapper;

import app.config.MapperConfig;
import app.dto.task.CreateTaskRequestDto;
import app.dto.task.TaskDtoWithoutLabelsAndComments;
import app.dto.task.TaskResponseDto;
import app.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {LabelMapper.class, CommentMapper.class})
public interface TaskMapper {
    @Mapping(target = "assignee.id", source = "assigneeId")
    Task toModel(CreateTaskRequestDto requestDto);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "assigneeId", source = "assignee.id")
    TaskDtoWithoutLabelsAndComments toDtoWithoutLabelsAndComments(Task task);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "labels", source = "labels", qualifiedByName = "setLabels")
    @Mapping(target = "comments", source = "comments", qualifiedByName = "setComments")
    TaskResponseDto toDto(Task task);
}
