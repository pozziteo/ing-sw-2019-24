package adrenaline.view.gui.stages;

import adrenaline.network.ClientInterface;
import adrenaline.network.rmi.client.RmiClient;
import adrenaline.network.socket.client.SocketClient;
import adrenaline.view.gui.GUIController;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.Modality;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;
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
        Image image = new Image(new FileInputStream("src" + File.separatorChar + "Resources" + File.separatorChar + "images"
                + File.separatorChar + "Adrenaline.jpg"), 1360, 768, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT ,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        pane.setBackground(new Background(backgroundImage));

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
            client = new SocketClient("localhost", 6666, GUIController.getController());
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
        connectionScene.getStylesheets().add(getClass().getResource("/assets/launchScene.css").toExternalForm());

        pane.requestFocus();
    }

    public ClientInterface getConnection(Stage owner) {
        stage = new Stage(StageStyle.UTILITY);
        stage.initOwner(owner);
        stage.initModality(Modality.APPLICATION_MODAL);
        stage.setScene(connectionScene);
        stage.sizeToScene();
        stage.showAndWait();
        return client;
    }
}
