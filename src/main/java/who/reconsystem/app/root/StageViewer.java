package who.reconsystem.app.root;

import javafx.application.Platform;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXMLLoader;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.exception.LoginException;
import who.reconsystem.app.log.Log;
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

}
