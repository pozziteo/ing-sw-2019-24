package adrenaline.view.gui;

import adrenaline.data.data_for_client.DataForClient;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.network.ClientInterface;
import adrenaline.view.UserInterface;
import adrenaline.view.gui.stages.ConnectionStage;
import javafx.stage.Stage;

import java.io.FileNotFoundException;

public class GUIController implements UserInterface {

    private static GUIController controller;
    private Stage stage;
    private ConnectionStage connectionStage;

    private ClientInterface client;

    private GUIController(Stage stage) {
        this.stage = stage;
        try {
            this.connectionStage = new ConnectionStage();
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

    @Override
    public void updateView(DataForClient data) {

    }

    @Override
    public void sendToServer(DataForServer data) {

    }

    @Override
    public void setUpAccount() {

    }
}
