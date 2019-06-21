package adrenaline.view.gui.game;

import adrenaline.data.data_for_client.responses_for_view.fake_model.*;
import adrenaline.data.data_for_server.data_for_game.*;
import adrenaline.view.gui.GUIController;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GameInterface {

    private GUIController userController;

    private StackPane root;
    private BorderPane game;
    private GridPane mapPane;
    private VBox contextBox;
    private List<Button> mapButtons;
    private ImageView cachedImage;
    private List<BoardLoader> boards;
    private List<Button> skulls;
    private List<Button> overkill;

    private FiguresLoader figuresLoader;
    private CardLoader cardLoader;
    private Scene gameScene;

    private List<AtomicTarget> chosenTargets;
    private final Object locker = new Object();

    public GameInterface(Stage stage) {

        this.userController = GUIController.getController();
        this.figuresLoader = new FiguresLoader();
        this.cardLoader = new CardLoader();
        this.boards = new ArrayList<>();
        this.skulls = new SkullsLoader().getSkullsList();
        this.overkill = new SkullsLoader().getOverkill();
        this.root = new StackPane();
        root.setId("game_scene");

        List<String> nicknames = new ArrayList<>(userController.getPlayerColors().keySet());
        HBox topBoards = new HBox();
        topBoards.setId("box");
        for (int i=0; i < userController.getPlayerColors().keySet().size(); i++) {
            String nickname;
            if (i == 0) {
                nickname = userController.getNickname();
            } else {
                nickname = nicknames.get(0);
            }
            BoardLoader boardLoader = new BoardLoader(nickname);
            boards.add(boardLoader);
            if (i > 2) {
                if (i == 3)
                    topBoards.getChildren().add(boardLoader.getTopBoardL());
                else
                    topBoards.getChildren().add(boardLoader.getTopBoardR());
            }
            nicknames.remove(nickname);
        }
        ImageView map = new MapLoader(userController.getMap()).loadMap();
        this.game = new BorderPane(map, topBoards, boards.get(2).getRightBoard(), boards.get(0).getBottomBoard(), boards.get(1).getLeftBoard());

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
            if (boards.size() > 3)
                GridPane.setValignment(button, VPos.BOTTOM);
            else
                GridPane.setValignment(button, VPos.TOP);
            mapButtons.add(button);
        }

        GridPane skullsPane = new SkullsLoader().getSkullsPane();
        if(boards.size()>3){
            skullsPane.setId("more_skulls_style");
        }else{
            skullsPane.setId("skulls_style");
        }
        root.getChildren().add(game);
        root.getChildren().add(mapPane);
        root.getChildren().add(skullsPane);
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
                disableButtons();
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
                SquareDetails squareDetails = map.stream().filter(details -> details.getId() == i).findFirst().get();
                if (squareDetails.isSpawnPoint())
                    Platform.runLater(() -> chooseWeapon((SpawnPointDetails) squareDetails));
                else {
                    NewPosition newPosition = new NewPosition(userController.getNickname(), i);
                    userController.sendToServer(newPosition);
                }
                disableButtons();
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
                    this.cachedImage = weapon;
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
        buttonsPane.add(none, columnIndex, 0);
        weaponOptions.getChildren().addAll(cachedImage, buttonsPane);
        contextBox.getChildren().addAll(chooseEffect, weaponOptions);

        Platform.runLater(() -> root.getChildren().add(contextBox));

    }

    public void chooseTargets(List<TargetDetails> targets, List<String> compliantTargets, List<SquareDetails> map, boolean hasTargetingScope) {
        this.chosenTargets = new ArrayList<>();
        boolean invalid = false;
        boolean canTargetScope = false;
        synchronized (locker) {
            for (TargetDetails target : targets) {
                int actualSize = chosenTargets.size();
                if (target.getValue() != -1) {
                    canTargetScope = true;
                    Platform.runLater(() -> chooseTargets(target.getValue(), target.getMovements(), target.isArea(), compliantTargets, map, chosenTargets));
                } else if (target.getValue () == -1 && target.isArea ()) {
                    canTargetScope = true;
                    Platform.runLater(() -> chooseAreaToTarget(compliantTargets, map));
                } else if (target.getValue () == -1 && !target.isArea () && target.getMovements () == -1) {
                    chosenTargets.add(new AtomicTarget(null, -1));
                } else if (target.getValue () == -1 && !target.isArea () && target.getMovements () > 0) {
                    Platform.runLater(() -> chooseHowManyMovements(target.getMovements (), compliantTargets, map));
                }
                try {
                    while (actualSize == chosenTargets.size()) {
                        locker.wait();
                    }
                } catch (InterruptedException exc) {
                    exc.printStackTrace();
                    Thread.currentThread().interrupt();
                }
                if (chosenTargets.get(chosenTargets.size()-1) == null) {
                    invalid = true;
                    userController.sendAction ("end action");
                    userController.showMessage("Error: you built an illegal action. Automatically skipping this action...");
                    break;
                }
            }
        }
        if (!invalid) {
            if (hasTargetingScope && canTargetScope) {
                this.contextBox = new VBox();
                contextBox.setId("small-box");
                Text choose = new Text("Use Targeting Scope to deal additional damage to a target?");
                choose.setId("medium-text");
                HBox buttonsBox = new HBox();
                buttonsBox.setId("small-box");

                Button yes = new Button("YES");
                yes.setId("action-button");
                yes.setOnMouseClicked(mouseEvent -> {
                    contextBox.getChildren().clear();
                    Text text = new Text("Choose one target and make him suffer more!");
                    text.setId("medium-text");
                    HBox imageBox = new HBox();
                    imageBox.setId("small-box");

                    List<String> targetNames = new ArrayList<>();
                    for (AtomicTarget target : chosenTargets) {
                        List<String> atomicNames = target.getTargetNames();
                        for (String name : atomicNames)
                            if (!targetNames.contains(name))
                                targetNames.add(name);
                    }
                    for (String target : targetNames) {
                        ImageView imageView = figuresLoader.loadFigure(userController.getPlayerColors().get(target));
                        imageView.setOnMouseClicked(mouseEvent1 -> {
                            userController.sendToServer(new ChosenTargets(userController.getNickname(), this.chosenTargets, target));
                            root.getChildren().remove(contextBox);
                        });
                        imageBox.getChildren().add(imageView);
                    }
                    contextBox.getChildren().addAll(text, imageBox);
                });

                Button no = new Button("NO");
                no.setId("action-button");
                no.setOnMouseClicked(mouseEvent -> {
                    userController.sendToServer (new ChosenTargets (userController.getNickname(), chosenTargets, null));
                    root.getChildren().remove(contextBox);
                });
                buttonsBox.getChildren().addAll(yes, no);
                contextBox.getChildren().addAll(choose, buttonsBox);
                Platform.runLater(() -> root.getChildren().add(contextBox));
            }
            else
                userController.sendToServer (new ChosenTargets (userController.getNickname(), chosenTargets, null));
        }
    }

    private void chooseTargets(int maxAmount, int movements, boolean isArea, List<String> compliantTargets,
                              List<SquareDetails> map, List<AtomicTarget> chosenTargets) {
        this.contextBox = new VBox();
        contextBox.setId("small-box");
        Text choose = new Text("Choose up to " + maxAmount + " targets and give them some pain!");
        choose.setId("medium-text");
        GridPane grid = new GridPane();
        grid.setId("grid-box");

        List<String> addedTargets = new ArrayList<>();

        Button finish = new Button("FINISH");
        finish.setId("action-button");
        finish.setOnMouseClicked(mouseEvent -> {
            synchronized (locker) {
                contextBox.getChildren().clear();
                if (!addedTargets.isEmpty()) {
                    if (movements != -1) {
                        Text select = new Text("Select a square to move your targets!\n(Max distance of " + movements + ")");
                        select.setId("medium-text");
                        for (SquareDetails details : map) {
                            mapButtons.get(details.getId()).setId("active-square");
                            mapButtons.get(details.getId()).setDisable(false);
                            mapButtons.get(details.getId()).setOnMouseClicked(mouseEvent1 -> {
                                synchronized (locker) {
                                    chosenTargets.add(new AtomicTarget(addedTargets, details.getId()));
                                    disableButtons();
                                    root.getChildren().remove(contextBox);
                                    locker.notifyAll();
                                }
                            });
                        }
                        contextBox.getChildren().add(select);
                        mapPane.toFront();
                    } else if (isArea) {
                        int n = getTargetPos(addedTargets.get(0), map);
                        chosenTargets.add(new AtomicTarget(addedTargets, n));
                        locker.notifyAll();
                    } else {
                        chosenTargets.add(new AtomicTarget(addedTargets, -1));
                        locker.notifyAll();
                    }
                } else {
                    chosenTargets.add(null);
                    locker.notifyAll();
                }
            }
        });

        int columnIndex = 0;
        List<ImageView> targets = new ArrayList<>();
        if (compliantTargets != null) {
            for (String validTarget : compliantTargets) {
                ImageView target = figuresLoader.loadFigure(userController.getPlayerColors().get(validTarget));
                target.setOnMouseClicked(mouseEvent -> {
                    addedTargets.add(validTarget);
                    if (addedTargets.size() == maxAmount) {
                        for (ImageView image : targets) {
                            image.setOpacity(0.4);
                            image.setDisable(true);
                        }
                    } else {
                        target.setOpacity(0.4);
                        target.setDisable(true);
                    }
                });
                targets.add(target);
                grid.add(target, columnIndex, 0);
                columnIndex++;
            }
        }
        grid.add(finish, columnIndex, 0);
        contextBox.getChildren().addAll(choose, grid);

        Platform.runLater(() -> root.getChildren().add(contextBox));
    }

    private int getTargetPos(String target, List<SquareDetails> map) {
        for (SquareDetails square : map) {
            if (square.getPlayersOnSquare().contains(target)) {
                return square.getId();
            }
        }
        return -1;
    }

    private void chooseAreaToTarget(List<String> compliantTargets, List<SquareDetails> map) {
        if (compliantTargets != null && compliantTargets.isEmpty()) {
            synchronized (locker) {
                userController.showMessage("You can't hit targets with this effect");
                chosenTargets.add(null);
                locker.notifyAll();
                return;
            }
        }
        this.contextBox = new VBox();
        contextBox.setId("small-box");
        Text select = new Text("Select the square/room you want to target!");
        select.setId("medium-text");

        activateSquareEffect(map);
        /*for (SquareDetails details : map) {
            mapButtons.get(details.getId()).setId("active-square");
            mapButtons.get(details.getId()).setDisable(false);
            mapButtons.get(details.getId()).setOnMouseClicked(mouseEvent -> {
                synchronized (locker) {
                    chosenTargets.add(new AtomicTarget(null, details.getId()));
                    disableButtons();
                    root.getChildren().remove(contextBox);
                    locker.notifyAll();
                }
            });
        } */
        contextBox.getChildren().add(select);
        Platform.runLater(() -> {
            root.getChildren().add(contextBox);
            mapPane.toFront();
        });
    }

    private void chooseHowManyMovements(int movements, List<String> compliantTargets, List<SquareDetails> map) {
        if (compliantTargets != null && compliantTargets.isEmpty()) {
            synchronized (locker) {
                chosenTargets.add(null);
                userController.showMessage("You can't hit targets with this effect");
                locker.notifyAll();
                return;
            }
        }
        this.contextBox = new VBox();
        contextBox.setId("small-box");
        Text select = new Text("Choose the square you want to move to!\n(Max " + movements + " distance)");
        select.setId("medium-text");

        activateSquareEffect(map);
        /*for (SquareDetails details : map) {
            mapButtons.get(details.getId()).setId("active-square");
            mapButtons.get(details.getId()).setDisable(false);
            mapButtons.get(details.getId()).setOnMouseClicked(mouseEvent -> {
                synchronized (locker) {
                    chosenTargets.add(new AtomicTarget(null, details.getId()));
                    disableButtons();
                    root.getChildren().remove(contextBox);
                    locker.notifyAll();
                }
            });
        } */
        contextBox.getChildren().add(select);
        Platform.runLater(() -> {
            root.getChildren().add(contextBox);
            mapPane.toFront();
        });
    }

    private void activateSquareEffect(List<SquareDetails> map) {
        for (SquareDetails details : map) {
            mapButtons.get(details.getId()).setId("active-square");
            mapButtons.get(details.getId()).setDisable(false);
            mapButtons.get(details.getId()).setOnMouseClicked(mouseEvent -> {
                synchronized (locker) {
                    chosenTargets.add(new AtomicTarget(null, details.getId()));
                    disableButtons();
                    root.getChildren().remove(contextBox);
                    locker.notifyAll();
                }
            });
        }
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

    public void chooseSquare(List<Integer> validSquares) {
        this.contextBox = new VBox();
        contextBox.setId("small-box");
        Text select = new Text("Select the square to apply the effect");
        select.setId("medium-text");

        for (int square : validSquares) {
            mapButtons.get(square).setId("active-square");
            mapButtons.get(square).setDisable(false);
            mapButtons.get(square).setOnMouseClicked(mouseEvent -> {
                ChosenPowerUpEffect effect = new ChosenPowerUpEffect(userController.getNickname(), square);
                userController.sendToServer(effect);
                disableButtons();
                root.getChildren().remove(contextBox);
            });
        }

        contextBox.getChildren().add(select);
        Platform.runLater(() -> {
            root.getChildren().add(contextBox);
            mapPane.toFront();
        });
    }

    public void chooseSquareForTarget(List<String> targets, Map<String, List<Integer>> possiblePaths) {
        this.contextBox = new VBox();
        contextBox.setId("small-box");
        Text targetSelect = new Text("Select a target to move");
        targetSelect.setId("medium-text");
        GridPane targetsBox = new GridPane();
        targetsBox.setId("grid-box");

        int columnIndex = 0;
        for (String validTarget : targets) {
            ImageView target = figuresLoader.loadFigure(userController.getPlayerColors().get(validTarget));
            target.setOnMouseClicked(mouseEvent -> {
                contextBox.getChildren().clear();
                Text move = new Text("Move your target to a new position. Choose wisely!");
                move.setId("medium-text");
                List<Integer> validPaths = possiblePaths.get(validTarget);
                for (int validSquare : validPaths) {
                    Button square = mapButtons.get(validSquare);
                    square.setId("active-square");
                    square.setDisable(false);
                    square.setOnMouseClicked(mouseEvent1 -> {
                        ChosenPowerUpEffect effect = new ChosenPowerUpEffect(userController.getNickname(), validTarget, validSquare);
                        userController.sendToServer(effect);
                        disableButtons();
                        root.getChildren().remove(contextBox);
                    });
                }
                contextBox.getChildren().add(move);
                mapPane.toFront();
            });
            targetsBox.add(target, columnIndex, 0);
            columnIndex++;
        }

        contextBox.getChildren().addAll(targetSelect, targetsBox);
        Platform.runLater(() -> root.getChildren().add(contextBox));
    }

    public void askReload(List<WeaponDetails> weapons) {
        this.contextBox = new VBox();
        contextBox.setId("small-box");
        Text choose = new Text("Reload your weapons before ending turn?");
        choose.setId("medium-text");
        HBox buttonsBox = new HBox();
        buttonsBox.setId("small-box");

        Button yes = new Button("YES");
        yes.setId("action-button");

        Button no = new Button("NO");
        no.setId("action-button");

        yes.setOnMouseClicked(mouseEvent -> {
            contextBox.getChildren().clear();
            Text ask = new Text("Choose the weapon to reload");
            ask.setId("medium-text");
            GridPane weaponsBox = new GridPane();
            weaponsBox.setId("small-box");

            int columnIndex = 0;
            for (WeaponDetails details : weapons) {
                ImageView weapon = cardLoader.loadCard(details.getName());
                weapon.setOnMouseClicked(mouseEvent1 -> {
                    ReloadResponse response = new ReloadResponse(userController.getNickname(), true, details.getName());
                    userController.sendToServer(response);
                    root.getChildren().remove(contextBox);
                });
                weaponsBox.add(weapon, columnIndex, 0);
                columnIndex++;
            }
            Button finish = new Button("NONE");
            finish.setId("action-button");
            finish.setOnMouseClicked(mouseEvent1 -> {
                ReloadResponse response = new ReloadResponse(userController.getNickname(), false, "");
                userController.sendToServer(response);
                root.getChildren().remove(contextBox);
            });
            weaponsBox.add(finish, columnIndex, 0);

            contextBox.getChildren().addAll(ask, weaponsBox);
        });

        no.setOnMouseClicked(mouseEvent -> {
            ReloadResponse response = new ReloadResponse(userController.getNickname(), false, "");
            userController.sendToServer(response);
            root.getChildren().remove(contextBox);
        });
        buttonsBox.getChildren().addAll(yes, no);
        contextBox.getChildren().addAll(choose, buttonsBox);

        Platform.runLater(() -> root.getChildren().add(contextBox));
    }

    public void askTagback(String attackerName) {
        this.contextBox = new VBox();
        contextBox.setId("small-box");
        Text notify = new Text(attackerName + " just hit you!\nUse your Tagback Grenade to give him a mark and take revenge later?");
        notify.setId("medium-text");
        HBox buttonsBox = new HBox();
        buttonsBox.setId("small-box");

        Button yes = new Button("YES");
        yes.setId("action-button");
        yes.setOnMouseClicked(mouseEvent -> {
            TagbackResponse response = new TagbackResponse(userController.getNickname(), attackerName, true);
            userController.sendToServer(response);
            root.getChildren().remove(contextBox);
        });

        Button no = new Button("NO");
        no.setId("action-button");
        no.setOnMouseClicked(mouseEvent -> {
            TagbackResponse response = new TagbackResponse(userController.getNickname(), attackerName, false);
            userController.sendToServer(response);
            root.getChildren().remove(contextBox);
        });

        buttonsBox.getChildren().addAll(yes, no);
        contextBox.getChildren().addAll(notify, buttonsBox);

        Platform.runLater(() -> root.getChildren().add(contextBox));
    }

    public void discardWeapon(List<WeaponDetails> weapons) {
        this.contextBox = new VBox();
        contextBox.setId("small-box");
        Text ask = new Text("You have too many weapons! To pick this you must discard one first.");
        ask.setId("medium-text");
        HBox weaponsBox = new HBox();
        weaponsBox.setId("small-box");

        for (WeaponDetails weapon : weapons) {
            ImageView imageWeapon = cardLoader.loadCard(weapon.getName());
            imageWeapon.setOnMouseClicked(mouseEvent -> {
                DiscardedWeapon response = new DiscardedWeapon(userController.getNickname(), weapon.getName());
                userController.sendToServer(response);
                root.getChildren().remove(contextBox);
            });
            weaponsBox.getChildren().add(imageWeapon);
        }

        contextBox.getChildren().addAll(ask, weaponsBox);

        Platform.runLater(() -> root.getChildren().add(contextBox));
    }

    public void showEndGameScreen(List<String> finalRanking) {
        this.contextBox = new VBox();
        contextBox.setId("ranking-style");
        Text ranking = new Text("FINAL RANKING");
        ranking.setId("medium-text");

        GridPane rank = new GridPane();
        rank.setId("small-box");
        for (int position = 1;  position <= finalRanking.size(); position++) {
            ImageView playerFigure = figuresLoader.loadFigure(userController.getPlayerColors().get(finalRanking.get(position-1)));
            Text playerName = new Text(finalRanking.get(position-1));
            playerName.setId("medium-text");

            rank.add(playerFigure, 0, position-1);
            rank.add(playerName, 1, position-1);
        }

        Button finish = new Button("EXIT");
        finish.setId("action-button");
        finish.setOnMouseClicked(mouseEvent -> {
            if (!finalRanking.get(0).equals(userController.getNickname()))
                Platform.exit();
            else {
                try {
                    contextBox.getChildren().clear();
                    Text winner = new Text("YOU WON!");
                    winner.setId("text");
                    ImageView parrot = new ImageView(new Image(new FileInputStream("src" + File.separatorChar + "Resources"
                            + File.separatorChar + "images" + File.separatorChar + "partyparrot.gif")));
                    parrot.setPreserveRatio(true);
                    parrot.setFitWidth(400);
                    Button exit = new Button("EXIT");
                    exit.setId("action-button");
                    exit.setOnMouseClicked(mouseEvent1 -> Platform.exit());

                    contextBox.getChildren().addAll(winner, parrot, exit);
                } catch (FileNotFoundException exc) {
                    exc.printStackTrace();
                }
            }
        });

        contextBox.getChildren().addAll(ranking, rank, finish);

        Platform.runLater(() -> {
            root.getChildren().clear();
            root.getChildren().add(contextBox);
        });

    }

    private void disableButtons() {
        for (Button button : mapButtons) {
            button.setId("inactive-square");
            button.setDisable(true);
            button.setOnMouseClicked(null);
        }
    }

    public void timeOut() {
        Platform.runLater(() -> {
            if (contextBox != null && root.getChildren().contains(contextBox))
                root.getChildren().remove(contextBox);
            disableButtons();
        });
    }
}
