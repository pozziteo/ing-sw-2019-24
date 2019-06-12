package adrenaline.view.gui.game;

import adrenaline.view.gui.GUIController;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.stage.Stage;

public class GameInterface {

    private BorderPane root;

    private Scene gameScene;

    public GameInterface(Stage stage) {

        this.root = new BorderPane();
        root.setCenter(new MapLoader(GUIController.getController().getMap()).getMap());

        this.gameScene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
        root.requestFocus();
    }

    public Scene initGame() {
        return this.gameScene;
    }
}
