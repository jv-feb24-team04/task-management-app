package app.repository;

import app.model.Project;
import app.model.ProjectStatus;
import app.model.User;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("FROM Project p JOIN FETCH p.tasks t WHERE t.assignee = :user")
    Page<Project> findAllProjectsByIdAndUser(Pageable pageable, User user);

    @Query("FROM Project p JOIN FETCH p.tasks t WHERE p.id = :id")
    Optional<Project> findProjectById(Long id);

    @Modifying
    @Query("UPDATE Project p SET p.projectStatus = :projectStatus WHERE p.id = :id")
    int updateProjectStatusById(Long id, ProjectStatus projectStatus);

    @Query("FROM Project p JOIN FETCH p.tasks t WHERE p.id = :projectId AND t.assignee = :user")
    Optional<Project> findProjectByIdAndUser(Long projectId, User user);
}
