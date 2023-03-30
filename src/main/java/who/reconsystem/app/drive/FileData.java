package who.reconsystem.app.drive;

import com.google.api.services.drive.Drive;

public interface FileData {

    String updateFile(String remoteFileName, String localeFilePath, String parentFolder);

    String uploadFile(String remoteFileName, String localeFilePath, String folderId, String mimeType);

    GoogleDriveFileFields getFileInfo(String fileName, String folderId);

    void downloadFile(String remoteFileName, String localePath, String folderId);
}
