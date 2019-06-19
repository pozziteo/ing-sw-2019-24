package adrenaline.view.gui.game;

import adrenaline.data.data_for_client.responses_for_view.fake_model.*;
import adrenaline.data.data_for_server.data_for_game.*;
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
    private VBox contextBox;
    private List<Button> mapButtons;
    private ImageView selectedWeapon;

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
        this.contextBox = new VBox();
        contextBox.setId("spawn-box");
        Text select = new Text(userController.getNickname() + ", choose your Spawn Point:");
        select.setId("small-text");
        HBox imageBox = new HBox();
        imageBox.setId("box");

        List<ImageView> images = new ArrayList<>();
        for (PowerUpDetails powerUpDetails : powerups) {
            ImageView powerUp = cardLoader.loadCard(powerUpDetails.getType() + "_" + powerUpDetails.getColor());
            images.add(powerUp);
            powerUp.setOnMouseClicked(mouseEvent -> {
                userController.sendChosenSpawnPoint(powerUpDetails.getColor());
                root.getChildren().remove(contextBox);
            });
        }

        imageBox.getChildren().addAll(images);
        contextBox.getChildren().addAll(select, imageBox);

        Platform.runLater( () -> root.getChildren().add(contextBox));
    }

    public void startTurn() {
        this.contextBox = new VBox();
        contextBox.setId("actions-box");
        Text actionsSelect = new Text(userController.getNickname() + ", choose your next action!");
        actionsSelect.setId("medium-text");
        HBox actionButtons = new HBox();
        actionButtons.setId("box");

        Button move = new Button("MOVE");
        move.setId("action-button");
        move.setOnMouseClicked(mouseEvent -> {
            userController.sendAction("move");
            userController.showMessage("Move action selected");
            root.getChildren().remove(contextBox);
        });

        Button moveAndGrab = new Button("MOVE AND GRAB");
        moveAndGrab.setId("action-button");
        moveAndGrab.setOnMouseClicked(mouseEvent -> {
            userController.sendAction("move and grab");
            userController.showMessage("Move and Grab action selected");
            root.getChildren().remove(contextBox);
        });

        Button shoot = new Button("SHOOT");
        shoot.setId("action-button");
        shoot.setOnMouseClicked(mouseEvent -> {
            userController.sendAction("shoot");
            userController.showMessage("Shoot action selected");
            root.getChildren().remove(contextBox);
        });

        Button powerUp = new Button("POWER-UP");
        powerUp.setId("action-button");
        powerUp.setOnMouseClicked(mouseEvent -> {
            userController.sendAction("power up");
            userController.showMessage("Power-Up action selected");
            root.getChildren().remove(contextBox);
        });

        Button pass = new Button("PASS TURN");
        pass.setId("action-button");
        pass.setOnMouseClicked(mouseEvent -> {
            userController.sendAction("pass");
            userController.showMessage("You are passing this turn");
            root.getChildren().remove(contextBox);
        });

        actionButtons.getChildren().addAll(move, moveAndGrab, shoot, powerUp, pass);
        contextBox.getChildren().addAll(actionsSelect, actionButtons);

        Platform.runLater( () -> root.getChildren().add(contextBox));
    }

    public void showPaths(List<Integer> paths) {
        this.contextBox = new VBox();
        contextBox.setId("actions-box");
        Text text = new Text(userController.getNickname() + ", choose the square to move to.");
        text.setId("medium-text");

        for (int i : paths) {
            mapButtons.get(i).setId("active-square");
            mapButtons.get(i).setDisable(false);
            mapButtons.get(i).setOnMouseClicked(mouseEvent -> {
                NewPosition newPosition = new NewPosition(userController.getNickname(), i);
                userController.sendToServer(newPosition);
                disableButtons(paths);
                root.getChildren().remove(contextBox);
            });
        }

        contextBox.getChildren().add(text);
        Platform.runLater(() -> {
            root.getChildren().add(contextBox);
            mapPane.toFront();
        });
    }

    public void showPathsAndGrabOptions(List<Integer> paths, List<SquareDetails> map) {
        this.contextBox = new VBox();
        contextBox.setId("actions-box");
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
                root.getChildren().remove(contextBox);
            });
        }

        contextBox.getChildren().add(text);
        Platform.runLater(() -> {
            root.getChildren().add(contextBox);
            mapPane.toFront();
        });
    }

    private void chooseWeapon(SpawnPointDetails square) {
        this.contextBox = new VBox();
        contextBox.setId("spawn-box");
        Text select = new Text(userController.getNickname() + ", choose a weapon from the Spawn Point:");
        select.setId("small-text");
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
                root.getChildren().remove(contextBox);
            });
        }

        imageBox.getChildren().addAll(weapons);
        contextBox.getChildren().addAll(select, imageBox);

        Platform.runLater( () -> root.getChildren().add(contextBox));
    }

    public void chooseWeapon(List<WeaponDetails> weapons) {
        if (!weapons.isEmpty()) {
            this.contextBox = new VBox();
            contextBox.setId("spawn-box");
            Text select = new Text(userController.getNickname() + ", choose a weapon to slaughter an enemy!");
            select.setId("medium-text");
            HBox weaponsBox = new HBox();
            weaponsBox.setId("box");

            List<ImageView> loadedWeapons = new ArrayList<>();
            for (WeaponDetails details : weapons) {
                ImageView weapon = cardLoader.loadCard(details.getName());
                loadedWeapons.add(weapon);
                weapon.setOnMouseClicked(mouseEvent -> {
                    this.selectedWeapon = weapon;
                    ChosenWeapon chosenWeapon = new ChosenWeapon(userController.getNickname(), details.getName());
                    userController.sendToServer(chosenWeapon);
                    root.getChildren().remove(contextBox);
                });
            }

            weaponsBox.getChildren().addAll(loadedWeapons);
            contextBox.getChildren().addAll(select, weaponsBox);

            Platform.runLater(() -> root.getChildren().add(contextBox));
        } else {
            userController.showMessage("You have no loaded weapons");
            userController.sendAction("end action");
        }
    }

    public void chooseWeaponEffect(List<EffectDetails> effects) {
        this.contextBox = new VBox();
        contextBox.setId("small-box");
        Text chooseEffect = new Text("Now " + userController.getNickname() + ", choose an effect and sow destruction " +
                "in the arena!");
        chooseEffect.setId("small-text");
        VBox weaponOptions = new VBox();
        weaponOptions.setId("small-box");
        GridPane buttonsPane = new GridPane();
        buttonsPane.setId("grid-box");

        int i = 1;
        int columnIndex = 0;
        for (EffectDetails effect : effects) {
            Button button = new Button();
            if (effect.getEffectType ().equals("base effect")) {
               button.setText("BASE EFFECT");
            } else if (effect.getEffectType ().equals("optional effect") && effect.isAlternativeMode ()) {
                button.setText("ALTERNATIVE MODE");
            } else if (effect.getEffectType ().equals ("optional effect")) {
                button.setText("OPTIONAL EFFECT " + i + "\n(usable before base: " + effect.isUsableBeforeBase() + ")" );
                i++;
            }
            button.setId("action-button");
            button.setOnMouseClicked(mouseEvent -> {
                ChosenEffect chosenEffect = new ChosenEffect(userController.getNickname(), effects.indexOf(effect));
                userController.sendToServer(chosenEffect);
                root.getChildren().remove(contextBox);
            });
            buttonsPane.add(button, columnIndex, 0);
            columnIndex++;
        }
        Button none = new Button("NONE");
        none.setId("action-button");
        none.setOnMouseClicked(mouseEvent -> {
            userController.sendAction("end action");
            userController.showMessage("Skipping action...");
            root.getChildren().remove(contextBox);
        });
        buttonsPane.getChildren().add(none);
        weaponOptions.getChildren().addAll(selectedWeapon, buttonsPane);
        contextBox.getChildren().addAll(chooseEffect, weaponOptions);

        Platform.runLater(() -> root.getChildren().add(contextBox));

    }

    public void choosePowerUp(List<PowerUpDetails> powerups) {
        this.contextBox = new VBox();
        contextBox.setId("small-box");
        Text choosePowerUp = new Text(userController.getNickname() + ", choose a Power-Up to use:");
        choosePowerUp.setId("medium-text");
        GridPane powerupPane = new GridPane();
        powerupPane.setId("grid-box");

        Button skip = new Button("NONE");
        skip.setId("action-button");
        skip.setOnMouseClicked(mouseEvent -> {
            userController.sendAction("end action");
            userController.showMessage("Skipping action...");
            root.getChildren().remove(contextBox);
        });

        int columnIndex = 0;
        for (PowerUpDetails details : powerups) {
            ImageView powerUp = cardLoader.loadCard(details.getType() + "_" + details.getColor());
            powerUp.setOnMouseClicked(mouseEvent -> {
                contextBox.getChildren().clear();
                Text bool = new Text("Use Power-Up as bonus ammo?");
                bool.setId("small-text");
                HBox buttonsBox = new HBox();
                buttonsBox.setId("small-box");

                Button yes = new Button("YES");
                yes.setId("action-button");
                yes.setOnMouseClicked(mouseEvent1 -> {
                    ChosenPowerUp chosen = new ChosenPowerUp(userController.getNickname(), details.getType(), true);
                    userController.sendToServer(chosen);
                    root.getChildren().remove(contextBox);
                });

                Button no = new Button("NO");
                no.setId("action-button");
                no.setOnMouseClicked(mouseEvent1 -> {
                    ChosenPowerUp chosen = new ChosenPowerUp(userController.getNickname(), details.getType(), false);
                    userController.sendToServer(chosen);
                    root.getChildren().remove(contextBox);
                });
                buttonsBox.getChildren().addAll(yes, no);
                contextBox.getChildren().addAll(bool, buttonsBox);
            });
            powerupPane.add(powerUp, columnIndex, 0);
            columnIndex++;
        }
        powerupPane.add(skip, columnIndex, 0);

        contextBox.getChildren().addAll(choosePowerUp, powerupPane);
        Platform.runLater(() -> root.getChildren().add(contextBox));
    }

    private void disableButtons(List<Integer> buttons) {
        for (int index : buttons) {
            mapButtons.get(index).setId("inactive-square");
            mapButtons.get(index).setDisable(true);
            mapButtons.get(index).setOnMouseClicked(null);
        }
    }

    public void timeOut() {
        Platform.runLater(() -> {
            if (contextBox != null && root.getChildren().contains(contextBox))
                root.getChildren().remove(contextBox);
            List<Integer> allButtons = new ArrayList<>();
            for (int index = 0; index < mapButtons.size(); index++)
                allButtons.add(index);
            disableButtons(allButtons);
        });
    }
}
