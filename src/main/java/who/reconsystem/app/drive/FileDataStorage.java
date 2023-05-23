package who.reconsystem.app.drive;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.inject.Inject;
import who.reconsystem.app.dialog.DialogMessage;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class FileDataStorage implements FileData {

    private GoogleDriveFileFields fields;

    private final GoogleDriveApiCredentials credentials;

    @Inject
    public FileDataStorage(
            GoogleDriveFileFields fields,
            GoogleDriveApiCredentials credentials
    ) {
        this.fields = fields;
        this.credentials = credentials;
    }

    @Override
    public String updateFile(String remoteFileName, String localeFilePath, String parentFolder) {
        String fileId = "";
        try {
            Drive drive = credentials.driveService();
            GoogleDriveFileFields remoteFileFields = getFile(drive, remoteFileName, parentFolder);
            File fileName = new File();
            fileName.setName(remoteFileFields.getFileName());
            java.io.File localFileContent = new java.io.File(localeFilePath);
            FileContent content = new FileContent(remoteFileFields.getFileMimeType(), localFileContent);
            try {
                Drive.Files.Update updatedFile = drive.files().update(remoteFileFields.getFileId(), fileName, content)
                        .setFields("id");
                if (parentFolder != null) updatedFile.setAddParents(parentFolder);
                File updated = updatedFile.execute();
                fileId = updated.getId();
                System.out.println("Update success");
            } catch (IOException e) {
                System.out.println("Unable to update: " + e.getMessage());
            }
        }catch (IOException | GeneralSecurityException g) {
            System.out.println("Error encounter " + Arrays.toString(g.getStackTrace()));
        }
        return fileId;
    }

    @Override
    public void downloadFile(String remoteFileName, String localePath, String folderId) {
        try {
            Drive drive = credentials.driveService();
            String remoteFileId = getFile(drive, remoteFileName, folderId).getFileId();
            ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
            drive.files().get(remoteFileId).executeMediaAndDownloadTo(outputStream);
            FileOutputStream fos = new FileOutputStream(localePath);
            outputStream.writeTo(fos);
            outputStream.close();
            fos.close();
            System.out.println("Successfully downloaded through " + localePath);
        }catch (IOException | GeneralSecurityException g) {
            DialogMessage.exceptionDialog(g);
        }
    }

    @Override
    public String uploadFile(String remoteFileName, String localeFilePath, String folderId, String mimeType) {
        String fileId = "";
        try {
            Drive drive = credentials.driveService();
            File file = new File();
            file.setName(remoteFileName);
            if(folderId != null) file.setParents(Collections.singletonList(folderId));
            java.io.File fileData = new java.io.File(localeFilePath);
            FileContent content = new FileContent(mimeType, fileData);
            try {
                File uploadedFile = drive.files().create(file, content)
                        .setFields("id")
                        .execute();
                fileId = uploadedFile.getId();
            }catch (GoogleJsonResponseException e) {
                DialogMessage.exceptionDialog(e);
            }
        }catch (IOException | GeneralSecurityException g) {
            DialogMessage.exceptionDialog(g);
        }
        return fileId;
    }

    @Override
    public GoogleDriveFileFields getFileInfo(String fileName, String folderId) {
        try {
            getFile(credentials.driveService(), fileName, folderId);
        }catch (IOException | GeneralSecurityException g) {
            DialogMessage.exceptionDialog(g);
        }
        return fields;
    }

    private GoogleDriveFileFields getFile(Drive drive, String remoteFileName, String remoteFolderId) {
        try {
            String query = "";
            if(remoteFolderId != null) query = String.format("'%s' in parents and trashed = false", remoteFolderId);
            FileList fileList = drive.files().list()
                    .setQ(query)
                    .setPageSize(10)
                    .setFields("nextPageToken, files(id, name, mimeType, createdTime, modifiedTime, size)")
                    .execute();
            List<File> files = fileList.getFiles();
            if (files == null || files.isEmpty()) {
                String content = "Le fichier " + remoteFileName + " est introuvable";
                DialogMessage.showWarningDialog("No files found", content);
            }else {
                System.out.println("Files: ");
                for (File file: files) {
                    if (!credentials.isFolder(file) && file.getName().equals(remoteFileName)) {
                        fields = new GoogleDriveFileFields(
                                file.getId(),
                                file.getName(),
                                file.getMimeType(),
                                file.getCreatedTime(),
                                file.getModifiedTime(),
                                file.getSize()
                        );
                        System.out.printf("%s (%s)\n", file.getName() + " " + file.getMimeType(), file.getId());
                    }
                }
            }
        }catch (IOException g) {
            DialogMessage.exceptionDialog(g);
        }
        return fields;
    }


}
