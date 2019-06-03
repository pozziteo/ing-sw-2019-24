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

import java.io.FileNotFoundException;
import java.rmi.RemoteException;

public class ConnectionStage {

    private Stage stage;
    private ClientInterface client;
    private Scene connectionScene;

    public ConnectionStage() throws FileNotFoundException {
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
            stage.close();
        });

        rmiButton.setOnMouseClicked(mouseEvent -> {
            try {
                client = new RmiClient(GUIController.getController());
            } catch (RemoteException exc) {
                exc.printStackTrace();
            }
            stage.close();
        });

        pane.getChildren().add(grid);
        this.connectionScene = new Scene(pane, 1360, 768);
        connectionScene.getStylesheets().addAll(getClass().getResource("/assets/launch_scene.css").toExternalForm(),
                getClass().getResource("/assets/background.css").toExternalForm());
        pane.requestFocus();
    }

    public ClientInterface getConnection(Stage owner) {
        owner.setScene(connectionScene);
        stage = new Stage(StageStyle.TRANSPARENT);
        stage.sizeToScene();
        stage.toBack();
        stage.showAndWait();

        return client;
    }
}
