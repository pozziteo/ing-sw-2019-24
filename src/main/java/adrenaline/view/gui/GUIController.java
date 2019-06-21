package adrenaline.view.gui;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.*;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.data.data_for_server.data_for_game.ActionBuilder;
import adrenaline.data.data_for_server.data_for_game.ChosenMapSetUp;
import adrenaline.data.data_for_server.data_for_game.ChosenSpawnPointSetUp;
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

    private String map;
    private ClientInterface client;
    private String nickname;
    private Map<String, String> playerColors;
    private List<String> nicks = new ArrayList<>();

    private final Object obj = new Object();

    private GUIController(Stage stage) {
        this.stage = stage;
    }

    public static GUIController getController() {
        return controller;
    }

    public static GUIController createController(Stage stage) {
        if (controller == null)
            controller = new GUIController(stage);
        return getController();
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setClient(ClientInterface client) {
        this.client = client;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public void setMap(String map) {
        this.map = map;
    }

    public String getMap() {
        return this.map;
    }

    public Map<String, String> getPlayerColors() {
        return this.playerColors;
    }

    public void setCurrentScene(Scene scene) {
        this.currentScene = scene;
        this.stage.setScene(currentScene);
    }

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

    @Override
    public void sendToServer(DataForServer data) {
        client.sendData(data);
    }

    @Override
    public void setUpAccount() {
        this.loginStage = new LoginStage(stage);
        Platform.runLater(() ->
                setCurrentScene(loginStage.getLoginScene()));
    }

    public void establishConnection() {
        this.connectionStage = new ConnectionStage(stage);
        Platform.runLater(() ->
                setCurrentScene(connectionStage.getConnectionScene()));
    }

    public void entryLobby() {
        this.lobbyStage = new LobbyStage(stage);
        Platform.runLater(() ->
                setCurrentScene(lobbyStage.getLobbyScene()));
    }

    public void selectMap(String firstPlayerNick, Map<String, String> playerColors) {
        this.playerColors = playerColors;
        showMessage("Your color is " + playerColors.get(getNickname()));
        boolean selector = nickname.equals(firstPlayerNick);
        lobbyStage.mapSelection(selector);
    }

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

    public void sendChosenMap(String map) {
        ChosenMapSetUp mapdata = new ChosenMapSetUp(nickname, map);
        sendToServer(mapdata);
    }

    public void sendChosenSpawnPoint(String spawnColor){
        ChosenSpawnPointSetUp spawndata = new ChosenSpawnPointSetUp(nickname, spawnColor);
        sendToServer (spawndata);
        showMessage("Your choice has been sent...\n");
    }

    public synchronized void initGame() {
        this.gameInterface = new GameInterface(stage);
        Platform.runLater(() ->
                setCurrentScene(gameInterface.initGame()));
        notifyAll();
    }

    public void showTurn(String nickname) {
        if (nickname.equals(this.nickname)) {
            showMessage("It's your turn!");
            gameInterface.startTurn();
        } else {
            showMessage(nickname + " is playing. Please wait your turn...");
        }
    }

    public void sendAction(String actionType) {
        ActionBuilder action = new ActionBuilder(nickname, actionType);
        sendToServer(action);
    }

    public void showPaths(List<Integer> paths) {
        gameInterface.showPaths(paths);
    }

    public void showPathsAndGrabOptions(List<Integer> paths, List<SquareDetails> map) {
        gameInterface.showPathsAndGrabOptions(paths, map);
    }

    public void chooseWeapon(List<WeaponDetails> weapons) {
        gameInterface.chooseWeapon(weapons);
    }

    public void chooseWeaponEffect(List<EffectDetails> effects) {
        gameInterface.chooseWeaponEffect(effects);
    }

    public void chooseTargets(List<TargetDetails> targets, List<String> compliantTargets, List<SquareDetails> map, boolean targetingScope) {
        gameInterface.chooseTargets(targets, compliantTargets, map, targetingScope);
    }

    public void askReload(List<WeaponDetails> weapons) {
        gameInterface.askReload(weapons);
    }

    public void choosePowerUp(List<PowerUpDetails> powerups) {
        gameInterface.choosePowerUp(powerups);
    }

    public void chooseSquare(List<Integer> validSquares) {
        gameInterface.chooseSquare(validSquares);
    }

    public void chooseSquareForTarget(List<String> targets, Map<String, List<Integer>> targetPaths) {
        gameInterface.chooseSquareForTarget(targets, targetPaths);
    }

    public void notifyTimeOut() {
        showMessage("Time is up. You took too long to make a choice.");
        gameInterface.timeOut();
    }
}
