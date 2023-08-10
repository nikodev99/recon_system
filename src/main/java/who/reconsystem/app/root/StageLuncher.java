package who.reconsystem.app.root;

import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import who.reconsystem.app.dialog.DialogMessage;

import java.io.IOException;
import java.util.Objects;

public class StageLuncher {
    private double x, y;

    private final Stage stage;

    private final String resourceName;

    private final String stageTitle;

    private boolean isDecorated;

    public StageLuncher(Stage stage, String resourceName, String stageTitle, boolean isDecorated) {
        this.stage = stage;
        this.resourceName = resourceName;
        this.stageTitle = stageTitle;
        this.isDecorated = isDecorated;
        this.x = 0; this.y = 0;
    }

    public StageLuncher(Stage stage, String resourceName, String stageTitle) {
        this.stage = stage;
        this.resourceName = resourceName;
        this.stageTitle = stageTitle;
        this.isDecorated = false;
        this.x = 0; this.y = 0;
    }

    public StageLuncher(Stage stage, String resourceName) {
        this.stage = stage;
        this.resourceName = resourceName;
        this.stageTitle = "WHO Reconciliation System";
        this.x = 0; this.y = 0;
    }

    public void lunchStage() {
        try {
            stage.setScene(initScene());
            initStage(this.isDecorated ? StageStyle.DECORATED : StageStyle.UNDECORATED);
            stage.show();
        }catch (IOException io) {
            //TODO uncomment the DialogMessage and adding the log file
            /* DialogMessage.exceptionDialog(io); */
            io.printStackTrace();
        }
    }

    public void setSceneParams(Scene scene) {}

    public void setStageParams(Stage stage) {}

    public void setLoaderParams(FXMLLoader loader) {}

    public FXMLLoader runLoader() {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource(getResource())));
        setLoaderParams(fxmlLoader);
        return fxmlLoader;
    }

    public void handleClose() {
        stage.close();
        Platform.exit();
    }

    private Scene initScene() throws IOException, NullPointerException {
        Parent root = runLoader().load();
        Scene scene = new Scene(root);

        scene.setOnMousePressed(event -> {
            x = event.getSceneX();
            y = event.getSceneY();
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
        if (!stageTitle.isEmpty()) {
            this.stage.setTitle(stageTitle);
        }
        setStageParams(this.stage);
    }

    private String getResource() {
        return  "../" + resourceName + ".fxml";
    }
}
