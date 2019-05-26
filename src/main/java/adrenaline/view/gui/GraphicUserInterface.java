package adrenaline.view.gui;

import javafx.application.Application;
import javafx.geometry.HPos;
import javafx.geometry.VPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.layout.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import java.io.File;
import java.io.FileInputStream;


public class GraphicUserInterface extends Application {

    public static void launchGUI() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Adrenaline Launcher");
        primaryStage.setResizable(true);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.centerOnScreen();

        Image image = new Image(new FileInputStream("src" + File.separatorChar + "Resources" + File.separatorChar + "images"
                + File.separatorChar + "Adrenaline.jpg"), 1360, 768, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT ,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        StackPane pane = new StackPane();
        pane.setBackground(new Background(backgroundImage));

        GridPane grid = new GridPane();
        grid.setId("grid");

        Label label = new Label("Choose your connection");
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

        pane.getChildren().add(grid);
        Scene firstScene = new Scene(pane, 1360, 768);
        primaryStage.setScene(firstScene);
        firstScene.getStylesheets().add(getClass().getResource("/assets/launchScene.css").toExternalForm());
        primaryStage.sizeToScene();
        primaryStage.show();
    }

}
