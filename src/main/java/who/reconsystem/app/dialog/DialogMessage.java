package who.reconsystem.app.dialog;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;

import java.nio.charset.StandardCharsets;
import java.util.Arrays;
import java.util.List;
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
        //TODO add great message error to the exception dialog
        //ExceptionDialog exception = new ExceptionDialog(ex.getMessage());
        String content = Arrays.toString(ex.getStackTrace());
        DialogAlert dialog = new DialogAlert("Exception Dialog", ex.getClass().getName(), content);
        Alert alert = dialogAlert(AlertType.ERROR, dialog);
        alert.showAndWait();
    }

    public static Optional<ButtonType> confirmationDialog (List<String> messages) {
        List<String> btnHeaders = Arrays.asList("Oui", "Non");
        return confirmationDialog(messages.get(0), messages.get(1), btnHeaders);
    }

    public static Optional<ButtonType> confirmationDialog (List<String> messages, List<String> buttons) {
        return confirmationDialog(messages.get(0), messages.get(1), buttons);
    }

    static Optional<ButtonType> confirmationDialog (String header, String content, List<String> btnHeaders) {
        DialogAlert dialog = new DialogAlert("Confirmation Dialog", header, content);
        Alert alert = dialogAlert(AlertType.CONFIRMATION, dialog);

        ButtonType yesBtn = new ButtonType(btnHeaders.get(0), ButtonBar.ButtonData.YES);
        ButtonType noBtn = new ButtonType(btnHeaders.get(1), ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesBtn, noBtn);
        return alert.showAndWait();
    }

    private static Alert dialogAlert (AlertType type, DialogAlert params) {
        Alert alert = new Alert(type.getType());
        alert.setTitle(decodeUtf8String(params.getTitle()));
        alert.setHeaderText(decodeUtf8String(params.getHeader()));
        alert.setContentText(decodeUtf8String(params.getContent()));
        return alert;
    }

    private static String decodeUtf8String(String encodedString) {
        byte[] bytes = encodedString.getBytes(StandardCharsets.ISO_8859_1);
        return new String(bytes, StandardCharsets.UTF_8);
    }

}
