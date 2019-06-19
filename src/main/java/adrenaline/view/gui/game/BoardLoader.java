package adrenaline.view.gui.game;

import adrenaline.view.gui.GUIController;
import javafx.geometry.Insets;
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
    private StackPane board;
    private List<Button> boardLifeBar;
    private GUIController userController = GUIController.getController();
    private String thisPlayerColor;
    private List<String> names = userController.getPlayersNicks();
    private List<String> colors = new ArrayList<>();
    private String position;


    BoardLoader(String position){

        this.position = position;
        for (int i = 0; i < names.size(); i++) {
            String name = names.get(i);
            colors.add(userController.getPlayerColors().get(name));
        }
        thisPlayerColor = userController.getPlayerColors().get(userController.getNickname());
        for(int i=0; i<colors.size(); i++){
            if(colors.get(i).equalsIgnoreCase(thisPlayerColor)){
                colors.remove(i);
                break;
            }
        }

        switch (position){
            case "topR":
                if(names.size()==5) {
                    loadBoard(colors.get(3));
                }else {
                    loadBoard("null");
                }

                break;
            case "topL":
                if (names.size()==4 || names.size()==5) {
                    loadBoard(colors.get(2));
                }else {
                    loadBoard("null");
                }
                break;
            case "right":
                loadBoard(colors.get(1));
                break;
            case "left":
                loadBoard(colors.get(0));
                break;
            case "bottom":
                loadBoard(thisPlayerColor);
                break;

                default:
                    break;
        }

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
            if(!color.equals("null")){
                if (position.equals("bottom")) {
                    boardPane.setId("board_style_bottom");
                } else {
                    boardPane.setId("board_style");
                }
                boardPane.getRowConstraints().add(new RowConstraints(30));
                this.boardLifeBar = new ArrayList<>();
                for (int i = 0; i < 12; i++) {
                    Button button = new Button();
                    button.setId("board");
                    button.setDisable(true);
                    button.setStyle("-fx-background-color: white ");
                    boardPane.add(button, i, 0);
                    boardLifeBar.add(button);
                }
                boardPane.setHgap(9);
            }
            pane.getChildren().addAll(boardView, boardPane);
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

}