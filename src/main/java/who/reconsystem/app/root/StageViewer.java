package who.reconsystem.app.root;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import who.reconsystem.app.user.UserBean;

import java.lang.reflect.Constructor;

public class StageViewer {

    private final StageLuncher luncher;

    public StageViewer(StageLuncher luncher) {
        this.luncher = luncher;
    }

    public void showStage() {
        Platform.runLater(luncher::lunchStage);
    }

    public void showSessionStage(UserBean user) {
        Platform.runLater(() -> {
            initData(user);
            luncher.lunchStage();
        });
    }

    public void hide (MouseEvent event) {
        Platform.runLater(() -> {
            Node node = (Node) event.getSource();
            node.getScene().getWindow().hide();
        });
    }

    public void hide (KeyEvent keyEvent) {
        Platform.runLater(() -> {
            Node node = (Node) keyEvent.getSource();
            node.getScene().getWindow().hide();
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
