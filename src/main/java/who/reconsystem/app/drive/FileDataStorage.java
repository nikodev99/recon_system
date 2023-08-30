package who.reconsystem.app.drive;

import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;
import com.google.inject.Inject;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.log.Log;

import java.io.ByteArrayOutputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.security.GeneralSecurityException;
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
                Log.set(FileDataStorage.class).info("Mise à jour de la base de données réussi");
            } catch (IOException e) {
                Log.set(FileDataStorage.class).trace(e);
                //TODO exception dialog
            }
        }catch (IOException | GeneralSecurityException g) {
            Log.set(FileDataStorage.class).trace(g);
            //TODO exception dialog
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
            Log.set(FileDataStorage.class).debug("DBfile path=" + localePath);
            Log.set(FileDataStorage.class).info("Téléchargement la base de données complet");
        }catch (IOException | GeneralSecurityException g) {
            Log.set(FileDataStorage.class).trace(g);
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
                Log.set(FileDataStorage.class).info("Synchronisation de la base de données complet");
                fileId = uploadedFile.getId();
            }catch (GoogleJsonResponseException e) {
                Log.set(FileDataStorage.class).trace(e);
                DialogMessage.exceptionDialog(e);
            }
        }catch (IOException | GeneralSecurityException g) {
            Log.set(FileDataStorage.class).trace(g);
            DialogMessage.exceptionDialog(g);
        }
        return fileId;
    }

    @Override
    public GoogleDriveFileFields getFileInfo(String fileName, String folderId) {
        try {
            getFile(credentials.driveService(), fileName, folderId);
        }catch (IOException | GeneralSecurityException g) {
            Log.set(FileDataStorage.class).trace(g);
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
                Log.set(FileDataStorage.class).warn(content);
                DialogMessage.showWarningDialog("No files found", content);
            }else {
                Log.set(FileDataStorage.class).debug("Les fichiers: " + files);
                for (File file: files) {
                    if (!credentials.isFolder(file) && file.getName().equals(remoteFileName)) {
                        fields = GoogleDriveFileFields.builder()
                                .fileId(file.getId())
                                .fileName(file.getName())
                                .fileMimeType(file.getMimeType())
                                .fileCreationOn(file.getCreatedTime())
                                .fileModifyOn(file.getModifiedTime())
                                .fileSize(file.getSize())
                                .build();
                        System.out.printf("%s (%s)\n", file.getName() + " " + file.getMimeType(), file.getId());
                    }
                }
            }
        }catch (IOException g) {
            Log.set(FileDataStorage.class).trace(g);
            DialogMessage.exceptionDialog(g);
        }
        return fields;
    }


}
