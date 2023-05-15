package who.reconsystem.app.dialog;

import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;

import java.util.Arrays;
import java.util.List;

public class ErrorSet {
    public void errorStyle (boolean status, TextField... textFields) {
        List<TextField> textFieldList = Arrays.asList(textFields);
        textFieldList.forEach(textField -> {
            if(status) textField.setStyle("-fx-text-box-border: #FF0011; -fx-focus-color: #FF0011;");
            else textField.setStyle("");
        });
    }

    public void errorStyle (boolean status, TextArea... textAreas) {
        List<TextArea> textAreaList = Arrays.asList(textAreas);
        textAreaList.forEach(textArea -> {
            if (status) textArea.setStyle("-fx-text-box-border: #FF0011; -fx-focus-color: #FF0011;");
            else textArea.setStyle("");
        });
    }
}
