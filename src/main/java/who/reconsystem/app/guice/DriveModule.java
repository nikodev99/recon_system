package who.reconsystem.app.guice;

import com.google.api.client.json.JsonFactory;
import com.google.api.client.json.gson.GsonFactory;
import com.google.inject.AbstractModule;
import who.reconsystem.app.drive.FileData;
import who.reconsystem.app.drive.FileDataStorage;
import who.reconsystem.app.drive.annotations.*;

public class DriveModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(FileData.class).to(FileDataStorage.class);
        bind(String.class).annotatedWith(CredentialFilePathValue.class).toInstance("/credential.json");
        bind(JsonFactory.class).annotatedWith(JsonFactoryValue.class).toInstance(
                GsonFactory.getDefaultInstance()
        );
        bind(String.class).annotatedWith(TokenDirectoryValue.class).toInstance("token");
        bind(String.class).annotatedWith(ApplicationNameValue.class).toInstance("connect-slqlite-from-gd");
        bind(String.class).annotatedWith(ParentFolderValue.class).toInstance("158nX9RA7Emissv0E9ImuNkMlXV4v7xmp");
        bind(String.class).annotatedWith(DatabaseValue.class).toInstance("reconsystem.db");
        bind(String.class).annotatedWith(MimeTypeValue.class).toInstance("application/x-sqlite3");
    }
}
