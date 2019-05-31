package adrenaline.view.gui;

import adrenaline.view.gui.stages.ConnectionStage;
import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.util.Duration;

import java.io.File;
import java.io.FileInputStream;


public class GraphicUserInterface extends Application {

    private Stage primaryStage;
    private GUIController controller;

    public static void launchGUI() {
        launch();
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        this.primaryStage = primaryStage;
        primaryStage.setTitle("Adrenaline Launcher");
        primaryStage.setResizable(false);
        primaryStage.setAlwaysOnTop(true);
        primaryStage.initStyle(StageStyle.UNIFIED);
        primaryStage.centerOnScreen();

        controller = GUIController.createController(primaryStage);

        Image image = new Image(new FileInputStream("src" + File.separatorChar + "Resources" + File.separatorChar + "images"
                + File.separatorChar + "Adrenaline.jpg"), 1360, 768, false, true);
        BackgroundImage backgroundImage = new BackgroundImage(image, BackgroundRepeat.NO_REPEAT, BackgroundRepeat.NO_REPEAT ,
                BackgroundPosition.CENTER, BackgroundSize.DEFAULT);
        StackPane pane = new StackPane();
        pane.setBackground(new Background(backgroundImage));

        Text text = new Text("Press ENTER to continue...");
        text.setTextAlignment(TextAlignment.CENTER);
        text.setId("text");
        FadeTransition transition = new FadeTransition(Duration.seconds(1.5), text);
        transition.setFromValue(1.0);
        transition.setToValue(0.0);
        transition.setAutoReverse(true);
        transition.setCycleCount(Animation.INDEFINITE);
        transition.play();

        pane.getChildren().add(text);

        pane.setOnKeyPressed(keyEvent -> {
            if (keyEvent.getCode().equals(KeyCode.ENTER)) {
                try {
                    controller.establishConnection();
                } catch (Exception exc) {
                    exc.printStackTrace();
                }
            }
        });

        Scene firstScene = new Scene(pane, 1360, 768);
        primaryStage.setScene(firstScene);
        firstScene.getStylesheets().add(getClass().getResource("/assets/launchScene.css").toExternalForm());
        primaryStage.sizeToScene();
        primaryStage.show();
        pane.requestFocus();

        /*GridPane grid = new GridPane();
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
        primaryStage.show(); */
    }

}
