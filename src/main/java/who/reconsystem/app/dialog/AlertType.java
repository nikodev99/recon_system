package who.reconsystem.app.dialog;

import javafx.scene.control.Alert;

public enum AlertType {
    INFORMATION,
    WARNING,
    ERROR,
    CONFIRMATION,
    NONE;

    private Alert.AlertType type;

    public Alert.AlertType getType() {
        return type;
    }

    static {
        INFORMATION.type = Alert.AlertType.INFORMATION;
        WARNING.type = Alert.AlertType.WARNING;
        ERROR.type = Alert.AlertType.ERROR;
        CONFIRMATION.type = Alert.AlertType.CONFIRMATION;
        NONE.type = Alert.AlertType.NONE;
    }
}
