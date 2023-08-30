package who.reconsystem.app.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.Node;
import javafx.scene.control.ButtonType;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Stage;
import who.reconsystem.app.dialog.DialogMessage;

import java.awt.event.MouseEvent;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class MenuController {

    @FXML
    private ImageView close;

    @FXML
    private ImageView maximize;

    @FXML
    private ImageView minimize;

    @FXML
    private ImageView menu;

    @FXML
    private AnchorPane menu_toggle;

    public void handleStageResizability() {
        close.setOnMouseClicked(e -> Controller.closeProgram());
        maximize.setOnMouseClicked(e -> {
            Platform.runLater(() -> {
                Node node = (Node) e.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setMaximized(!stage.isMaximized());
            });
        });
        minimize.setOnMouseClicked(e -> {
            Platform.runLater(() -> {
                Node node = (Node) e.getSource();
                Stage stage = (Stage) node.getScene().getWindow();
                stage.setIconified(true);
            });
        });
        menu.setOnMouseClicked(e -> {
            menu_toggle.setVisible(false);
        });
    }

}
