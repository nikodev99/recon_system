package who.reconsystem.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import who.reconsystem.app.drive.DBFile;
import who.reconsystem.app.guice.DriveModule;
import who.reconsystem.app.guice.QueryModule;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.models.tables.UserTable;
import who.reconsystem.app.root.StageLuncher;
import who.reconsystem.app.root.StageViewer;
import who.reconsystem.app.root.config.Functions;
import who.reconsystem.app.root.config.PBKDF2WithHmacSHA1;

import java.util.Arrays;
import java.util.List;

public class WHOReconSystemApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        StageLuncher stageLuncher = new StageLuncher(stage, "login", "Login Pane", false);
        StageViewer viewer = new StageViewer(stageLuncher);
        viewer.show();
    }

    private void queryBuilder() {
        List<Object> values = Arrays.asList("sB6qS1rmTJkucT2", "Test", "Test", "test", PBKDF2WithHmacSHA1.hashPassword("toto"), "test@test.com",
                "2023-05-24 14:57:01", Functions.instantDatetime("yyyy-MM-dd HH:mm:ss"), "1");
        Injector injector = Guice.createInjector(new QueryModule());
        Table table = injector.getInstance(UserTable.class);
        long insertId = table.update(values);
        System.out.println(insertId);
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
