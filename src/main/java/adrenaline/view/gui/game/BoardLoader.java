package adrenaline.view.gui.game;

import adrenaline.view.gui.GUIController;
import javafx.geometry.Pos;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.VBox;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class BoardLoader{
    private VBox board;


    BoardLoader(String position){
//TODO sistemare, questa era solo una prova. al posto di 'color' la board del giocatore
//        switch (position){
//            case "topR":
//                loadBoard("red");
//                break;
//            case "topL":
//                loadBoard("ss");
//                break;
//            case "right":
//                loadBoard("green");
//                break;
//            case "left":
//                loadBoard("grey");
//                break;
//            case "bottom":
//                loadBoard("c");
//                break;
//
//                default:
//                    break;
//        }

    }

    public void loadBoard(String color) {
        try {
            VBox pane = new VBox();
            Image boardImage = new Image(new FileInputStream("src/Resources/images/boards/"+color+"_player_front.png"));
            ImageView boardView = new ImageView(boardImage);
            boardView.setPreserveRatio(true);
            boardView.setFitHeight(110);
            boardView.setFitWidth(700);
            pane.getChildren().addAll(boardView);
            this.board = pane;
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
    }

    VBox getBottomBoard(){
        board.setAlignment(Pos.CENTER);
        return this.board;}

    VBox getLeftBoard(){
        board.setAlignment(Pos.CENTER);
        board.setRotate(90);
        return this.board;
    }

    VBox getRightBoard(){
        board.setAlignment(Pos.CENTER);
        board.setRotate(270);
        return this.board;
    }

    VBox getTopBoardR(){
        board.setAlignment(Pos.CENTER_RIGHT);
        board.setRotate(180);
        return this.board;
    }
    VBox getTopBoardL(){
        board.setAlignment(Pos.CENTER_LEFT);
        board.setRotate(180);
        return this.board;
    }

}