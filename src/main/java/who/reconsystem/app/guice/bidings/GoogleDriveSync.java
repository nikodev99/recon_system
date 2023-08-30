package who.reconsystem.app.guice.bidings;

import com.google.inject.Guice;
import com.google.inject.Injector;
import who.reconsystem.app.drive.DBFile;
import who.reconsystem.app.guice.DriveModule;

public class GoogleDriveSync {

    public static DBFile useSyncFile() {
        Injector injector = Guice.createInjector(new DriveModule());
        return injector.getInstance(DBFile.class);
    }

}
