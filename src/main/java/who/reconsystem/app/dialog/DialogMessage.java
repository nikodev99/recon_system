package who.reconsystem.app.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import who.reconsystem.app.service.collections.StringList;

import java.util.Arrays;
import java.util.Optional;

public class DialogMessage {

    public static void showInformationDialog (String header, String content) {
        DialogAlert dialog = new DialogAlert("Information Dialog", header, content);
        Alert alert = dialogAlert(AlertType.INFORMATION, dialog);
        alert.showAndWait();
    }

    public static void showWarningDialog (String header, String content) {
        DialogAlert dialog = new DialogAlert("Warning Dialog", header, content);
        Alert alert = dialogAlert(AlertType.WARNING, dialog);
        alert.showAndWait();
    }

    public static void ErrorDialog (String header, String content) {
        DialogAlert dialog = new DialogAlert("Error Dialog", header, content);
        Alert alert = dialogAlert(AlertType.ERROR, dialog);
        alert.showAndWait();
    }

    public static void exceptionDialog (Exception ex) {
        //ExceptionDialog exception = new ExceptionDialog(ex.getMessage());
        String content = Arrays.toString(ex.getStackTrace());
        DialogAlert dialog = new DialogAlert("Exception Dialog", ex.getClass().getName(), content);
        Alert alert = dialogAlert(AlertType.ERROR, dialog);
        alert.showAndWait();
    }

    public static Optional<ButtonType> confirmationDialog (StringList messages) {
        StringList btnHeaders = new StringList("Oui", "Non");
        return confirmationDialog(messages.getElement(0), messages.getElement(1), btnHeaders);
    }

    public static Optional<ButtonType> confirmationDialog (StringList messages, StringList buttons) {
        return confirmationDialog(messages.getElement(0), messages.getElement(1), buttons);
    }

    static Optional<ButtonType> confirmationDialog (String header, String content, StringList btnHeaders) {
        DialogAlert dialog = new DialogAlert("Confirmation Dialog", header, content);
        Alert alert = dialogAlert(AlertType.CONFIRMATION, dialog);

        ButtonType yesBtn = new ButtonType(btnHeaders.getElement(0), ButtonBar.ButtonData.YES);
        ButtonType noBtn = new ButtonType(btnHeaders.getElement(1), ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesBtn, noBtn);
        return alert.showAndWait();
    }

    private static Alert dialogAlert (AlertType type, DialogAlert params) {
        Alert alert = new Alert(type.getType());
        alert.setTitle(params.getTitle());
        alert.setHeaderText(params.getHeader());
        alert.setContentText(params.getContent());
        return alert;
    }

}
