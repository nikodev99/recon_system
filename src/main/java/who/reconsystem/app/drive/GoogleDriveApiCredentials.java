package who.reconsystem.app.drive;

import com.google.api.client.auth.oauth2.Credential;
import com.google.api.client.extensions.java6.auth.oauth2.AuthorizationCodeInstalledApp;
import com.google.api.client.extensions.jetty.auth.oauth2.LocalServerReceiver;
import com.google.api.client.googleapis.auth.oauth2.GoogleAuthorizationCodeFlow;
import com.google.api.client.googleapis.auth.oauth2.GoogleClientSecrets;
import com.google.api.client.http.javanet.NetHttpTransport;
import com.google.api.client.json.JsonFactory;
import com.google.api.client.util.store.FileDataStoreFactory;
import com.google.inject.Inject;
import who.reconsystem.app.MainApp;
import who.reconsystem.app.drive.annotations.CredentialFilePathValue;
import who.reconsystem.app.drive.annotations.JsonFactoryValue;
import who.reconsystem.app.drive.annotations.TokenDirectoryValue;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class GoogleDriveApiCredentials {

    private final String credentialFilePath;

    private final JsonFactory jsonFactory;

    private final String tokenDirectory;

    @Inject
    public GoogleDriveApiCredentials(
            @CredentialFilePathValue String credentialFilePath,
            @JsonFactoryValue JsonFactory jsonFactory,
            @TokenDirectoryValue String tokenDirectory
    ) {
        this.credentialFilePath = credentialFilePath;
        this.jsonFactory = jsonFactory;
        this.tokenDirectory = tokenDirectory;
    }

    private Credential getCredentials(final NetHttpTransport HTTP_TRANSPORT, final List<String> SCOPES) throws IOException {
        InputStream in = GoogleDriveApiCredentials.class.getResourceAsStream(credentialFilePath);
        if (in == null) {
            throw new FileNotFoundException("Resource not found: " + credentialFilePath);
        }
        GoogleClientSecrets secrets = GoogleClientSecrets.load(jsonFactory, new InputStreamReader(in));
        GoogleAuthorizationCodeFlow codeFlow = new GoogleAuthorizationCodeFlow.Builder(
                HTTP_TRANSPORT, jsonFactory, secrets, SCOPES
        )
                .setDataStoreFactory(new FileDataStoreFactory(new java.io.File(tokenDirectory)))
                .setAccessType("offline")
                .build();
        LocalServerReceiver receiver = new LocalServerReceiver.Builder().setPort(8888).build();
        return new AuthorizationCodeInstalledApp(codeFlow, receiver).authorize("nikodev99");
    }

}
