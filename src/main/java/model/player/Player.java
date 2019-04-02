package model.player;

import model.map.*;
import model.deck.*;
import java.util.ArrayList;

/**
 * Player is the class that represents every client taking part in the game.
 */

public class Player {
    private String playerID;
    private boolean firstPlayerCard;
    private Board playerBoard;
    private int pointTokens;
    private Square position;
    private ArrayList<Weapon> ownedWeapons;
    private ArrayList<PowerUp> ownedPowerUps;
    private int givenMarks;

    public Player(String color, Square spawnPoint) {
        this.playerID = color;
        this.playerBoard = new Board();
        this.position = spawnPoint;
        this.ownedWeapons = new ArrayList<Weapon>();
        this.ownedPowerUps = new ArrayList<PowerUp>();
    }

    /**
     * Getter method to obtain player's color
     * @return color which is used to univocally distinguish players
     */

    public String getPlayerColor() {
        return this.playerID;
    }

    /**
     * Setter method to set player's color
     * @param color
     */

    public void setPlayerColor(String color) {
        this.playerID = color;
    }

    /**
     * Method to know if the current player is the one that begins the game
     * @return true (if firstPlayerCard is true), false (otherwise)
     */

    public boolean isFirstPlayer() {
        return this.firstPlayerCard;
    }

    /**
     * Setter method to decide whether or not Player is the first player
     * @param value
     */

    public void setFirstPlayer(boolean value) {
        this.firstPlayerCard = value;
    }

    /**
     * Getter method to know how many marks a player has given; the amount should be less than 3
     * @return amount of given marks
     */

    public int getGivenMarks() {
        return this.givenMarks;
    }

    /**
     * giveMark() adds a certain amount of marks to a model.player.Player
     * @param amount of marks
     * @param toPlayer player whom the mark is given to
     */

    public void giveMark(int amount, Player toPlayer) {
            this.givenMarks += amount;
            toPlayer.playerBoard.gotMarked(amount, this);
    }

    /**
     * Getter method to know the player's current position
     * @return player's position
     */

    public Square getPosition() {
        return this.position;
    }

    /**
     * Method to change a player's position if the action chosen requires to do so
     */

    public void changePosition() {

    }

    /**
     * Getter to obtain a player's board
     * @return Board
     */

    public Board getBoard(){return this.playerBoard;}

    /**
     * Getter to obtain a player's weapons
     * @return ArrayList of Weapons
     */

    public ArrayList<Weapon> getWeapons() {
        return this.ownedWeapons;
    }

    public void grabWeapon(Weapon w) {
        //remove Weapon w from spawn point
        this.getWeapons().add(w);
    }

    public void grabTile(Tile t) {
        //remove Tile t from Square
        //this.playerBoard.addAmmo(t);
    }

    public void useWeapon(Weapon w, Player p) {
        //write when we have weapon class done
    }

    public void usePowerUp(PowerUp pup) {
        //write when we have powerup class done
    }

}