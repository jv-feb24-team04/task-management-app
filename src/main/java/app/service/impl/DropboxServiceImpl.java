package app.service.impl;

import app.service.DropboxService;
import com.dropbox.core.DbxRequestConfig;
import com.dropbox.core.v2.DbxClientV2;
import com.dropbox.core.v2.files.FileMetadata;
import com.dropbox.core.v2.files.GetTemporaryLinkResult;
import com.dropbox.core.v2.files.WriteMode;
import jakarta.annotation.PostConstruct;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Path;
import java.nio.file.Paths;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class DropboxServiceImpl implements DropboxService {
    private DbxClientV2 client;
    @Value("${dropbox.token}")
    private String dropboxToken;

    @PostConstruct
    public void init() {
        DbxRequestConfig config = DbxRequestConfig.newBuilder("dropbox/java-tutorial").build();
        client = new DbxClientV2(config, dropboxToken);
    }

    @Override
    public String uploadFile(String filePath, Long taskId) {
        try (InputStream in = new FileInputStream(filePath)) {
            FileMetadata metadata = client.files()
                    .uploadBuilder("/folder"
                            + taskId + "/"
                            + getFileName(filePath))
                    .withMode(WriteMode.ADD)
                    .uploadAndFinish(in);
            return metadata.getId();
        } catch (Exception e) {
            throw new RuntimeException("Can't upload file by this path: "
            + filePath, e);
        }
    }

    @Override
    public void createFolder(Long taskId) {
        try {
            client.files().createFolderV2("/folder" + taskId);
        } catch (Exception e) {
            throw new RuntimeException("Can't create folder for task: "
                    + taskId);
        }
    }

    @Override
    public String getFilePublicLink(String fileId) {
        try {
            GetTemporaryLinkResult result = client.files().getTemporaryLink(fileId);
            return result.getLink();
        } catch (Exception e) {
            throw new RuntimeException("Can't get link by id: " + fileId);
        }
    }

    @Override
    public void downloadFile(String path, String fileId) {
        try (OutputStream outputStream = new FileOutputStream(path)) {
            client.files().download(fileId).download(outputStream);
        } catch (Exception e) {
            throw new RuntimeException("Can't download file by id: " + fileId);
        }
    }

    @Override
    public void deleteById(String fileId) {
        try {
            client.files().deleteV2(fileId);
        } catch (Exception e) {
            throw new RuntimeException("Can't delete file by id:" + fileId);
        }
    }

    @Override
    public void deleteAllByTaskId(Long taskId) {
        try {
            client.files().deleteV2("/folder" + taskId);
        } catch (Exception e) {
            throw new RuntimeException("Can't delete all files by task id: " + taskId);
        }
    }

    private String getFileName(String filePath) {
        Path path = Paths.get(filePath);
        return path.getFileName().toString();
    }
}
