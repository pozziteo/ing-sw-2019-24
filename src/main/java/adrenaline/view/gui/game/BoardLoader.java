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
    private List<String> weapons = new ArrayList<>();
    private List<String> powerups = new ArrayList<>();
    private int death = 0;
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
            HBox weaponsBox = getWeaponsPane();
            HBox pupsBox = getPupsPane();
            if (owner.equals(userController.getNickname())) {
                boardPane.setId("board_style_bottom");
                markPane.setId("mark_style_bottom");
                ammosPane.setId("ammo_style_bottom");
                maxPointsPane.setId("maxPoints_style_bottom");
                weaponsBox.setId("weapons_style_bottom");
                pupsBox.setId("powerUps_style");
            } else {
                boardPane.setId("board_style");
                markPane.setId("mark_style");
                ammosPane.setId("ammo_style");
                maxPointsPane.setId("maxPoints_style");
                weaponsBox.setId("weapons_style");
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
            if (owner.equals(userController.getNickname())){
                pane.getChildren().addAll(boardView, boardPane, markPane, ammosPane, maxPointsPane/*, weaponsBox, pupsBox*/);
            }else
                pane.getChildren().addAll(boardView, boardPane, markPane, ammosPane, maxPointsPane/*, weaponsBox*/);
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
    private GridPane getBoardMarks(){
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


    private HBox getWeaponsPane(){
        HBox box = new HBox();
        try{
            Image boardImage1 = new Image(new FileInputStream("src"+ File.separator+"Resources"+File.separator+"images"+ File.separator+"cards"+ File.separator+weapons.get(0)+".png"));
            ImageView boardView1 = new ImageView(boardImage1);
            boardView1.setPreserveRatio(true);
            boardView1.setFitHeight(100);

            Image boardImage2 = new Image(new FileInputStream("src"+ File.separator+"Resources"+File.separator+"images"+ File.separator+"cards"+ File.separator+weapons.get(1)+".png"));
            ImageView boardView2 = new ImageView(boardImage2);
            boardView2.setPreserveRatio(true);
            boardView2.setFitHeight(100);

            Image boardImage3 = new Image(new FileInputStream("src"+ File.separator+"Resources"+File.separator+"images"+ File.separator+"cards"+ File.separator+weapons.get(2)+".png"));
            ImageView boardView3 = new ImageView(boardImage3);
            boardView3.setPreserveRatio(true);
            boardView3.setFitHeight(100);


            box.getChildren().addAll(boardView1, boardView2, boardView3);
            box.setSpacing(10);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
        return box;
    }

    private HBox getPupsPane(){
        HBox box = new HBox();
        try{
            Image boardImage1 = new Image(new FileInputStream("src"+ File.separator+"Resources"+File.separator+"images"+ File.separator+"cards"+ File.separator+ powerups.get(0)+".png"));
            ImageView boardView1 = new ImageView(boardImage1);
            boardView1.setPreserveRatio(true);
            boardView1.setFitHeight(50);

            Image boardImage2 = new Image(new FileInputStream("src"+ File.separator+"Resources"+File.separator+"images"+ File.separator+"cards"+ File.separator+ powerups.get(1)+".png"));
            ImageView boardView2 = new ImageView(boardImage2);
            boardView2.setPreserveRatio(true);
            boardView2.setFitHeight(50);

            Image boardImage3 = new Image(new FileInputStream("src"+ File.separator+"Resources"+File.separator+"images"+ File.separator+"cards"+ File.separator+powerups.get(2)+".png"));
            ImageView boardView3 = new ImageView(boardImage3);
            boardView3.setPreserveRatio(true);
            boardView3.setFitHeight(50);


            box.getChildren().addAll(boardView1, boardView2, boardView3);
            box.setSpacing(10);
        } catch (FileNotFoundException exc) {
            exc.printStackTrace();
        }
        return box;
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
    //                          SETTER METHODS
    //**********************************************************************

    /**
     * Setter method to add a weapon
     * @param weapon is the name of the weapon
     */
    public void addWeapons(String weapon){
        this.weapons.add(weapon);
    }

    /**
     * Setter method to add a powerup
     * @param powerup is the name of the powerup
     */
    public void addPowerups(String powerup){
        if(this.powerups.size()<3)
            this.powerups.add(powerup);
    }

    /**
     * Method to remove a weapon
     * @param weapon is the name of the weapon
     */
    public void removeWeapons(String weapon){
        for(String w: weapons)
            if (w.equalsIgnoreCase(weapon))
                weapons.remove(w);
    }

    /**
     * method to remove a powerup
     * @param powerup is the name of the powerup
     */
    public void removePowerUps(String powerup){
        for(String pup: powerups)
            if(pup.equalsIgnoreCase(powerup)) {
                powerups.remove(pup);
                break;
            }
    }

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
     * @param ammoToAdd is the list of ammo to add
     */
    public void addAmmo(List<String> ammoToAdd){
        List<String> toAddNow = new ArrayList<>();
        int oldSize = ammo.size();
        for(String ammoColor: ammoToAdd){
            if(ammoColor.equalsIgnoreCase(red)){
                if(Collections.frequency(ammo, red)<3) {
                    ammo.add(red);
                    toAddNow.add(red);
                }
            } else if(ammoColor.equalsIgnoreCase(yellow)){
                if(Collections.frequency(ammo, yellow)<3) {
                    ammo.add(yellow);
                    toAddNow.add(yellow);
                }
            } else{
                if(Collections.frequency(ammo, blue)<3) {
                    ammo.add(blue);
                    toAddNow.add(blue);
                }
            }
        }
        int newSize = ammo.size();
        for(int i=oldSize, j=0; i<newSize; i++, j++){
            ammoButton.get(i).setStyle("-fx-background-color: " + toAddNow.get(j));
        }
        toAddNow.clear();
    }

    /**
     * Method to remove ammo from a board
     * @param ammoToRemove is the list of ammo to remove
     */
    public void removeAmmo(List<String> ammoToRemove){
        List<Integer> indexOfAmmoToRemove = new ArrayList<>();
        for(String ammoColor: ammoToRemove){
            if(ammoColor.equalsIgnoreCase(red)){
                if (!ammo.isEmpty() && Collections.frequency(ammo, red) > 0) {
                    indexOfAmmoToRemove.add(ammo.lastIndexOf(red));
                    ammo.remove(red);
                }
            } else if(ammoColor.equalsIgnoreCase(yellow)){
                if (!ammo.isEmpty() && Collections.frequency(ammo, yellow) > 0) {
                    indexOfAmmoToRemove.add(ammo.lastIndexOf(yellow));
                    ammo.remove(yellow);
                }
            } else{
                if (!ammo.isEmpty() && Collections.frequency(ammo, blue) > 0) {
                    indexOfAmmoToRemove.add(ammo.lastIndexOf(blue));
                    ammo.remove(blue);
                }
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

    /**
     * Method to clear the life bar after a death
     */
    public void clearLifeBar(){
        //TO BE CALLED BEFORE METHOD "decreaseMaxPoints"
        for(Button b: getBoardLifeBar()){
            b.setStyle("-fx-background-color: transparent");
        }
        this.death++;
    }

    /**
     * Method to add a skull on a player's board
     */
    public void decreaseMaxPoints(){
        maxPoints.get(this.death - 1).setStyle("-fx-background-color: red; -fx-opacity: 1");
    }
}