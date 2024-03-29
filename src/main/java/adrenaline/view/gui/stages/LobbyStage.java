package adrenaline.view.gui.stages;

import adrenaline.view.gui.GUIController;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.SequentialTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;

import javafx.stage.Stage;
import javafx.util.Duration;

import java.io.File;

/**
 * Class that handles the lobby stage
 */
public class LobbyStage {

    private static final String PATH = "/maps/";
    private static final String SMALL = PATH +"smallmap.json";
    private static final String MEDIUM_1 = PATH + "mediummap_1.json";
    private static final String MEDIUM_2 = PATH +"mediummap_2.json";
    private static final String LARGE = PATH + "largemap.json";

    private Stage stage;
    private Scene lobbyScene;
    private StackPane rootPane;
    private VBox box;
    private Text waitingText;
    private Text waitingSelection;

    public LobbyStage(Stage stage) {
        this.stage = stage;
        this.rootPane = new StackPane();
        rootPane.setId("launcher");

        this.box = new VBox();
        box.setId("box");

        Text success = new Text("Login Successful\nInserted in a lobby");
        success.setId("text");

        this.waitingText = new Text("Wait for other players...");
        waitingText.setId("text");

        this.waitingSelection = new Text("The first player in your lobby\nis choosing the arena.\nPlease wait...");
        waitingSelection.setId("text");

        FadeTransition successTrans = new FadeTransition(Duration.seconds(3.0), success);
        successTrans.setFromValue(1.0);
        successTrans.setToValue(0.0);
        successTrans.setOnFinished(actionEvent -> {
            waitingText.setOpacity(0.0);
            box.getChildren().remove(success);
        });

        FadeTransition fade = new FadeTransition(Duration.seconds(1.5), waitingText);
        fade.setFromValue(0.0);
        fade.setToValue(1.0);
        fade.setAutoReverse(true);
        fade.setCycleCount(Animation.INDEFINITE);

        SequentialTransition sequentialTransition = new SequentialTransition(successTrans, fade);
        sequentialTransition.play();

        box.getChildren().addAll(success, waitingText);
        rootPane.getChildren().add(box);

        this.lobbyScene = new Scene(rootPane, stage.getScene().getWidth(), stage.getScene().getHeight());
        lobbyScene.getStylesheets().addAll(getClass().getResource("/assets/background.css").toExternalForm(),
                getClass().getResource("/assets/login_stage.css").toExternalForm());
        rootPane.requestFocus();
    }

    /**
     * Method to select the map, available only for the first player
     * @param selector tells if the selector is the first player (true) or another player (false)
     */
    public void mapSelection(boolean selector) {
        Platform.runLater(() -> {
            if (selector) {
                Text select = new Text(GUIController.getController().getNickname() + ", select the battlefield:");
                select.setId("text");
                HBox buttonBox = new HBox();
                buttonBox.setId("box");

                Button small = new Button("SMALL MAP");
                Button medium1 = new Button("MEDIUM MAP 1");
                Button medium2 = new Button("MEDIUM MAP 2");
                Button large = new Button("LARGE MAP");

                small.setId("map_button");
                medium1.setId("map_button");
                medium2.setId("map_button");
                large.setId("map_button");

                small.setOnMouseClicked(mouseEvent ->
                    GUIController.getController().sendChosenMap(SMALL)
                );

                medium1.setOnMouseClicked(mouseEvent ->
                    GUIController.getController().sendChosenMap(MEDIUM_1)
                );

                medium2.setOnMouseClicked(mouseEvent ->
                    GUIController.getController().sendChosenMap(MEDIUM_2)
                );

                large.setOnMouseClicked(mouseEvent ->
                    GUIController.getController().sendChosenMap(LARGE)
                );

                buttonBox.getChildren().addAll(small, medium1, medium2, large);
                box.getChildren().remove(waitingText);
                box.getChildren().addAll(select ,buttonBox);
            } else {
                box.getChildren().remove(waitingText);
                box.getChildren().add(waitingSelection);
            }
        });
    }

    /**
     * Getter method
     * @return the lobby scene
     */
    public Scene getLobbyScene() {
        return this.lobbyScene;
    }
}
