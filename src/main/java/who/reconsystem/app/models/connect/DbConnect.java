package who.reconsystem.app.models.connect;

import com.google.inject.Inject;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.drive.DBFile;
import who.reconsystem.app.drive.annotations.DatabaseValue;
import who.reconsystem.app.root.config.Functions;

import java.io.File;
import java.nio.file.Paths;
import java.security.CodeSource;
import java.security.ProtectionDomain;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Objects;

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
        File file = new File(sqliteFilePath);
        if (file.exists()) {
            String jdbc = "jdbc:sqlite:/" + sqliteFilePath;
            try {
                Class.forName("org.sqlite.JDBC");
                //Log.getLog(ConnectDB.class).info("Connexion à la base des données... ");
                connection = DriverManager.getConnection(jdbc);
                //Log.getLog(ConnectDB.class).info("Successfully connected to " + connection);
                DialogMessage.showInformationDialog("Connection", "Connection à " + connection + " établie");
            }catch (Exception e) {
                //Log.getLog(ConnectDB.class).error("Erreur rencontrée lors de la connexion à la base de données: " + e.getMessage());
                DialogMessage.exceptionDialog(e);
            }
        }else {
            String content = "Le fichier " + fileName + " est introuvable";
            DialogMessage.ErrorDialog("File not found", content);
        }
        return connection;
    }

}
