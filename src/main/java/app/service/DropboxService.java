package app.service;

public interface DropboxService {

    String uploadFile(String filePath, Long taskId);

    void createFolder(Long taskId);

    String getFilePublicLink(String fileId);

    void downloadFile(String path, String fileId);

    void deleteById(String fileId);

    void deleteAllByTaskId(Long taskId);
}
