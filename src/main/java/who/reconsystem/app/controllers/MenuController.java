package who.reconsystem.app.controllers;

import javafx.application.Platform;
import javafx.fxml.FXML;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;

import java.awt.event.MouseEvent;

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

    public void handleClose(MouseEvent e) {
        if (e.getSource() == close) {
            Platform.exit();
        }
    }

}
