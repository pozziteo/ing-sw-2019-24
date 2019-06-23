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
import java.util.Collections;
import java.util.List;

class BoardLoader{

    private String owner;
    private StackPane board;
//    private boolean emptySlot; usato per controllare se un bottone delle munizioni o dei marchi e' colorabile o meno. da implentare
    private String blue = "blue";
    private String red = "red";
    private String yellow = "yellow";
    private List<String> life = new ArrayList<>();
    private List<String> marks = new ArrayList<>();
    private List<String> ammo = new ArrayList<>();

    private List<Button> boardLifeBar;
    private List<Button> maxPoints;
    private List<Button> marksButton;
    private List<Button> ammoButton;
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
                button.setStyle("-fx-background-color: transparent");
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

    //**********************************************************************
    //                          GETTER METHODS
    //**********************************************************************

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
        this.ammoButton = ammos;
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
            button.setStyle("-fx-background-color: transparent");
            button.setMaxSize(5,5);
            pane.add(button, i, 0);
            playerMarks.add(button);
        }
        pane.setHgap(5);
        this.marksButton = playerMarks;
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
            button.setStyle("-fx-background-color: transparent");
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
    public List<Button> getAmmo(){return this.ammoButton;}

    /**
     * Getter method
     * @return the list of marks on a board
     */
    public List<Button> getMarks(){return this.marksButton;}

    /**
     * Getter method
     * @return the list of damages
     */
    public List<Button> getBoardLifeBar(){return this.boardLifeBar;}


    //**********************************************************************
    //                   METHOD TO UPDATE THE BOARD
    //**********************************************************************

    /**
     * Method to update the players' life
     * @param damage is the number of hits
     * @param attackerColor is the color of the perpetrator
     */
    public void updateLifeBar(int damage, String attackerColor){
        changeButtonList(damage, attackerColor, life, getBoardLifeBar());
    }

    /**
     * Method to update the players' marks
     * @param amount is the number of marks
     * @param attackerColor is the color of the perpetrator
     */
    public void addMarks(int amount, String attackerColor){
        changeButtonList(amount, attackerColor, marks, getMarks());
    }

    /**
     * Method to remove marks
     * @param amount is the number of marks
     * @param color is the color of the marks
     */
    public void removeMarks(int amount, String color){
        List<Integer> markToRemove = new ArrayList<>();
        for(int i=0; i<amount; i++){
            if(Collections.frequency(marks, color)>0){
                markToRemove.add(marks.lastIndexOf(color));
                marks.remove(color);
            }
        }
        for(Integer integer: markToRemove){
            marksButton.get(integer).setStyle("-fx-background-color: transparent");
        }
        markToRemove.clear();
    }

    /**
     * Method to add ammo
     * @param r is the number of red ammo
     * @param y is the number of yellow ammo
     * @param b is the number of blue ammo
     */
    public void addAmmo(int r, int y, int b){
        List<String> toAddNow = new ArrayList<>();
        int oldSize = ammo.size();
        for(int i=0; i<r; i++){
            if (ammo.size()<9 && Collections.frequency(ammo, red)<3){
                ammo.add(red);
                toAddNow.add(red);
            }
        }
        for(int i=0; i<y; i++){
            if (ammo.size()<9 && Collections.frequency(ammo, yellow)<3){
                toAddNow.add(yellow);
                ammo.add(yellow);
            }
        }
        for(int i=0; i<b; i++){
            if (ammo.size()<9 && Collections.frequency(ammo, blue)<3){
                ammo.add(blue);
                toAddNow.add(blue);
            }
        }
        int newSize = ammo.size();
        int j=0;
        for(int i=oldSize; i<newSize; i++){
            ammoButton.get(i).setStyle("-fx-background-color: " + toAddNow.get(j));
            j++;
        }
        toAddNow.clear();
    }

    /**
     * Method to remove ammo from a board
     * @param r is the number of red ammo
     * @param y is the number of yellow ammo
     * @param b is the number of blue ammo
     */
    public void removeAmmo(int r, int y, int b){
        List<Integer> indexOfAmmoToRemove = new ArrayList<>();
        for(int i=0; i<r; i++){
            if(!ammo.isEmpty() && Collections.frequency(ammo, red)>0){
                indexOfAmmoToRemove.add(ammo.lastIndexOf(red));
                ammo.remove(red);
            }
        }
        for(int i=0; i<y; i++){
            if(!ammo.isEmpty() && Collections.frequency(ammo, yellow)>0){
                indexOfAmmoToRemove.add(ammo.lastIndexOf(yellow));
                ammo.remove(yellow);
            }
        }
        for(int i=0; i<b; i++){
            if(!ammo.isEmpty() && Collections.frequency(ammo, blue)>0){
                indexOfAmmoToRemove.add(ammo.lastIndexOf(blue));
                ammo.remove(blue);
            }
        }
        for (Integer integer : indexOfAmmoToRemove) {
            ammoButton.get(integer).setStyle("-fx-background-color: transparent");
        }
        indexOfAmmoToRemove.clear();
    }


    /**
     * Method to update the graphic board of a player
     * @param amount is the number of hits
     * @param attackerColor is the color of the perpetrator
     * @param toChange is the list of string to keep track
     * @param toUpdate is the list of button to update
     */
    private void changeButtonList(int amount, String attackerColor, List<String> toChange, List<Button> toUpdate){
        int oldSize = toChange.size();
        for(int i=0; i<amount; i++) toChange.add(attackerColor);
        int newSize = toChange.size();
        for(int i = oldSize; i< newSize; i++){
            if(amount>0){
                toUpdate.get(i).setStyle("-fx-background-color: " + attackerColor);
                amount--;
            }
        }
    }
}