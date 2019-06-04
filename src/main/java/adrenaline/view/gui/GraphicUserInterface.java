package adrenaline.view.gui;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.scene.text.TextAlignment;
import javafx.stage.Stage;
import javafx.util.Duration;

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
        primaryStage.centerOnScreen();

        controller = GUIController.createController(primaryStage);

        StackPane pane = new StackPane();
        pane.setId("launcher");

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

        primaryStage.setOnCloseRequest(windowEvent -> {
            windowEvent.consume();
            primaryStage.close();
            //TODO improve
        });

        Scene firstScene = new Scene(pane, 1360, 768);
        primaryStage.setScene(firstScene);
        firstScene.getStylesheets().addAll(getClass().getResource("/assets/launch_scene.css").toExternalForm(),
                getClass().getResource("/assets/background.css").toExternalForm());
        primaryStage.sizeToScene();
        primaryStage.show();
        pane.requestFocus();
    }
}
