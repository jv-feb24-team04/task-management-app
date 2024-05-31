package app.repository;

import app.model.Label;
import java.util.Set;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface LabelRepository extends JpaRepository<Label, Long> {
    @Query("SELECT l FROM Label l JOIN FETCH l.tasks t WHERE t.id = :taskId")
    Set<Label> findAllByTaskId(@Param("taskId") Long taskId);

    @Query("SELECT DISTINCT l FROM Label l JOIN FETCH l.tasks t JOIN FETCH t.project p WHERE p.id = :projectId")
    Set<Label> findAllByProjectId(@Param("projectId") Long projectId);
}
