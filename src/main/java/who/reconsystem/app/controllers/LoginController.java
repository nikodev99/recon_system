package who.reconsystem.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyCode;
import javafx.scene.input.MouseEvent;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.log.Log;
import who.reconsystem.app.root.auth.Auth;
import who.reconsystem.app.root.auth.Session;
import who.reconsystem.app.user.User;

import java.net.URL;
import java.util.ResourceBundle;

public class LoginController implements Initializable {

    @FXML
    private TextField username;

    @FXML
    private PasswordField password;

    @FXML
    private Button loginBtn;

    @FXML
    private Button cancelBtn;

    private Session session;

    private User user;

    private Auth auth;

    public LoginController() {
        user = getUserBeanInstance();
        auth = getAuthInstance();
        session = getSessionInstance();
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        Controller.keyboardValidation(username, password);
        handleKeyboardConnection(username, password);
    }

    public void handleConnection(MouseEvent event) {
        if (event.getSource() == loginBtn) {
            gettingConnected();
            if (session.isLogged()) Controller.hide(event);
        }
    }

    public void handleConnectionCancelled(MouseEvent event) {
        if (event.getSource() == cancelBtn) {
            Controller.closeProgram();
        }
    }

    private void handleKeyboardConnection(TextField ...fields) {
        for (TextField field: fields) {
            field.setOnKeyPressed(e -> {
                if (e.getCode() == KeyCode.ENTER) {
                    gettingConnected();
                    Log.set(LoginController.class).debug("isLogged: " + session.isLogged());
                    if (session.isLogged()) Controller.hide(e);
                }
            });
        }
    }

    private void gettingConnected() {
        String inputUsername = Controller.tr(username);
        String inputPassword = Controller.tr(password);

        System.out.println("{ " + inputUsername + " " + inputPassword + " }");

        String errorTitle = "Erreur de connection";
        String errorMessage = "Identifiant ou mot de passe incorrect";

        if (Controller.checkEmptiness(username, password)) {
            boolean login = auth.login(inputUsername, inputUsername, inputPassword);
            if (login) {
                session.setLogged(true);
                session.sessionStart();
                user = session.userLogged();
                Controller.showStage("index", true, session);
            }else {
                //TODO log
                DialogMessage.errorDialog(errorTitle, errorMessage);
            }
        }else {
            //TODO log
            DialogMessage.errorDialog(errorTitle, errorMessage);
        }

    }

    private Auth getAuthInstance() {
        if (auth == null) {
            auth = new Auth(user);
        }
        return auth;
    }

    private User getUserBeanInstance() {
        if (user == null) {
            user = User.builder().build();
        }
        return user;
    }

    private Session getSessionInstance() {
        if (session == null) {
            session = new Session(auth);
        }
        return session;
    }

}
