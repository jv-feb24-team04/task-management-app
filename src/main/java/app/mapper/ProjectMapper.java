package app.mapper;

import app.config.MapperConfig;
import app.dto.ProjectRequestDto;
import app.dto.ProjectResponseDto;
import app.model.Project;
import app.model.ProjectStatus;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(config = MapperConfig.class,
        unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface ProjectMapper {
    @Mapping(target = "status", source = "projectStatus")
    ProjectResponseDto toDto(Project project);

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
