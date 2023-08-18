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

import java.io.File;
import java.util.Arrays;
import java.util.List;

public class WHOReconSystemApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        Log.createLogFile();
        database();
        Log.set(WHOReconSystemApp.class).info("Début d'insertion");
        //database();
        //queryBuilder();
        Log.set(WHOReconSystemApp.class).info("fin de l'insertion");
        //StageLuncher stageLuncher = new StageLuncher(stage, "login", "Login Pane", false, null);
        //StageViewer viewer = new StageViewer(stageLuncher);
        //viewer.show();
    }

    private void queryBuilder() {
        List<Object> values = Arrays.asList(StrongIdGenerator.generateRandomString(20), "Nikhe", "Niama", "nikhe", PBKDF2WithHmacSHA1.hashPassword("toto"), "nikhe@test.com",
                Functions.instantDatetime("yyyy-MM-dd HH:mm:ss"), Functions.instantDatetime("yyyy-MM-dd HH:mm:ss"));
        Injector injector = Guice.createInjector(new QueryModule());
        Table table = injector.getInstance(UserTable.class);
        long insertId = table.insert(values);
        Log.set(WHOReconSystemApp.class).info("Insertion réussi. id: " + insertId);
    }

    private void database() {
        Injector injector = Guice.createInjector(new DriveModule());
        DBFile file = injector.getInstance(DBFile.class);
        Log.set(WHOReconSystemApp.class).info("Début du téléchargement");
        file.downloadDatabaseFromGoogleDrive();
        Log.set(WHOReconSystemApp.class).info("Téléchargement réussi");
        //String id = file.uploadDatabaseFile();
        //Log.set(WHOReconSystemApp.class).info("upload réussi avec id=" + id);
        //String id = file.updateDatabaseFile();
        //Log.set(WHOReconSystemApp.class).info("Mise à jour du fichier réussi avec id=" + id);
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
