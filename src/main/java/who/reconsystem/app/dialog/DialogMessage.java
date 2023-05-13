package who.reconsystem.app.dialog;

import javafx.scene.control.*;

import java.util.Arrays;
import java.util.Optional;

public class DialogMessage {

    public void showInformationDialog (String header, String content) {
        String[] settings = new String[]{"INFORMATION", "Information Dialog", header, content};
        Alert alert = dialogAlert(settings);
        alert.showAndWait();
    }

    public void showWarningDialog (String header, String content) {
        String[] settings = new String[]{"WARNING", "Warning Dialog", header, content};
        Alert alert = dialogAlert(settings);
        alert.showAndWait();
    }

    public void ErrorDialog (String header, String content) {
        String[] settings = new String[]{"ERROR", "Error Dialog", header, content};
        Alert alert = dialogAlert(settings);
        alert.showAndWait();
    }

    public void exceptionDialog (Exception ex) {
        String[] settings = new String[]{"ERROR", "Exception Dialog", ex.getMessage(), Arrays.toString(ex.getStackTrace())};
        Alert alert = dialogAlert(settings);
        alert.showAndWait();
    }

    public Optional<ButtonType> confirmationDialog (String header, String content) {
        String[] settings = new String[]{"CONFIRMATION", "Confirmation Dialog", header, content};
        Alert alert = dialogAlert(settings);

        ButtonType yesBtn = new ButtonType("Oui", ButtonBar.ButtonData.YES);
        ButtonType noBtn = new ButtonType("Non", ButtonBar.ButtonData.NO);

        alert.getButtonTypes().setAll(yesBtn, noBtn);
        return alert.showAndWait();
    }

    public Optional<ButtonType> confirmationDialog (String[] messages, String [] buttons) {
        String[] settings = new String[]{"CONFIRMATION", "Confirmation Dialog", messages[0], messages[1]};
        Alert alert = dialogAlert(settings);

        ButtonType yesBtn = new ButtonType(buttons[0], ButtonBar.ButtonData.APPLY);
        ButtonType noBtn = new ButtonType(buttons[1], ButtonBar.ButtonData.OK_DONE);
        alert.getButtonTypes().setAll(yesBtn, noBtn);
        return alert.showAndWait();
    }

    public void errorStyle (TextField[] textFields, boolean status) {
        for (TextField textField : textFields)
            if (status) {
                textField.setStyle("-fx-text-box-border: #FF0011; -fx-focus-color: #FF0011;");
            } else {
                textField.setStyle("");
            }
    }

    public void errorStyle(TextField textField, boolean status) {
        if (status) {
            textField.setStyle("-fx-text-box-border: #FF0011; -fx-focus-color: #FF0011;");
        }else {
            textField.setStyle("");
        }
    }

    public void errorStyle (TextArea textArea, boolean status) {
        if (status) {
            textArea.setStyle("-fx-text-box-border: #FF0011; -fx-focus-color: #FF0011;");
        }else {
            textArea.setStyle("");
        }
    }

    private Alert dialogAlert (String[] informations) {
        Alert alert = new Alert(Alert.AlertType.valueOf(informations[0]));
        alert.setTitle(informations[1]);
        alert.setHeaderText(informations[2]);
        alert.setContentText(informations[3]);
        return alert;
    }

}
