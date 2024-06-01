package app.repository.comment;

import app.model.Comment;
import java.util.List;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface CommentRepository extends JpaRepository<Comment, Long> {
    @Query("SELECT c FROM Comment c JOIN FETCH c.user WHERE c.user.id = :userId")
    List<Comment> findAllByUserId(Long userId, Pageable pageable);

    @Query("SELECT c FROM Comment c JOIN FETCH c.task WHERE c.task.id = :taskId")
    List<Comment> findAllByTaskId(Long taskId, Pageable pageable);
}
