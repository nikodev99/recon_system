package who.reconsystem.app.root;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import who.reconsystem.app.root.auth.Session;

import java.io.IOException;
import java.util.Objects;

public class StageLuncher {
    private double x, y = 0;

    private final Stage stage;

    private Stage stageInstance;

    private final String resourceName;

    private final String stageTitle;

    private final boolean isDecorated;

    @Getter
    private final Session session;

    private long lastActivity;

    public StageLuncher(Stage stage, String resourceName, String stageTitle, boolean isDecorated, Session session) {
        this.stage = stage;
        this.resourceName = resourceName;
        this.stageTitle = stageTitle;
        this.isDecorated = isDecorated;
        this.session = session;
    }

    public StageLuncher(String resourceName, String stageTitle, boolean isDecorated, Session session) {
        this.stage = getStage();
        this.resourceName = resourceName;
        this.stageTitle = stageTitle;
        this.isDecorated = isDecorated;
        this.session = session;
    }

    public StageLuncher(String resourceName, String stageTitle, Session session) {
        this.stage = getStage();
        this.resourceName = resourceName;
        this.stageTitle = stageTitle;
        this.isDecorated = false;
        this.session = session;
    }

    public StageLuncher(String resourceName, boolean isDecorated, Session session) {
        this.stage = getStage();
        this.resourceName = resourceName;
        this.stageTitle = "WHO Reconciliation System";
        this.isDecorated = isDecorated;
        this.session = session;
    }

    public StageLuncher(String resourceName, Session session) {
        this.stage = getStage();
        this.resourceName = resourceName;
        this.stageTitle = "WHO Reconciliation System";
        this.isDecorated = false;
        this.session = session;
    }

    public void lunchStage() {
        try {
            stage.setScene(initScene());
            initStage(this.isDecorated ? StageStyle.DECORATED : StageStyle.UNDECORATED);
            stage.show();
            if (session.isLogged()) {
                inactivityTimer();
                lastActivity = System.currentTimeMillis();
            }
        }catch (IOException io) {
            //TODO uncomment the DialogMessage and adding the log file
            /* DialogMessage.exceptionDialog(io); */
            io.printStackTrace();
        }
    }

    public void setSceneParams(Scene scene) {
        if (session != null) {
            scene.setOnMouseMoved(e -> lastActivity = System.currentTimeMillis());
        }
    }

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

    private void inactivityTimer() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                final long INACTIVITY_TIMOUT = 30 * 60 * 100L;
                long currentTime = System.currentTimeMillis();
                while(true) {
                    if (currentTime - lastActivity >= INACTIVITY_TIMOUT) {
                        Platform.runLater(() -> {
                            session.setUpInactivity();
                            lunchStage();
                            handleClose();
                        });
                        break;
                    }
                    Thread.sleep(1000);
                }
                return null;
            }
        };
        Thread thread = new Thread(task);
        thread.setDaemon(true);
        thread.start();
    }

    private Stage getStage() {
        if (stageInstance == null) {
            stageInstance = new Stage();
        }
        return stageInstance;
    }
}
