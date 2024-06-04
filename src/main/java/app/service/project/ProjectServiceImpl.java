package app.service.project;

import app.dto.project.ProjectRequestDto;
import app.dto.project.ProjectResponseDto;
import app.dto.project.ProjectStatusDto;
import app.exception.EntityNotFoundException;
import app.mapper.ProjectMapper;
import app.model.Project;
import app.model.User;
import app.repository.ProjectRepository;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProjectServiceImpl implements ProjectService {
    private static final String PROJECT_NOT_FOUND_ERROR_MESSAGE = "Failed to find Project by id=%s";

    private final ProjectMapper projectMapper;
    private final ProjectRepository projectRepository;

    @Override
    @Transactional
    public ProjectResponseDto saveProject(ProjectRequestDto projectDto) {
        Project project = projectMapper.toModel(projectDto);
        return projectMapper.toDto(projectRepository.save(project));
    }

    @Override
    public List<ProjectResponseDto> getAllProjects(Pageable pageable, User user) {
        return projectRepository.findAllProjectsByIdAndUser(pageable, user).stream()
                .map(projectMapper::toDto)
                .toList();
    }

    @Override
    public ProjectResponseDto getProjectDetailsById(Long projectId, User user) {
        return projectMapper.toDto(projectRepository.findProjectByIdAndUser(projectId, user)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(PROJECT_NOT_FOUND_ERROR_MESSAGE, projectId))));
    }

    @Override
    @Transactional
    public ProjectResponseDto updateProject(Long projectId,
                                            ProjectRequestDto projectDto,
                                            User user) {
        Project projectFromDb = projectRepository.findProjectByIdAndUser(projectId, user)
                .orElseThrow(() -> new EntityNotFoundException(
                        String.format(PROJECT_NOT_FOUND_ERROR_MESSAGE, projectId)));
        BeanUtils.copyProperties(projectDto, projectFromDb);
        return projectMapper.toDto(projectRepository.save(projectFromDb));
    }

    @Override
    @Transactional
    public void updateProjectStatus(Long id, ProjectStatusDto statusDto, User user) {
        if (projectRepository.updateProjectStatusById(id, statusDto.projectStatus()) < 1) {
            throw new EntityNotFoundException(String.format(PROJECT_NOT_FOUND_ERROR_MESSAGE, id));
        }
    }

    @Override
    public void deleteProject(Long id, User user) {
        if (projectRepository.findProjectByIdAndUser(id, user).isEmpty()) {
            throw new EntityNotFoundException(String.format(PROJECT_NOT_FOUND_ERROR_MESSAGE, id));
        }
        projectRepository.deleteById(id);
    }
}
