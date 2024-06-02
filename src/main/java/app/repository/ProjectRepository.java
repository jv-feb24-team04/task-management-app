package app.repository;

import app.model.Project;
import app.model.ProjectStatus;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

public interface ProjectRepository extends JpaRepository<Project, Long> {
    @Query("FROM Project p JOIN FETCH p.tasks")
    List<Project> findAllProjects(Pageable pageable);

    @EntityGraph(attributePaths = {"tasks"})
    Optional<Project> findProjectById(Long id);

    @Modifying
    @Query("UPDATE Project p SET p.projectStatus = :projectStatus WHERE p.id = :id")
    int updateProjectStatusById(Long id, ProjectStatus projectStatus);
}
