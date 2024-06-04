package app.mapper;

import app.config.MapperConfig;
import app.dto.task.CreateTaskRequestDto;
import app.dto.task.TaskResponseDto;
import app.dto.task.UpdateTaskRequestDto;
import app.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(config = MapperConfig.class, uses = {LabelMapper.class, CommentMapper.class})
public interface TaskMapper {
    @Mapping(target = "assignee.id", source = "assigneeId")
    Task toModelCreate(CreateTaskRequestDto requestDto);

    @Mapping(target = "assignee.id", source = "assigneeId")
    Task toModelUpdate(UpdateTaskRequestDto requestDto);

    @Mapping(target = "projectId", source = "project.id")
    @Mapping(target = "assigneeId", source = "assignee.id")
    @Mapping(target = "labels", source = "labels", qualifiedByName = "setLabels")
    @Mapping(target = "comments", source = "comments", qualifiedByName = "setComments")
    TaskResponseDto toDto(Task task);
}
