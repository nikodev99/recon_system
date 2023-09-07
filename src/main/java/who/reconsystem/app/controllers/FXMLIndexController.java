package who.reconsystem.app.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.drive.DBFile;
import who.reconsystem.app.guice.bidings.GoogleDriveSync;
import who.reconsystem.app.log.Log;
import who.reconsystem.app.root.auth.Session;
import who.reconsystem.app.user.User;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLIndexController implements Initializable {

    private final User loggedUserData;

    private final Session session;

    @FXML
    private Label welcomeMessage;

    @FXML
    private Button dashboardButton;

    @FXML
    private Button dataButton;

    @FXML
    private Button accountsButton;

    @FXML
    private Button who414Button;

    @FXML
    private Button settingsButton;

    @FXML
    private Button logoutButton;

    public FXMLIndexController(User loggedUserData, Session session) {
        this.loggedUserData = loggedUserData;
        this.session = session;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeMessage.setText("Welcome, " + loggedUserData.getFirstName() + " " + loggedUserData.getLastName());
        handleMenu();
        new GoogleDriveThread().start();
    }

    private void handleMenu() {
        if (session.isLogged()) {
            dataButton.setOnMouseClicked(e -> Controller.showStage("data-management", "Data Management Stage", session));
        }else {
            Log.set(FXMLIndexController.class).error("Impossible d'afficher le stage. La session est terminé");
            DialogMessage.errorDialog("Session terminé", "Impossible d'afficher le stage. La session est terminé");
        }
    }

    static class GoogleDriveThread extends Thread {
        @Override
        public void run() {
            while (true) {
                try {
                    final long THREAD_INACTIVITY = 60 * 60 * 1000L;
                    Thread.sleep(THREAD_INACTIVITY);
                }catch (InterruptedException e) {
                    Log.set(GoogleDriveThread.class).error("Google sync thead error: " + e.getMessage());
                }
                Platform.runLater(() -> {
                    DBFile file = GoogleDriveSync.useSyncFile();
                    String fileID = file.uploadDatabaseFile();
                    if (fileID != null && !fileID.isEmpty()) {
                        file.downloadDatabaseFromGoogleDrive();
                    }
                });
            }
        }
    }
}
