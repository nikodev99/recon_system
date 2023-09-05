package who.reconsystem.app.root;

import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import lombok.Getter;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.log.Log;
import who.reconsystem.app.root.auth.Session;
import who.reconsystem.app.user.UserBean;

import java.io.IOException;
import java.lang.reflect.Constructor;
import java.util.Objects;
import java.util.Stack;

public class StageLuncher {
    private double x, y = 0;

    private final Stage stage;

    private Stage stageInstance;

    private final String resourceName;

    private String stageTitle = "WHO Reconciliation System";

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
        this.isDecorated = isDecorated;
        this.session = session;
    }

    public StageLuncher(String resourceName, Session session) {
        this.stage = getStage();
        this.resourceName = resourceName;
        this.isDecorated = false;
        this.session = session;
    }

    public void lunchStage() {
        try {
            stage.setScene(initScene());
            initStage(this.isDecorated ? StageStyle.DECORATED : StageStyle.UNDECORATED);
            if (session != null && session.isLogged()) {
                inactivityTimer();
                lastActivity = System.currentTimeMillis();
            }
            stage.show();
        }catch (IOException io) {
            Log.set(StageLuncher.class).trace(io);
            DialogMessage.exceptionDialog(io);
        }
    }

    public void setSceneParams(Scene scene) {
        if (session != null) {
            scene.setOnMouseMoved(e -> lastActivity = System.currentTimeMillis());
        }
    }

    public FXMLLoader runLoader(UserBean user) {
        FXMLLoader fxmlLoader = new FXMLLoader();
        fxmlLoader.setLocation(Objects.requireNonNull(getClass().getResource(getResource())));
        if(session != null && session.isLogged() && user != null) {
            fxmlLoader.setControllerFactory((Class<?> controllerType) -> {
                try {
                    Constructor<?>[] constructors = controllerType.getConstructors();
                    for (Constructor<?> constructor: constructors) {
                        if (constructor.getParameterCount() == 1 && constructor.getParameterTypes()[0] == UserBean.class) {
                            return constructor.newInstance(user);
                        }
                    }
                    return controllerType.newInstance();
                }catch (Exception se) {
                    Log.set(StageViewer.class).error(se.getMessage());
                    DialogMessage.exceptionDialog(se);
                    return null;
                }
            });
        }
        return fxmlLoader;
    }

    public void handleClose() {
        Platform.runLater(stage::close);
    }

    private Scene initScene() throws IOException, NullPointerException {
        UserBean user = session != null && session.isLogged() ? session.userLogged() : null;
        Parent root = runLoader(user).load();
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
    }

    private String getResource() {
        return  "../" + resourceName + ".fxml";
    }

    private void inactivityTimer() {
        Task<Void> task = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                final long INACTIVITY_TIMOUT = 3 * 60 * 100L;
                while(true) {
                    long currentTime = System.currentTimeMillis();
                    System.out.println("before the end: " + (currentTime - lastActivity));
                    System.out.println("the end at: " + (INACTIVITY_TIMOUT));
                    System.out.println("status of the end: " + (currentTime - lastActivity >= INACTIVITY_TIMOUT));
                    if (currentTime - lastActivity >= INACTIVITY_TIMOUT) {
                        Platform.runLater(() -> {
                            Log.set(Stack.class).info("Session timed out. Please log in again");
                            handleClose();
                            session.setUpInactivity();
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
