package app.service.project;

import app.dto.project.ProjectRequestDto;
import app.dto.project.ProjectResponseDto;
import app.model.User;
import org.springframework.stereotype.Service;

@Service
public interface ProjectService {
    ProjectResponseDto saveProject(ProjectRequestDto requestDto, User user);
}
