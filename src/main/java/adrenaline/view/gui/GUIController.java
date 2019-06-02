package adrenaline.view.gui;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.ClientInterface;
import adrenaline.view.UserInterface;
import adrenaline.view.gui.stages.ConnectionStage;
import adrenaline.view.gui.stages.LoginStage;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class GUIController implements UserInterface {

    private static GUIController controller;
    private Stage stage;
    private ConnectionStage connectionStage;
    private LoginStage loginStage;

    private ClientInterface client;
    private String nickname;

    private final Object obj = new Object();

    private GUIController(Stage stage) {
        this.stage = stage;
        try {
            this.connectionStage = new ConnectionStage();
            this.loginStage = new LoginStage();
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    public static GUIController getController() {
        return controller;
    }

    public static GUIController createController(Stage stage) {
        if (controller == null)
            controller = new GUIController(stage);
        return getController();
    }

    public void establishConnection() {
        client = connectionStage.getConnection(stage);
        client.connectToServer();
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    @Override
    public void updateView(DataForClient data) {

    }

    @Override
    public void sendToServer(DataForServer data) {
        client.sendData(data);
    }

    @Override
    public void setUpAccount() {
        stage.setScene(loginStage.getLoginScene());
    }
}
