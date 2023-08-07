package who.reconsystem.app.drive;

import com.google.inject.Inject;
import org.jetbrains.annotations.NotNull;
import who.reconsystem.app.drive.annotations.DatabaseValue;
import who.reconsystem.app.drive.annotations.MimeTypeValue;
import who.reconsystem.app.drive.annotations.ParentFolderValue;
import who.reconsystem.app.root.config.Functions;

public class DBFile {

    private final FileData file;
    private final String parentFolderId;
    private final String databaseFileName;
    private final String mimeType;

    @Inject
    public DBFile(
            FileData file,
            @ParentFolderValue String parentFolderId,
            @DatabaseValue String databaseFileName,
            @MimeTypeValue String mimeType
    ) {
        this.file = file;
        this.parentFolderId = parentFolderId;
        this.databaseFileName = databaseFileName;
        this.mimeType = mimeType;
    }

    public GoogleDriveFileFields getDatabaseFromGoogleDrive() {
        return file.getFileInfo(databaseFileName, parentFolderId);
    }

    public String uploadDatabaseFile() {
        return file.uploadFile(
                databaseFileName,
                getLocalePath(),
                parentFolderId,
                mimeType
        );
    }

    public void downloadDatabaseFromGoogleDrive() {
        file.downloadFile(
                databaseFileName,
                getLocalePath(),
                parentFolderId
        );
    }

    public String updateDatabaseFile() {
        return file.updateFile(
                databaseFileName,
                getLocalePath(),
                parentFolderId
        );
    }

    private @NotNull String getLocalePath() {
        return Functions.getLocalePath(databaseFileName);
    }
}
