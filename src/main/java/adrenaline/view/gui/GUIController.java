package adrenaline.view.gui;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.*;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.data.data_for_server.data_for_game.ActionBuilder;
import adrenaline.data.data_for_server.data_for_game.ChosenMapSetUp;
import adrenaline.data.data_for_server.data_for_game.ChosenSpawnPointSetUp;
import adrenaline.data.data_for_server.requests_for_model.SquareDetailsRequest;
import adrenaline.network.ClientInterface;
import adrenaline.view.UserInterface;
import adrenaline.view.gui.game.GameInterface;
import adrenaline.view.gui.stages.ConnectionStage;
import adrenaline.view.gui.stages.LobbyStage;
import adrenaline.view.gui.stages.LoginStage;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

public class GUIController implements UserInterface {

    private static GUIController controller;
    private Stage stage;
    private Scene currentScene;
    private ConnectionStage connectionStage;
    private LoginStage loginStage;
    private LobbyStage lobbyStage;
    private GameInterface gameInterface;

    private Thread updatingThread;
    private boolean updated;

    private String map;
    private ClientInterface client;
    private String nickname;
    private Map<String, String> playerColors;

    private final Object obj = new Object();

    private GUIController(Stage stage) {
        this.stage = stage;
    }

    /**
     * Getter method
     * @return the controller
     */
    public static GUIController getController() {
        return controller;
    }

    /**
     * Method that creates a new GUIController
     * @param stage is the stage
     * @return the controller
     */
    public static GUIController createController(Stage stage) {
        if (controller == null)
            controller = new GUIController(stage);
        return getController();
    }

    /**
     * Getter method
     * @return the nickname of the controller's user
     */
    public String getNickname() {
        return this.nickname;
    }

    /**
     * Method to set the client
     * @param client is the client
     */
    public void setClient(ClientInterface client) {
        this.client = client;
    }

    /**
     * Method to set the nickname
     * @param nickname is the name given by the player
     */
    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    /**
     * Method to set the game's map
     * @param map is the name of the map
     */
    public void setMap(String map) {
        this.map = map;
    }

    /**
     * Getter method
     * @return the name of the game's map
     */
    public String getMap() {
        return this.map;
    }

    /**
     * Getter method
     * @return a map of a player's name and color
     */
    public Map<String, String> getPlayerColors() {
        return this.playerColors;
    }

    /**
     * Method to //TODO
     */
    public synchronized void setUpdated() {
        synchronized (obj) {
            this.updated = true;
            obj.notifyAll();
        }
    }

