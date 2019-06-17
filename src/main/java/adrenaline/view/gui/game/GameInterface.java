package adrenaline.view.gui.game;

import adrenaline.data.data_for_client.responses_for_view.fake_model.PowerUpDetails;
import adrenaline.data.data_for_client.responses_for_view.fake_model.SpawnPointDetails;
import adrenaline.data.data_for_client.responses_for_view.fake_model.SquareDetails;
import adrenaline.data.data_for_client.responses_for_view.fake_model.WeaponDetails;
import adrenaline.data.data_for_server.data_for_game.NewPosition;
import adrenaline.data.data_for_server.data_for_game.NewPositionAndGrabbed;
import adrenaline.view.gui.GUIController;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

public class GameInterface {

    private GUIController userController;

    private StackPane root;
    private BorderPane game;
    private GridPane mapPane;
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

        this.mapPane = new GridPane();
        mapPane.setId("map_style");
        mapPane.getRowConstraints().add(new RowConstraints(140));
        mapPane.getRowConstraints().add(new RowConstraints(140));
        mapPane.getRowConstraints().add(new RowConstraints(140));

        this.mapButtons = new ArrayList<>();
        for (int i=0; i < 12; i++) {
            Button button = new Button("");
            button.setDisable(true);
            button.setId("inactive-square");
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
        VBox box = new VBox();
        box.setId("spawn-box");
        Text select = new Text(userController.getNickname() + ", choose your Spawn Point:");
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

        Platform.runLater( () -> root.getChildren().add(box));
    }

    public void startTurn() {
        VBox box = new VBox();
        box.setId("actions-box");
        Text actionsSelect = new Text(userController.getNickname() + ", choose your next action!");
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

        Platform.runLater( () -> root.getChildren().add(box));
    }

    public void showPaths(List<Integer> paths) {
        VBox box = new VBox();
        box.setId("actions-box");
        Text text = new Text(userController.getNickname() + ", choose the square to move to.");
        text.setId("medium-text");

        for (int i : paths) {
            mapButtons.get(i).setId("active-square");
            mapButtons.get(i).setDisable(false);
            mapButtons.get(i).setOnMouseClicked(mouseEvent -> {
                NewPosition newPosition = new NewPosition(userController.getNickname(), i);
                userController.sendToServer(newPosition);
                disableButtons(paths);
                root.getChildren().remove(box);
            });
        }

        box.getChildren().add(text);
        Platform.runLater(() -> {
            root.getChildren().add(box);
            mapPane.toFront();
        });
    }

    public void showPathsAndGrabOptions(List<Integer> paths, List<SquareDetails> map) {
        VBox box = new VBox();
        box.setId("actions-box");
        Text text = new Text(userController.getNickname() + ", choose the square to grab stuff from.");
        text.setId("medium-text");

        for (int i: paths) {
            mapButtons.get(i).setId("active-square");
            mapButtons.get(i).setDisable(false);
            mapButtons.get(i).setOnMouseClicked(mouseEvent -> {
                SquareDetails squareDetails = map.get(i);
                if (squareDetails.isSpawnPoint())
                    Platform.runLater(() -> chooseWeapon((SpawnPointDetails) squareDetails));
                else {
                    NewPosition newPosition = new NewPosition(userController.getNickname(), i);
                    userController.sendToServer(newPosition);
                }
                disableButtons(paths);
                root.getChildren().remove(box);
            });
        }

        box.getChildren().add(text);
        Platform.runLater(() -> {
            root.getChildren().add(box);
            mapPane.toFront();
        });
    }

    private void chooseWeapon(SpawnPointDetails square) {
        VBox box = new VBox();
        box.setId("spawn-box");
        Text select = new Text(userController.getNickname() + ", choose a weapon from the Spawn Point:");
        select.setId("small_text");
        HBox imageBox = new HBox();
        imageBox.setId("box");

        List<ImageView> weapons = new ArrayList<>();
        for (WeaponDetails details : square.getWeaponsOnSquare()) {
            ImageView weapon = cardLoader.loadCard(details.getName());
            weapons.add(weapon);
            weapon.setOnMouseClicked(mouseEvent -> {
                NewPositionAndGrabbed newPositionAndGrabbed = new NewPositionAndGrabbed
                        (userController.getNickname(), square.getId(), details.getName());
                userController.sendToServer(newPositionAndGrabbed);
                root.getChildren().remove(box);
            });
        }

        imageBox.getChildren().addAll(weapons);
        box.getChildren().addAll(select, imageBox);

        Platform.runLater( () -> root.getChildren().add(box));
    }

    private void disableButtons(List<Integer> buttons) {
        for (int index : buttons) {
            mapButtons.get(index).setId("inactive-square");
            mapButtons.get(index).setDisable(true);
            mapButtons.get(index).setOnMouseClicked(null);
        }
    }
}
