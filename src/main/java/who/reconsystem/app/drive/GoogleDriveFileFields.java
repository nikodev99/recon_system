package who.reconsystem.app.drive;

public class GoogleDriveFileFields {

    private String fileId;

    private String fileName;

    private String  fileMimeType;

    private String fileCreationOn;

    private String fileModifyOn;

    private String fileSize;

    public GoogleDriveFileFields(String fileId, String fileName, String fileMimeType, String fileCreationOn, String fileModifyOn, String fileSize) {
        this.fileId = fileId;
        this.fileName = fileName;
        this.fileMimeType = fileMimeType;
        this.fileCreationOn = fileCreationOn;
        this.fileModifyOn = fileModifyOn;
        this.fileSize = fileSize;
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

    public String getFileCreationOn() {
        return fileCreationOn;
    }

    public void setFileCreationOn(String fileCreationOn) {
        this.fileCreationOn = fileCreationOn;
    }

    public String getFileModifyOn() {
        return fileModifyOn;
    }

    public void setFileModifyOn(String fileModifyOn) {
        this.fileModifyOn = fileModifyOn;
    }

    public String getFileSize() {
        return fileSize;
    }

    public void setFileSize(String fileSize) {
        this.fileSize = fileSize;
    }
}