    /**
     * Method that set the current scene
     * @param scene is the scene to show
     */
    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
        this.stage.setScene(currentScene);
    }

    /**
     * Method to show a message to the player
     * @param message is the message that is shown
     */
    public void showMessage(String message) {
        updatingThread = Thread.currentThread();
        Platform.runLater(() -> {
            synchronized (obj) {
                Text text = new Text(message);
                currentScene.getStylesheets().add(getClass().getResource("/assets/message_popup.css").toExternalForm());
                text.setId("message");
                VBox box = new VBox();
                box.setId("box-message");
                box.getChildren().add(text);
                FadeTransition transition = new FadeTransition(Duration.seconds(1.2), box);
                transition.setFromValue(0.0);
                transition.setToValue(1.0);
                transition.setAutoReverse(true);
                transition.setCycleCount(2);
                StackPane pane = (StackPane) currentScene.getRoot();
                pane.getChildren().add(box);
                transition.play();
                transition.setOnFinished(actionEvent -> {
                    synchronized (obj) {
                        pane.getChildren().remove(box);
                            updatingThread = null;
                            obj.notifyAll();
                        }
                    });
                }
            });
    }

    /**
     * Method to update the view
     * @param data that has to be updated
     */
    @Override
    public void updateView(DataForClient data) {
        Runnable thread = () -> {
            synchronized (obj) {
                try {
                    while (updatingThread != null) {
                        obj.wait();
                    }
                } catch (InterruptedException exc) {
                    Thread.currentThread().interrupt();
                }
                data.updateView(this);
            }
        };
        Thread receiverThread = new Thread(thread);
        receiverThread.start();
    }

    /**
     * Method to send data to the server
     * @param data to send
     */
    @Override
    public void sendToServer(DataForServer data) {
        client.sendData(data);
    }

    /**
     * Method to set up an account in the login scene
     */
    @Override
    public void setUpAccount() {
        this.loginStage = new LoginStage(stage);
        Platform.runLater(() ->
                setCurrentScene(loginStage.getLoginScene()));
    }

    /**
     * Method to establish the connection
     */
    public void establishConnection() {
        this.connectionStage = new ConnectionStage(stage);
        Platform.runLater(() ->
                setCurrentScene(connectionStage.getConnectionScene()));
    }

    /**
     * Method to enter the lobby scene
     */
    public void entryLobby() {
        this.lobbyStage = new LobbyStage(stage);
        Platform.runLater(() ->
                setCurrentScene(lobbyStage.getLobbyScene()));
    }

    /**
     * Method to select the map for the game
     * @param firstPlayerNick is the name of the first player (the one who decide the map)
     * @param playerColors is the map that contains the player's nickname and color
     */
    public void selectMap(String firstPlayerNick, Map<String, String> playerColors) {
        this.playerColors = playerColors;
        showMessage("Your color is " + playerColors.get(getNickname()));
        boolean selector = nickname.equals(firstPlayerNick);
        lobbyStage.mapSelection(selector);
    }

    /**
     * Method to set the spawn point selection
     * @param powerUps lets you decide where you want to spawn
     */
    public synchronized void chooseSpawnPoint(List<PowerUpDetails> powerUps){
        try {
            while (gameInterface == null)
                wait();
        } catch (InterruptedException exc) {
            exc.printStackTrace();
            Thread.currentThread().interrupt();
        }
        gameInterface.selectSpawnPoint(powerUps);
    }

    /**
     * Method to send the chosen map to the server
     * @param map is the name of the chosen map
     */
    public void sendChosenMap(String map) {
        ChosenMapSetUp mapdata = new ChosenMapSetUp(nickname, map);
        sendToServer(mapdata);
    }

    /**
     * Method to send to the server the data of the player's spawn location
     * @param spawnColor is the color of the spawn
     */
    public void sendChosenSpawnPoint(String spawnColor){
        ChosenSpawnPointSetUp spawndata = new ChosenSpawnPointSetUp(nickname, spawnColor);
        sendToServer (spawndata);
        showMessage("Your choice has been sent...\n");
    }

    /**
     * TODO
     */
    public synchronized void initGame() {
        this.gameInterface = new GameInterface(stage);
        Platform.runLater(() ->
                setCurrentScene(gameInterface.initGame()));
        notifyAll();
    }

    /**
     * Method that tells the player if it's his turn or not
     * @param nickname is the player to show the message
     */
    public void showTurn(String nickname) {
        updated = false;
        sendToServer(new SquareDetailsRequest(this.nickname));
        synchronized (obj) {
            try {
                while (!updated) {
                    obj.wait();
                }
            } catch (InterruptedException exc) {
                exc.printStackTrace();
                Thread.currentThread().interrupt();
            }
        }
        if (nickname.equals(this.nickname)) {
            showMessage("It's your turn!");
            gameInterface.startTurn();
        } else {
            showMessage(nickname + " is playing. Please wait your turn...");
        }
    }

    /**
     * Method to update the map and its details
     * @param map is the list of square details
     */
    public void updateMap(List<SquareDetails> map) {
        gameInterface.updateMap(map);
    }

    /**
     * Method to send to the server the chosen action
     * @param actionType is the name of the chose action
     */
    public void sendAction(String actionType) {
        ActionBuilder action = new ActionBuilder(nickname, actionType);
        sendToServer(action);
    }

    /**
     * Method that tells the player where he can move
     * @param paths is the list of squares the player can reach
     */
    public void showPaths(List<Integer> paths) {
        gameInterface.showPaths(paths);
    }

    /**
     * Method that tells the player where he can move and grab
     * @param paths is the list of reachable squares
     * @param map is the list of square details
     */
    public void showPathsAndGrabOptions(List<Integer> paths, List<SquareDetails> map) {
        gameInterface.showPathsAndGrabOptions(paths, map);
    }

    /**
     * Method to choose a weapon
     * @param weapons is the list of weapons
     */
    public void chooseWeapon(List<WeaponDetails> weapons) {
        gameInterface.chooseWeapon(weapons);
    }

    /**
     * Method to choose the effect of weapon you want to perform
     * @param effects is the list of possible effects
     */
    public void chooseWeaponEffect(List<EffectDetails> effects) {
        gameInterface.chooseWeaponEffect(effects);
    }

    /**
     * Method to choose the targets to shoot
     * @param targets is the list of targets
     * @param compliantTargets is the list of compliant targets
     * @param map is the list of square details (they tells if a player is in the square or not)
     * @param targetingScope is a boolean to apply the targeting scope
     */
    public void chooseTargets(List<TargetDetails> targets, List<String> compliantTargets, List<SquareDetails> map, boolean targetingScope) {
        gameInterface.chooseTargets(targets, compliantTargets, map, targetingScope);
    }

    /**
     * Method to ask the player if he wants to reload
     * @param weapons is the list of unloaded weapons
     */
    public void askReload(List<WeaponDetails> weapons) {
        gameInterface.askReload(weapons);
    }

    /**
     * Method to choose the power up
     * @param powerups is the list of power ups
     */
    public void choosePowerUp(List<PowerUpDetails> powerups) {
        gameInterface.choosePowerUp(powerups);
    }

    /**
     * Method to choose a valid sqaure
     * @param validSquares is the list of squares
     */
    public void chooseSquare(List<Integer> validSquares) {
        gameInterface.chooseSquare(validSquares);
    }

    /**
     * Method to choose the new position of an enemy
     * @param targets is the list of targets
     * @param targetPaths is the list of squares a player can be moved to
     */
    public void chooseSquareForTarget(List<String> targets, Map<String, List<Integer>> targetPaths) {
        gameInterface.chooseSquareForTarget(targets, targetPaths);
    }

    /**
     * Method to ask the player if he wants to use the tagback grenade
     * @param attackerName is the name of the attacker
     */
    public void askTagback(String attackerName) {
        gameInterface.askTagback(attackerName);
    }

    /**
     * Method to ask the player what weapon he wants to discard
     * @param weapons is the list of weapons to discard
     */
    public void discardWeapon(List<WeaponDetails> weapons) {
        gameInterface.discardWeapon(weapons);
    }

    /**
     * Method that shows the end game with the ranking
     * @param finalRanking is the final ranking
     */
    public void showEndGameScreen(List<String> finalRanking) {
        Collections.reverse(finalRanking);
        gameInterface.showEndGameScreen(finalRanking);
    }

    /**
     * Method to inform a player that he took to much time to make an action.
     * The turn passes to the next player
     */
    public void notifyTimeOut() {
        showMessage("Time is up. You took too long to make a choice.");
        gameInterface.timeOut();
    }
}
