package app.service.project;

import app.dto.project.ProjectRequestDto;
import app.dto.project.ProjectResponseDto;
import app.dto.project.ProjectStatusDto;
import app.model.User;
import java.util.List;
import org.springframework.data.domain.Pageable;

public interface ProjectService {
    ProjectResponseDto saveProject(ProjectRequestDto projectDto);

    List<ProjectResponseDto> getAllProjects(Pageable pageable, User user);

    ProjectResponseDto getProjectDetailsById(Long id, User user);

    ProjectResponseDto updateProject(Long projectId, ProjectRequestDto projectDto, User user);

    void updateProjectStatus(Long id, ProjectStatusDto statusDto, User user);

    void deleteProject(Long id, User user);
}
