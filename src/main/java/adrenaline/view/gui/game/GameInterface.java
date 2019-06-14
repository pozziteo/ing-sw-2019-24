package adrenaline.view.gui.game;

import adrenaline.view.gui.GUIController;
import javafx.geometry.HPos;
import javafx.geometry.Pos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class GameInterface {

    private StackPane root;
    private BorderPane game;

    private Scene gameScene;

    public GameInterface(Stage stage) {

        this.root = new StackPane();
        root.setId("game_scene");
        this.game = new BorderPane();
        game.setCenter(new MapLoader(GUIController.getController().getMap()).getMap());

        GridPane mapPane = new GridPane();
        mapPane.setGridLinesVisible(true);
        mapPane.setAlignment(Pos.CENTER);
        mapPane.setHgap(30.0);
        mapPane.getRowConstraints().add(new RowConstraints(140));
        mapPane.getRowConstraints().add(new RowConstraints(140));
        mapPane.getRowConstraints().add(new RowConstraints(140));


        for (int j=0; j < 3; j++) {
            for (int i = 0; i < 4; i++) {
                Button button = new Button(j + ", " + i);
                button.setPrefSize(110, 110);
                button.setOpacity(0.4);
                mapPane.add(button, i, j);
                GridPane.setHalignment(button, HPos.CENTER);
                if (j == 0)
                    GridPane.setValignment(button, VPos.BOTTOM);
                else if (j == 1)
                    GridPane.setValignment(button, VPos.BOTTOM);
                else
                    GridPane.setValignment(button, VPos.BOTTOM);
            }
        }
        root.getChildren().add(game);
        root.getChildren().add(mapPane);
        this.gameScene = new Scene(root, stage.getScene().getWidth(), stage.getScene().getHeight());
        gameScene.getStylesheets().add(getClass().getResource("/assets/game_style.css").toExternalForm());
        root.requestFocus();
    }

    public Scene initGame() {
        return this.gameScene;
    }
}
