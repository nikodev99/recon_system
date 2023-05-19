package who.reconsystem.app;

import com.google.inject.Guice;
import com.google.inject.Injector;
import javafx.application.Application;
import javafx.stage.Stage;
import who.reconsystem.app.guice.DriveModule;
import who.reconsystem.app.drive.DBFile;
import who.reconsystem.app.guice.QueryModule;
import who.reconsystem.app.models.Operator;
import who.reconsystem.app.models.Table;
import who.reconsystem.app.models.tables.UserTable;
import who.reconsystem.app.root.StageLuncher;

public class WHOReconSystemApp extends Application {

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage stage) throws Exception {
        /*StageLuncher stageLuncher = new StageLuncher(stage, "index", "Login Pane");
        stageLuncher.lunchStage();*/

        System.out.println("Insert statement: " + queryBuilder());
    }

    private String queryBuilder() {
        Injector injector = Guice.createInjector(new QueryModule());
        Table table = injector.getInstance(UserTable.class);
        return table.insert();
    }

    private void database() {
        Injector injector = Guice.createInjector(new DriveModule());
        DBFile file = injector.getInstance(DBFile.class);
        file.downloadDatabaseFromGoogleDrive();
        //String id = file.uploadDatabaseFile();
        //System.out.println("réussi avec id=" + id);
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
