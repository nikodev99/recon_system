package who.reconsystem.app.root;

import javafx.application.Platform;

public class StageViewer {

    private final StageLuncher luncher;

    public StageViewer(StageLuncher luncher) {
        this.luncher = luncher;
    }

    public void show() {
        Platform.runLater(luncher::lunchStage);
    }

}
