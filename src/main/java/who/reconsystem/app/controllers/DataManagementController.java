package who.reconsystem.app.controllers;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.chart.BarChart;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.ListView;
import javafx.scene.control.TableView;
import javafx.scene.input.DragEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.input.TransferMode;
import javafx.stage.FileChooser;
import javafx.stage.Stage;
import who.reconsystem.app.maindata.CashBook;
import who.reconsystem.app.user.User;

import java.net.URL;
import java.util.List;
import java.util.ResourceBundle;

public class DataManagementController implements Initializable {
    private User user;

    @FXML
    private Button dragDropButton;

    @FXML
    private ListView<String> screen;

    @FXML
    private BarChart<String, Long> chart;

    @FXML
    private Label loggedUser;

    @FXML
    private Label fullDate;

    @FXML
    private Label tickedTime;

    @FXML
    private TableView<CashBook> dataTable;

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    @FXML
    void handleDragOver(DragEvent event) {
        if (event.getDragboard().hasFiles()) {
            event.acceptTransferModes(TransferMode.ANY);
        }
    }

    @FXML
    void handleDrop(DragEvent event) {
        List<java.io.File> files = event.getDragboard().getFiles();
        screen.getItems().add(files.get(0).getAbsolutePath());
    }

    @FXML
    void handleButtonOnClick(MouseEvent event) {
        if (event.getSource() == dragDropButton) {
            FileChooser fileChooser = new FileChooser();
            fileChooser.getExtensionFilters().addAll(
                    new FileChooser.ExtensionFilter("Excel Files", "*.xlsx", "*.csv")
            );
            java.io.File selectedFile = fileChooser.showOpenDialog(new Stage());
            if (selectedFile != null && selectedFile.exists()) {
                screen.getItems().add(selectedFile.getAbsolutePath());
            }
        }
    }

}
