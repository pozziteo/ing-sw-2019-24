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

    private GUIController userController;

    private StackPane root;
    private BorderPane game;
    private List<Button> mapButtons;

    private CardLoader cardLoader;
    private Scene gameScene;

    public GameInterface(Stage stage) {

        this.userController = GUIController.getController();
        this.cardLoader = new CardLoader();
        this.root = new StackPane();
        root.setId("game_scene");
        this.game = new BorderPane();
        game.setCenter(new MapLoader(userController.getMap()).loadMap());
        game.setLeft(new BoardLoader("left").getLeftBoard());
        game.setRight(new BoardLoader("right").getRightBoard());
        game.setBottom(new BoardLoader("bottom").getBottomBoard());
        HBox topBoards = new HBox();
        topBoards.setId("box");
        topBoards.getChildren().addAll(new BoardLoader("topR").getTopBoardR(),
                new BoardLoader("topL").getTopBoardL());
        game.setTop(topBoards);

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
            Text select = new Text(userController.getNickname() + ", choose your spawnPoint:");
            select.setTextAlignment(TextAlignment.CENTER);
            select.setId("small_text");
            HBox imageBox = new HBox();
            imageBox.setId("box");

            List<ImageView> images = new ArrayList<>();
            for (PowerUpDetails powerUpDetails : powerups) {
                ImageView powerUp = cardLoader.loadCard(powerUpDetails.getType() + "_" + powerUpDetails.getColor());
                images.add(powerUp);
                powerUp.setOnMouseClicked(mouseEvent -> {
                    userController.sendChosenSpawnPoint(powerUpDetails.getColor());
                    root.getChildren().remove(box);
                });
            }

            imageBox.getChildren().addAll(images);
            box.getChildren().addAll(select, imageBox);
            root.getChildren().add(box);
        });
    }

    public void startTurn() {
        Platform.runLater( () -> {
            VBox box = new VBox();
            box.setId("actions-box");
            Text actionsSelect = new Text(userController.getNickname() + ", choose your next action!");
            actionsSelect.setTextAlignment(TextAlignment.CENTER);
            actionsSelect.setId("medium-text");
            HBox actionButtons = new HBox();
            actionButtons.setId("box");

            Button move = new Button("MOVE");
            move.setId("action-button");
            move.setOnMouseClicked(mouseEvent -> {
                userController.sendAction("move");
                userController.showMessage("Move action selected");
                root.getChildren().remove(box);
            });

            Button moveAndGrab = new Button("MOVE AND GRAB");
            moveAndGrab.setId("action-button");
            moveAndGrab.setOnMouseClicked(mouseEvent -> {
                userController.sendAction("move and grab");
                userController.showMessage("Move and Grab action selected");
                root.getChildren().remove(box);
            });

            Button shoot = new Button("SHOOT");
            shoot.setId("action-button");
            shoot.setOnMouseClicked(mouseEvent -> {
                userController.sendAction("shoot");
                userController.showMessage("Shoot action selected");
                root.getChildren().remove(box);
            });

            Button powerUp = new Button("POWER-UP");
            powerUp.setId("action-button");
            powerUp.setOnMouseClicked(mouseEvent -> {
                userController.sendAction("power up");
                userController.showMessage("Power-Up action selected");
                root.getChildren().remove(box);
            });

            Button pass = new Button("PASS TURN");
            pass.setId("action-button");
            pass.setOnMouseClicked(mouseEvent -> {
                userController.sendAction("pass");
                userController.showMessage("You are passing this turn");
                root.getChildren().remove(box);
            });

            actionButtons.getChildren().addAll(move, moveAndGrab, shoot, powerUp, pass);
            box.getChildren().addAll(actionsSelect, actionButtons);
            root.getChildren().add(box);
        });
    }

}
