package app.service.dropbox;

public interface DropboxService {

    String uploadFile(String filePath, Long taskId);

    String getFilePublicLink(String fileId);

    void deleteById(String fileId);

    void deleteAllByTaskId(Long taskId);
}
