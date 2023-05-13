package who.reconsystem.app.root;

import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;

import java.io.IOException;
import java.util.Objects;

public class StageLuncher {

    double x, y = 0;

    public StageLuncher() {
        try {
            Parent root = FXMLLoader.load(Objects.requireNonNull(getClass().getResource("index.fxml")));
            Scene scene = new Scene(root);
        }catch (IOException file) {

        }
    }
}
