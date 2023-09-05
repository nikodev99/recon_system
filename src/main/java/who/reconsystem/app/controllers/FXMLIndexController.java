package who.reconsystem.app.controllers;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import who.reconsystem.app.user.UserBean;

import java.net.URL;
import java.util.ResourceBundle;

public class FXMLIndexController implements Initializable {

    private final UserBean loggedUserData;

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

    public FXMLIndexController(UserBean loggedUserData) {
        this.loggedUserData = loggedUserData;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        welcomeMessage.setText("Welcome, " + loggedUserData.getFirstName() + " " + loggedUserData.getLastName());
    }
}
