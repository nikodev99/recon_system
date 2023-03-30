package who.reconsystem.app.drive;

import com.google.api.client.util.DateTime;

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

    public String getFileId() {
        return fileId;
    }

    public void setFileId(String fileId) {
        this.fileId = fileId;
    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public String getFileMimeType() {
        return fileMimeType;
    }

    public void setFileMimeType(String fileMimeType) {
        this.fileMimeType = fileMimeType;
    }

    public DateTime getFileCreationOn() {
        return fileCreationOn;
    }

    public void setFileCreationOn(DateTime fileCreationOn) {
        this.fileCreationOn = fileCreationOn;
    }

    public DateTime getFileModifyOn() {
        return fileModifyOn;
    }

    public void setFileModifyOn(DateTime fileModifyOn) {
        this.fileModifyOn = fileModifyOn;
    }

    public long getFileSize() {
        return fileSize;
    }

    public void setFileSize(long fileSize) {
        this.fileSize = fileSize;
    }
}
