package who.reconsystem.app;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.googleapis.javanet.GoogleNetHttpTransport;
import com.google.api.client.googleapis.json.GoogleJsonResponseException;
import com.google.api.client.http.FileContent;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.api.services.drive.Drive;
import com.google.api.services.drive.DriveScopes;
import com.google.api.services.drive.model.File;
import com.google.api.services.drive.model.FileList;

import javax.swing.*;
import javax.swing.text.BadLocationException;
import javax.swing.text.Document;
import javax.swing.text.rtf.RTFEditorKit;
import java.io.*;
import java.security.GeneralSecurityException;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class MainApp {

    private static final String APPLICATION_NAME = "connect-slqlite-from-gd";
    private static final JsonFactory JSON_FACTORY = GsonFactory.getDefaultInstance();
    private static final String TOKEN_DIRECTORY = "token";
    private static final List<String> SCOPES = Collections.singletonList(DriveScopes.DRIVE_METADATA_READONLY);
    private static final String CREDENTIAL_FILE_PATH = "/credential.json";

    private static final String DATABASE_FOLDER_NAME = "158nX9RA7Emissv0E9ImuNkMlXV4v7xmp";
    private static final String DATABASE_FILE_NAME = "reconsystem.db";
    //private java.io.File DATA_STORE_DIR = new java.io.File(System.getProperty("user.home"), ".credentials/" + TOKEN_DIRECTORY);

    public static void main(String[] args) throws BadLocationException {
        RTFEditorKit rtfParser = new RTFEditorKit();
    }

    public static String updateFile(String fileName) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final List<String> SCOPE = Collections.singletonList(DriveScopes.DRIVE_FILE);
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, SCOPE))
                .setApplicationName(APPLICATION_NAME)
                .build();
        File localFile = new File();
        localFile.setName(fileName);
        java.io.File localFileContent = new java.io.File(Objects.requireNonNull(MainApp.class.getClassLoader().getResource(fileName)).getFile());
        FileContent content = new FileContent("text/plain", localFileContent);
        String remoteFileId = getFileId(fileName);
        String fileId = null;
        try {
            File updatedFile = drive.files().update(remoteFileId, localFile, content)
                    .setAddParents(DATABASE_FOLDER_NAME)
                    .setFields("id")
                    .execute();
            fileId = updatedFile.getId();
            System.out.println("Update success");
        }catch (IOException e) {
            System.out.println("Unable to update: " + e.getMessage());
        }
        return fileId;
    }

    public static void downloadFromGoogleDrive(String fileName, String path) throws IOException, GeneralSecurityException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, SCOPES))
                .setApplicationName(APPLICATION_NAME)
                .build();
        String remoteFileId = getFileId(fileName);
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        drive.files().get(remoteFileId).executeMediaAndDownloadTo(outputStream);
        FileOutputStream fos = new FileOutputStream(path + "/" + fileName);
        outputStream.writeTo(fos);
        fos.close();
    }

    public static String uploadFile() throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        final List<String> SCOPE = Collections.singletonList(DriveScopes.DRIVE_FILE);
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, SCOPE))
                .setApplicationName(APPLICATION_NAME)
                .build();
        File file = new File();
        file.setName("textFile.txt");
        file.setParents(Collections.singletonList(DATABASE_FOLDER_NAME));
        java.io.File fileData = new java.io.File(Objects.requireNonNull(MainApp.class.getClassLoader().getResource("textFile.txt")).getFile());
        FileContent content = new FileContent("text/plain", fileData);
        String fileId = null;
        try {
            File uploadedFile = drive.files().create(file, content)
                    .setFields("id")
                    .execute();
            fileId = uploadedFile.getId();
        }catch (GoogleJsonResponseException e) {
            System.out.println("Unable to upload: " + e.getMessage());
        }
        return fileId;
    }

    public static String getFileId(String fileName) throws GeneralSecurityException, IOException {
        final NetHttpTransport HTTP_TRANSPORT = GoogleNetHttpTransport.newTrustedTransport();
        Drive drive = new Drive.Builder(HTTP_TRANSPORT, JSON_FACTORY, getCredentials(HTTP_TRANSPORT, SCOPES))
                .setApplicationName(APPLICATION_NAME)
                .build();
        String query = String.format("'%s' in parents and trashed = false", DATABASE_FOLDER_NAME);
        FileList fileList = drive.files().list()
                .setQ(query)
                .setPageSize(10)
                .setFields("nextPageToken, files(id, name, mimeType)")
                .execute();
        List<File> files = fileList.getFiles();
        String fileId = null;
        if (files == null || files.isEmpty()) {
            System.out.println("No files found");
        }else {
            System.out.println("Files: ");
            for (File file: files) {
                if (!isFolder(file) && file.getName().equals(fileName)) {
                    fileId = file.getId();
                    System.out.printf("%s (%s)\n", file.getName() + " " + file.getMimeType(), file.getId());
                }
            }
        }
        return fileId;
    }

    private static Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, final List<String> SCOPES) throws IOException {
        System.out.println("Chemin vers les credentials: " + CREDENTIAL_FILE_PATH);
        InputStream in = MainApp.class.getResourceAsStream(CREDENTIAL_FILE_PATH);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + CREDENTIAL_FILE_PATH);
        }
        GoogleClientSecrets secrets = GoogleClientSecrets.load(JSON_FACTORY, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow codeFlow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, JSON_FACTORY, secrets, SCOPES
        )
        .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(TOKEN_DIRECTORY)))
        .setAccessType("offline")
        .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(codeFlow, receiver).authorize("nikodev99");
    }

    public static boolean isFolder(File file) {
        return "application/vnd.google-apps.folder".equals(file.getMimeType());
    }
}
