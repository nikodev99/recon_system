package who.reconsystem.app.root;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.stage.Stage;
import who.reconsystem.app.exception.LoginException;
import who.reconsystem.app.user.UserBean;

import java.lang.reflect.Constructor;

public class StageViewer {

    private final StageLuncher luncher;

    public StageViewer(StageLuncher luncher) {
        this.luncher = luncher;
    }

    public void show() {
        Platform.runLater(luncher::lunchStage);
    }

    public void openNewSession() throws LoginException {
        if (luncher.getSession() == null) throw new LoginException("Erreur de connection lié à la session.");
        UserBean user = luncher.getSession().userLogged();
        Platform.runLater(() -> {
            initData(user);
            luncher.lunchStage();
        });
    }

    public void hide (MouseEvent event) {
        Platform.runLater(() -> {
            Stage node = (Stage) event.getSource();
            node.getScene().getWindow();
            node.close();
        });
    }

    public void hide (KeyEvent keyEvent) {
        Platform.runLater(() -> {
            Stage node = (Stage) keyEvent.getSource();
            node.getScene().getWindow();
            node.close();
        });
    }

    private void initData(UserBean user) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        ObservableList<UserBean> userData = FXCollections.observableArrayList(user);
        fxmlLoader.setControllerFactory((Class<?> controllerType) -> {
            try {
                Constructor<?>[] constructors = controllerType.getConstructors();
                for (Constructor<?> constructor: constructors) {
                    if (constructor.getParameterCount() == 1 && constructor.getParameterTypes()[0] == ObservableList.class) {
                        return constructor.newInstance(userData);
                    }
                }
                return controllerType.newInstance();
            }catch (Exception se) {
                //TODO adding log
                se.printStackTrace();
                return null;
            }
        });
        luncher.setLoaderParams(fxmlLoader);
    }

}
