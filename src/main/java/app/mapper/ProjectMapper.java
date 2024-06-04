package app.mapper;

import app.config.MapperConfig;
import app.dto.project.ProjectRequestDto;
import app.dto.project.ProjectResponseDto;
import app.model.Project;
import app.model.ProjectStatus;
import app.model.Task;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {
    @Mapping(target = "status", source = "projectStatus")
    @Mapping(source = "tasks", target = "taskIds", qualifiedByName = "taskToId")
    ProjectResponseDto toDto(Project project);

    @Named("taskToId")
    default Long taskToId(Task task) {
        return task.getId();
    }

    default Project toModel(ProjectRequestDto requestDto) {
        Project project = new Project();
        project.setName(requestDto.name());
        project.setDescription(requestDto.description());
        project.setStartDate(requestDto.startDate());
        project.setEndDate(requestDto.endDate());
        project.setProjectStatus(ProjectStatus.INITIATED);
        return project;
    }
}
