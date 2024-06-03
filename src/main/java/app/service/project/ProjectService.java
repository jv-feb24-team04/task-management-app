package app.service.project;

import app.dto.project.ProjectRequestDto;
import app.dto.project.ProjectResponseDto;
import app.dto.project.ProjectStatusDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectResponseDto saveProject(ProjectRequestDto projectDto);

    List<ProjectResponseDto> getAllProjects(Pageable pageable);

    ProjectResponseDto getProjectDetailsById(Long id);

    ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectDto);

    void updateProjectStatus(Long id, ProjectStatusDto statusDto);
}
