package app.repository;

import app.model.Attachment;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AttachmentRepository extends JpaRepository<Attachment, Long> {

    List<Attachment> findAllByTaskId(Long taskId);

    boolean existsByTaskId(Long taskId);

    boolean existsByTaskIdAndFileName(Long taskId, String fileName);

    void deleteAllByTaskId(Long taskId);
}
