package adrenaline.view.gui;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_client.responses_for_view.fake_model.PowerUpDetails;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.data.data_for_server.data_for_game.ChosenMapSetUp;
import adrenaline.data.data_for_server.data_for_game.ChosenSpawnPointSetUp;
import adrenaline.model.GameModel;
import adrenaline.network.ClientInterface;
import adrenaline.view.UserInterface;
import adrenaline.view.gui.game.GameInterface;
import adrenaline.view.gui.stages.ConnectionStage;
import adrenaline.view.gui.stages.LobbyStage;
import adrenaline.view.gui.stages.LoginStage;
import javafx.animation.FadeTransition;
import javafx.application.Platform;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

import java.util.List;

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
                text.setTextAlignment(TextAlignment.CENTER);
                BorderPane border = new BorderPane();
                HBox box = new HBox();
                box.setAlignment(Pos.CENTER);
                box.getChildren().add(text);
                border.setBottom(box);
                FadeTransition transition = new FadeTransition(Duration.seconds(2.0), box);
                transition.setFromValue(0.0);
                transition.setToValue(1.0);
                transition.setAutoReverse(true);
                transition.setCycleCount(2);
                StackPane pane = (StackPane) currentScene.getRoot();
                pane.getChildren().add(border);
                transition.play();
                transition.setOnFinished(actionEvent -> {
                    synchronized (obj) {
                        pane.getChildren().remove(border);
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

    public void selectMap(String firstPlayerNick) {
        boolean selector = nickname.equals(firstPlayerNick);
        lobbyStage.mapSelection(selector);
    }

    public void chooseSpawnPoint(List<PowerUpDetails> powerUps){
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

    public void initGame() {
        this.gameInterface = new GameInterface(stage);
        Platform.runLater(() ->
                setCurrentScene(gameInterface.initGame()));
    }
}
