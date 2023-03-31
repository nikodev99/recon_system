package who.reconsystem.app.drive;

import com.google.inject.Inject;
import who.reconsystem.app.drive.annotations.DatabaseValue;
import who.reconsystem.app.drive.annotations.MimeTypeValue;
import who.reconsystem.app.drive.annotations.ParentFolderValue;

import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Objects;

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
                getLocalePath("") + databaseFileName,
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

    private String getLocalePath() {
        return getLocalePath(databaseFileName);
    }

    private String getLocalePath(String fileName) {
        return Objects.requireNonNull(DBFile.class.getClassLoader().getResource(fileName)).getFile();
    }
}
