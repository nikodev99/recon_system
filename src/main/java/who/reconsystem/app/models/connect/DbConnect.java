package who.reconsystem.app.models.connect;

import com.google.inject.Inject;
import javafx.application.Platform;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.drive.DBFile;
import who.reconsystem.app.drive.annotations.DatabaseValue;
import who.reconsystem.app.guice.bidings.GoogleDriveSync;
import who.reconsystem.app.log.Log;
import who.reconsystem.app.root.config.Functions;

import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.Optional;

public class DbConnect {

    private final String fileName;

    @Inject
    public DbConnect(@DatabaseValue String fileName) {
        this.fileName = fileName;
    }

    public Connection connectSQLite() {
        Connection connection = null;
        String sqliteFilePath = Functions.getLocalePath(fileName);
        System.out.println("Route: " + sqliteFilePath);
        while (true) {
            if (Files.exists(Paths.get(sqliteFilePath))) {
                String jdbc = "jdbc:sqlite:/" + sqliteFilePath;
                try {
                    Class.forName("org.sqlite.JDBC");
                    Log.set(DbConnect.class).info("Connexion à la base des données... ");
                    connection = DriverManager.getConnection(jdbc);
                    Log.set(DbConnect.class).info("Successfully connected to " + connection);
                    break;
                }catch (Exception e) {
                    Log.set(DbConnect.class).error("Erreur rencontrée lors de la connexion à la base de données: " + e.getMessage());
                    DialogMessage.exceptionDialog(e);
                }
            }else {
                Log.set(DbConnect.class).error("Le fichier " + fileName + " est introuvable");
                Optional<ButtonType> btn = DialogMessage.confirmationDialog(Arrays.asList(
                        "Erreur interne au system",
                        "Problème lié à la base de donné. Voulez-vous réessayez"),
                Arrays.asList("Relancer", "Annuler")
                );
                if (btn.isPresent() && btn.get().getButtonData() == ButtonBar.ButtonData.NO) {
                    Platform.exit();
                    break;
                }else {
                    DBFile file = GoogleDriveSync.useSyncFile();
                    file.downloadDatabaseFromGoogleDrive();
                    sqliteFilePath = Functions.getLocalePath(fileName);
                }
            }
        }
        return connection;
    }

}
