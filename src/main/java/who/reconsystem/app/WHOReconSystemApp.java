package who.reconsystem.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import who.reconsystem.app.drive.DBFile;
import who.reconsystem.app.guice.DriveModule;
import who.reconsystem.app.guice.QueryModule;
import who.reconsystem.app.log.Log;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.models.tables.UserTable;
import who.reconsystem.app.root.StageLuncher;
import who.reconsystem.app.root.StageViewer;
import who.reconsystem.app.root.config.Functions;
import who.reconsystem.app.root.config.PBKDF2WithHmacSHA1;
import who.reconsystem.app.root.config.StrongIdGenerator;

import java.util.Arrays;
import java.util.List;

public class WHOReconSystemApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Log.set(WHOReconSystemApp.class).info("Début d'insertion");
        queryBuilder();
        Log.set(WHOReconSystemApp.class).info("fin d'insertion");
        //StageLuncher stageLuncher = new StageLuncher(stage, "login", "Login Pane", false, null);
        //StageViewer viewer = new StageViewer(stageLuncher);
        //viewer.show();
    }

    private void queryBuilder() {
        List<Object> values = Arrays.asList(StrongIdGenerator.generateRandomString(20), "Davy", "Koah", "davy", PBKDF2WithHmacSHA1.hashPassword("toto"), "davy@test.com",
                Functions.instantDatetime("yyyy-MM-dd HH:mm:ss"), Functions.instantDatetime("yyyy-MM-dd HH:mm:ss"));
        Injector injector = Guice.createInjector(new QueryModule());
        Table table = injector.getInstance(UserTable.class);
        long insertId = table.insert(values);
        Log.set(WHOReconSystemApp.class).info("Insertion réussi. id: " + insertId);
    }

    private void database() {
        Injector injector = Guice.createInjector(new DriveModule());
        DBFile file = injector.getInstance(DBFile.class);
        //file.downloadDatabaseFromGoogleDrive();
        String id = file.uploadDatabaseFile();
        System.out.println("réussi avec id=" + id);
        //String id = file.updateDatabaseFile();
        //System.out.println("id=" + id);
        /*GoogleDriveFileFields fields = file.getDatabaseFromGoogleDrive();
        System.out.println(
                "[ id=" + fields.getFileId() +
                        " name=" + fields.getFileName() +
                        " mimeType=" + fields.getFileMimeType() +
                        " createdOn=" + fields.getFileCreationOn().toString() +
                        " modifyOn=" + fields.getFileModifyOn().toString() +
                        " size=" + fields.getFileSize() +"]"
                );*/
    }
}
