package adrenaline.view.gui.game;

import adrenaline.view.gui.GUIController;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.control.Button;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

class BoardLoader{

    private String owner;
    private StackPane board;
    private List<Button> boardLifeBar;
    private GUIController userController = GUIController.getController();

    BoardLoader(String owner){
        this.owner = owner;
        loadBoard(userController.getPlayerColors().get(owner));
    }

    private void loadBoard(String color) {
        try {
            StackPane pane = new StackPane();

            Image boardImage = new Image(new FileInputStream("src"+ File.separator+"Resources"+File.separator+"images"+ File.separator+"boards"+ File.separator+color+"_player_front.png"));
            ImageView boardView = new ImageView(boardImage);
            boardView.setPreserveRatio(true);
            boardView.setFitHeight(110);
            boardView.setFitWidth(700);

            GridPane boardPane = new GridPane();
            GridPane markPane = new MarkLoader().getMarks();
            GridPane ammosPane = new AmmoLoader().getAmmoPane();
            GridPane maxPointsPane = new MaxPointPlayer().getPointsPane();
            if (owner.equals(userController.getNickname())) {
                boardPane.setId("board_style_bottom");
                markPane.setId("mark_style_bottom");
                ammosPane.setId("ammo_style_bottom");
                maxPointsPane.setId("maxPoints_style_bottom");
            } else {
                boardPane.setId("board_style");
                markPane.setId("mark_style");
                ammosPane.setId("ammo_style");
                maxPointsPane.setId("maxPoints_style");
            }
            boardPane.getRowConstraints().add(new RowConstraints(30));
            this.boardLifeBar = new ArrayList<>();
            for (int i = 0; i < 12; i++) {
                Button button = new Button();
                button.setId("board");
                button.setDisable(true);
                button.setStyle("-fx-background-color: " +color);
                boardPane.add(button, i, 0);
                boardLifeBar.add(button);
            }
            boardPane.setHgap(9);
            pane.getChildren().addAll(boardView, boardPane, markPane, ammosPane, maxPointsPane);
            this.board = pane;
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    StackPane getBottomBoard(){
        board.setAlignment(Pos.CENTER);
        return this.board;}

    StackPane getLeftBoard(){
        board.setAlignment(Pos.CENTER);
        board.setRotate(90);
        return this.board;
    }

    StackPane getRightBoard(){
        board.setAlignment(Pos.CENTER);
        board.setRotate(270);
        return this.board;
    }

    StackPane getTopBoardR(){
        board.setAlignment(Pos.CENTER_RIGHT);
        board.setRotate(180);
        return this.board;
    }
    StackPane getTopBoardL(){
        board.setAlignment(Pos.CENTER_LEFT);
        board.setRotate(180);
        return this.board;
    }

    public String getOwner() {
        return this.owner;
    }
}