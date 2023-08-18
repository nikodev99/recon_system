package who.reconsystem.app.drive;

import com.google.api.client.util.DateTime;
import lombok.Getter;

@Getter
public class GoogleDriveFileFields {

    private String fileId;

    private String fileName;

    private String  fileMimeType;

    private DateTime fileCreationOn;

    private DateTime fileModifyOn;

    private long fileSize;

    public GoogleDriveFileFields(String fileId, String fileName, String fileMimeType, DateTime fileCreationOn, DateTime fileModifyOn, long fileSize) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileMimeType = fileMimeType;
        this.fileCreationOn = fileCreationOn;
        this.fileModifyOn = fileModifyOn;
        this.fileSize = fileSize;
    }

    public GoogleDriveFileFields() {
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public void setFileMimeType(String fileMimeType) {
        this.fileMimeType = fileMimeType;
    }

    public void setFileCreationOn(DateTime fileCreationOn) {
        this.fileCreationOn = fileCreationOn;
    }

    public void setFileModifyOn(DateTime fileModifyOn) {
        this.fileModifyOn = fileModifyOn;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
