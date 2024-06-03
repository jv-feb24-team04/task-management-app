package app.repository;

import app.model.Task;
import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.EntityGraph;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    @EntityGraph(attributePaths = {"labels", "comments"})
    List<Task> findAllByProjectId(Long projectId, Pageable pageable);

    @EntityGraph(attributePaths = {"labels", "comments"})
    Optional<Task> findById(Long id);
}
