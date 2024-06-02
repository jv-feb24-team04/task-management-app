package app.service;

import app.dto.ProjectRequestDto;
import app.dto.ProjectResponseDto;
import app.dto.ProjectStatusDto;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectResponseDto saveProject(ProjectRequestDto projectDto);

    List<ProjectResponseDto> getAllProjects(Pageable pageable);

    ProjectResponseDto getProjectDetailsById(Long id);

    ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectDto);

    void updateProjectStatus(Long id, ProjectStatusDto statusDto);
}
