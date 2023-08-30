package who.reconsystem.app.controllers;

import javafx.application.Platform;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import who.reconsystem.app.dialog.DialogMessage;
import who.reconsystem.app.dialog.ErrorSet;
import who.reconsystem.app.exception.LoginException;
import who.reconsystem.app.root.StageLuncher;
import who.reconsystem.app.root.StageViewer;
import who.reconsystem.app.root.auth.Session;
import who.reconsystem.app.root.config.Functions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class Controller {

    private static final List<String> contents = new ArrayList<>();

    private Controller() {
    }

    public static void showStage(String stageName, boolean isDecorated, Session session) {
        try {
            StageLuncher stageLuncher = new StageLuncher(stageName, isDecorated, session);
            StageViewer viewer = new StageViewer(stageLuncher);
            viewer.openNewSession();
        }catch (LoginException le) {
            //TODO log file and dialog
            le.printStackTrace();
        }
    }

    public static void showStage(String stageName, boolean isDecorated) {
        try {
            StageLuncher stageLuncher = new StageLuncher(stageName, isDecorated, null);
            StageViewer viewer = new StageViewer(stageLuncher);
            viewer.openNewSession();
        }catch (LoginException le) {
            //TODO log file and dialog
            le.printStackTrace();
        }
    }

    public static String tr(TextField input) {
        String goodInput = input.getText().trim();
        if (goodInput.isEmpty()) {
            ErrorSet.logErrorStyle(true, input);
        }
        return goodInput;
    }

    public static String tr(TextField input, String message) {
        String goodInput = input.getText().trim();
        if (goodInput.isEmpty()) {
            ErrorSet.errorStyle(true, input);
            contents.add(message);
        }
        return goodInput;
    }

    public static boolean checkEmptiness(TextField ...inputs) {
        boolean checked = true;
        for (TextField input: inputs) {
            if(tr(input).isEmpty()) {
                checked = false;
            }
        }
        return checked;
    }

    public static void keyboardValidation(TextField ...fields) {
        for(TextField field: fields) {
            field.setOnKeyPressed(e -> ErrorSet.logErrorStyle(false, field));
        }
    }

    public static String getContent() {
        String[] errorMessages = contents.toArray(new String[0]);
        return contents.size() > 1 ?
                Functions.arrayToString(errorMessages, true, "- ", "") :
                Functions.arrayToString(errorMessages);
    }

    public static void hide (MouseEvent event) {
        Platform.runLater(() -> {
            Node node = (Node) event.getSource();
            node.getScene().getWindow().hide();
        });
    }

    public static void hide (KeyEvent keyEvent) {
        Platform.runLater(() -> {
            Node node = (Node) keyEvent.getSource();
            node.getScene().getWindow().hide();
        });
    }

    public static void closeProgram () {
        List<String> messages = new ArrayList<>();
        messages.add("Fermeture de l'application");
        messages.add("Veuillez confirmer la fermeture de l'application");
        Optional<ButtonType> result = DialogMessage.confirmationDialog(messages, Arrays.asList("Fermer", "Annuler"));
        if (result.isPresent() && result.get().getButtonData() == ButtonBar.ButtonData.YES) Platform.exit();
    }
}
