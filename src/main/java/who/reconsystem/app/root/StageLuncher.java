package who.reconsystem.app.root;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import who.reconsystem.app.dialog.DialogMessage;

import java.io.IOException;
import java.util.Objects;

public class StageLuncher {
    double x, y = 0;

    private final Stage stage;

    private final String resourceName;

    private final String stageTitle;

    public StageLuncher(Stage stage, String resourceName, String stageTitle, double x, double y) {
        this.stage = stage;
        this.resourceName = resourceName;
        this.stageTitle = stageTitle;
        this.x = x;
        this.y = y;
    }

    public StageLuncher(Stage stage, String resourceName, String stageTitle) {
        this.stage = stage;
        this.resourceName = resourceName;
        this.stageTitle = stageTitle;
    }

    public void lunchStage() {
        try {
            this.stage.setScene(initScene());
            initStage(StageStyle.UNDECORATED);
            this.stage.show();
        }catch (IOException io) {
            DialogMessage.exceptionDialog(io);
        }
    }

    public void setSceneParams(Scene scene) {
    }

    public void SetStageParams(Stage stage) {

    }

    private Scene initScene() throws IOException, NullPointerException {
        Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource(getResource())));
        Scene scene = new Scene(root);

        scene.setOnMousePressed(event -> {
            this.x = event.getSceneX();
            this.y = event.getSceneY();
        });

        scene.setOnMouseDragged(event -> {
            this.stage.setX(event.getScreenX() - x);
            this.stage.setY(event.getScreenY() - y);
        });
        setSceneParams(scene);
        return scene;
    }

    private void initStage(StageStyle stageStyle) {
        this.stage.setResizable(true);
        this.stage.initStyle(stageStyle);
        this.stage.setTitle(stageTitle);
        SetStageParams(this.stage);
    }

    private String getResource() {
        return  "../" + resourceName + ".fxml";
    }
}
