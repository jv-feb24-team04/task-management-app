package app.service;

import app.dto.ProjectRequestDto;
import app.dto.ProjectResponseDto;
import app.dto.ProjectStatusDto;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface ProjectService {
    ProjectResponseDto saveProject(ProjectRequestDto projectDto);

    List<ProjectResponseDto> getAllProjects(Pageable pageable);

    ProjectResponseDto getProjectDetailsById(Long id);

    ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectDto);

    void updateProjectStatus(Long id, ProjectStatusDto statusDto);
}
