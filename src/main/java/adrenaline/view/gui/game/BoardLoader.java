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
    private List<Button> maxPoints;
    private List<Button> marks;
    private List<Button> ammos;
    private GUIController userController = GUIController.getController();

    BoardLoader(String owner){
        this.owner = owner;
        loadBoard(userController.getPlayerColors().get(owner));
    }

    /**
     * Method to create a Player's board
     * @param color is the color of the board to be loaded
     */
    private void loadBoard(String color) {
        try {
            StackPane pane = new StackPane();

            Image boardImage = new Image(new FileInputStream("src"+ File.separator+"Resources"+File.separator+"images"+ File.separator+"boards"+ File.separator+color+"_player_front.png"));
            ImageView boardView = new ImageView(boardImage);
            boardView.setPreserveRatio(true);
            boardView.setFitHeight(110);
            boardView.setFitWidth(700);

            GridPane boardPane = new GridPane();
            GridPane markPane = getBoardMarks();
            GridPane ammosPane = getAmmoPane();
            GridPane maxPointsPane = getPointsPane();
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

    /**
     * Getter method
     * @return the name of the board's owner
     */
    public String getOwner() {
        return this.owner;
    }


    /**
     * Method to create ammo grid
     * @return a grid
     */
    private GridPane getAmmoPane(){
        List<Button>ammos = new ArrayList<>();
        GridPane pane = new GridPane();
        pane.getRowConstraints().add(new RowConstraints(4));
        pane.getRowConstraints().add(new RowConstraints(4));
        pane.getRowConstraints().add(new RowConstraints(4));
        for(int i=0; i<9; i++){
            Button button = new Button();
            button.setDisable(true);
            button.setStyle("-fx-background-color: yellow; -fx-opacity: 1");
            button.setMaxSize(16,16);
            button.setMinSize(16,16);
            pane.add(button, i%3, i/3);
            ammos.add(button);
        }
        pane.setVgap(15);
        pane.setHgap(5);
        this.ammos = ammos;
        return pane;
    }

    /**
     * Method to create marks grid
     * @return a grid
     */
    public GridPane getBoardMarks(){
        GridPane pane = new GridPane();
        pane.getRowConstraints().add(new RowConstraints(5));
        List<Button> playerMarks = new ArrayList<>();
        for(int i=0; i<8; i++){
            Button button = new Button();
            button.setId("board");
            button.setDisable(true);
            button.setStyle("-fx-background-color: white");
            button.setMaxSize(5,5);
            pane.add(button, i, 0);
            playerMarks.add(button);
        }
        pane.setHgap(5);
        this.marks = playerMarks;
        return pane;
    }

    /**
     * Method to create the grid to place the skulls on the players' boards
     * @return a grid
     */
    private GridPane getPointsPane(){

        List<Button> maxPoints = new ArrayList<>();
        GridPane pane = new GridPane();
        pane.getRowConstraints().add(new RowConstraints(5));

        for(int i=0; i<6; i++){
            Button button = new Button();
            button.setDisable(true);
            button.setId("board");
            button.setStyle("-fx-background-color: white");
            pane.add(button, i, 0);
            maxPoints.add(button);
        }
        pane.setHgap(8);
        this.maxPoints = maxPoints;
        return pane;
    }

    /**
     * Getter method
     * @return the list of max points on a board
     */
    public List<Button> getMaxPoints(){return this.maxPoints;}

    /**
     * Getter method
     * @return the list of ammo on a board
     */
    public List<Button> getAmmos(){return this.ammos;}

    /**
     * Getter method
     * @return the list of marks on a board
     */
    public List<Button> getMarks(){return this.marks;}
}