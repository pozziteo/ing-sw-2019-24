package adrenaline.view.gui.game;

import adrenaline.data.data_for_client.responses_for_view.fake_model.PowerUpDetails;
import adrenaline.view.gui.GUIController;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameInterface {

    private StackPane root;
    private BorderPane game;
    private List<Button> mapButtons;

    private CardLoader cardLoader;
    private Scene gameScene;

    public GameInterface(Stage stage) {

        this.cardLoader = new CardLoader();
        this.root = new StackPane();
        root.setId("game_scene");
        this.game = new BorderPane();
        game.setCenter(new MapLoader(GUIController.getController().getMap()).loadMap());
//        game.setLeft(new BoardLoader("left").getLeftBoard());
//       game.setTop(new BoardLoader("topR").getTopBoard());
//        game.setRight(new BoardLoader("right").getRightBoard());
//        game.setBottom(new BoardLoader("bottom").getBottomBoard());

        GridPane mapPane = new GridPane();
        mapPane.setId("map_style");
        mapPane.getRowConstraints().add(new RowConstraints(140));
        mapPane.getRowConstraints().add(new RowConstraints(140));
        mapPane.getRowConstraints().add(new RowConstraints(140));

        this.mapButtons = new ArrayList<>();
        for (int i=0; i < 12; i++) {
            Button button = new Button(i/4 + ", " + i%4);
            button.setId("square");
            button.setDisable(true);
            mapPane.add(button, i%4, i/4);
            GridPane.setHalignment(button, HPos.CENTER);
            GridPane.setValignment(button, VPos.BOTTOM);
            mapButtons.add(button);
        }

        root.getChildren().add(game);
        root.getChildren().add(mapPane);
        this.gameScene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
        gameScene.getStylesheets().addAll(getClass().getResource("/assets/game_style.css").toExternalForm(),
                getClass().getResource("/assets/login_stage.css").toExternalForm());
        root.requestFocus();
    }

    public Scene initGame() {
        return this.gameScene;
    }

    public void selectSpawnPoint(List<PowerUpDetails> powerups){
        Platform.runLater( () -> {
            VBox box = new VBox();
            box.setId("spawn-box");
            Text select = new Text(GUIController.getController().getNickname() + ", choose your spawnPoint:");
            select.setTextAlignment(TextAlignment.CENTER);
            select.setId("small_text");
            HBox imageBox = new HBox();
            imageBox.setId("box");

            List<ImageView> images = new ArrayList<>();
            for (PowerUpDetails powerUpDetails : powerups) {
                ImageView powerUp = cardLoader.loadCard(powerUpDetails.getType() + "_" + powerUpDetails.getColor());
                images.add(powerUp);
                powerUp.setOnMouseClicked(mouseEvent -> {
                    GUIController.getController().sendChosenSpawnPoint(powerUpDetails.getColor());
                    root.getChildren().remove(box);
                });
            }

            imageBox.getChildren().addAll(images);
            box.getChildren().addAll(select, imageBox);
            root.getChildren().add(box);
        });
    }

    public void startTurn() {
        //TODO
    }

}
