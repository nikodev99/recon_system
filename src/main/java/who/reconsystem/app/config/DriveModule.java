package who.reconsystem.app.config;

import com.google.api.client.json.gson.GsonFactory;
import com.google.inject.AbstractModule;
import who.reconsystem.app.drive.annotations.CredentialFilePathValue;
import who.reconsystem.app.drive.annotations.JsonFactoryValue;
import who.reconsystem.app.drive.annotations.TokenDirectoryValue;

public class DriveModule extends AbstractModule {

    @Override
    protected void configure() {
        bind(String.class).annotatedWith(CredentialFilePathValue.class).toInstance("/credential.json");
        bind(GsonFactory.class).annotatedWith(JsonFactoryValue.class).toInstance(
                GsonFactory.getDefaultInstance()
        );
        bind(String.class).annotatedWith(TokenDirectoryValue.class).toInstance("token");
    }
}
