package adrenaline.view.gui.stages;

import adrenaline.network.ClientInterface;
import adrenaline.network.rmi.client.RmiClient;
import adrenaline.network.socket.client.SocketClient;
import adrenaline.utils.ConfigFileReader;
import adrenaline.view.gui.GUIController;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.rmi.RemoteException;

/**
 * Class that handles the connection stage
 */
public class ConnectionStage {

    private Stage stage;
    private ClientInterface client;
    private Scene connectionScene;

    public ConnectionStage(Stage stage) {
        this.stage = stage;

        GridPane grid = new GridPane();
        grid.setId("grid");

        StackPane pane = new StackPane();
        pane.setId("launcher");

        Text label = new Text("Choose your connection");
        label.setId("text");
        grid.add(label, 2, 24);

        Button socketButton = new Button("Socket");
        socketButton.setId("button-style");
        grid.add(socketButton, 2, 25);
        GridPane.setHalignment(socketButton, HPos.LEFT);
        GridPane.setValignment(socketButton, VPos.BASELINE);

        Button rmiButton = new Button("RMI");
        rmiButton.setId("button-style");
        grid.add(rmiButton, 2, 25);
        GridPane.setHalignment(rmiButton, HPos.RIGHT);
        GridPane.setValignment(rmiButton, VPos.BASELINE);

        socketButton.setOnMouseClicked(mouseEvent -> {
            client = new SocketClient("localhost", ConfigFileReader.readConfigFile("socketPort"), GUIController.getController());
            GUIController.getController().setClient(client);
            client.connectToServer();
        });

        rmiButton.setOnMouseClicked(mouseEvent -> {
            try {
                client = new RmiClient(GUIController.getController());
            } catch (RemoteException exc) {
                exc.printStackTrace();
            }
            GUIController.getController().setClient(client);
            client.connectToServer();
        });

        pane.getChildren().add(grid);
        this.connectionScene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        connectionScene.getStylesheets().addAll(getClass().getResource("/assets/launch_scene.css").toExternalForm(),
                getClass().getResource("/assets/background.css").toExternalForm());
        pane.requestFocus();
    }

    /**
     * Getter method
     * @return the connection stage
     */
    public Scene getConnectionScene() {
        return this.connectionScene;
    }
}
