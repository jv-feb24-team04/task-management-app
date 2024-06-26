package app.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.hibernate.annotations.SoftDelete;

@Entity
@Table(name = "attachments")
@SoftDelete(columnName = "is_deleted")
@Getter
@Setter
@ToString
@EqualsAndHashCode
public class Attachment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    @JoinColumn(name = "task_id", nullable = false)
    private Task task;
    @Column(nullable = false)
    private String dropboxFileId;
    @Column(nullable = false)
    private String fileName;
    @Column(nullable = false)
    private LocalDateTime uploadDate;
}
