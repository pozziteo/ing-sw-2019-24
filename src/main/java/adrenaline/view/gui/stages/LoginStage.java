package adrenaline.view.gui.stages;

import adrenaline.data.data_for_server.data_for_network.AccountSetUp;
import adrenaline.view.gui.GUIController;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;

public class LoginStage {

    private Scene loginScene;

    public LoginStage(Stage stage) {
        StackPane pane = new StackPane();
        pane.setId("launcher");

        BorderPane border = new BorderPane();
        VBox box = new VBox();
        box.setId("box");
        border.setCenter(box);

        pane.getChildren().add(border);

        Text connected = new Text("Connected to the server!");
        connected.setId("text");
        box.getChildren().add(connected);

        GridPane grid = new GridPane();
        grid.setId("login_form");

        Text request = new Text("Insert your username");
        request.setId("text");
        grid.add(request, 0, 0);
        GridPane.setColumnSpan(request, 2);
        GridPane.setHalignment(request, HPos.CENTER);

        Text username = new Text("Username:");
        username.setId("small_text");
        grid.add(username, 0, 1);
        GridPane.setHalignment(username, HPos.RIGHT);

        TextField field = new TextField();
        field.setId("field");
        grid.add(field, 1, 1);

        Button login = new Button("Login");
        login.setId("login_button");
        grid.add(login, 0, 3, 2, 1);
        GridPane.setHalignment(login, HPos.CENTER);

        box.getChildren().add(grid);

        login.setOnMouseClicked(mouseEvent -> {
            GUIController controller = GUIController.getController();
            AccountSetUp accountData = new AccountSetUp(controller.getNickname(), field.getText());
            controller.sendToServer(accountData);
        });

        this.loginScene = new Scene(pane, stage.getScene().getWidth(), stage.getScene().getHeight());
        loginScene.getStylesheets().addAll(getClass().getResource("/assets/background.css").toExternalForm(),
                getClass().getResource("/assets/login_stage.css").toExternalForm());
        pane.requestFocus();
    }

    public Scene getLoginScene() {
        return loginScene;
    }
}
