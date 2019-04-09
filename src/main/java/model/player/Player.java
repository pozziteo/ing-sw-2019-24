package model.player;

import model.Game;
import model.map.*;
import model.deck.*;
import java.util.*;

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

    public Player() {

    }

    public Player(String color, Square spawnPoint) {
        this.playerID = color;
        this.playerBoard = new Board();
        this.position = spawnPoint;
        this.ownedWeapons = new ArrayList<>();
        this.ownedPowerUps = new ArrayList<>();
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
     * Setter method to set the amount of marks a player has given
     * @param amount
     */

    public void setGivenMarks(int amount) {
        this.givenMarks = amount;
    }

    /**
     * Getter method to know the player's current position
     * @return player's position
     */

    public Square getPosition() {
        return this.position;
    }

    /**
     * Method to get a player's position
     */

    public void setPosition(Square square) {
        this.position = square;
    }

    /**
     * Getter method to obtain a player's board
     * @return Board
     */

    public Board getBoard(){return this.playerBoard;}

    /**
     * Setter method to set a player's board
     * @param board
     */

    public void setBoard(Board board) {
        this.playerBoard = board;
    }

    /**
     * Getter method to obtain a player's points
     * @return amount of points
     */

    public int getPointTokens() {
        return this.pointTokens;
    }

    /**Setter method to set a player's points
     * @param amount of points to be given
     */

    public void setPointTokens(int amount) {
        this.pointTokens = amount;
    }

    /**
     * Getter method to obtain a player's weapons
     * @return ArrayList of Weapons
     */

    public List<Weapon> getOwnedWeapons() {
        return this.ownedWeapons;
    }

    /**
     * Setter method to set a player's list of owned weapons
     * @param weaponsList
     */

    public void setOwnedWeapons(ArrayList<Weapon> weaponsList) {
        this.ownedWeapons = weaponsList;
    }

    /**
     * Getter method to obtain a player's power ups
     * @return
     */

    public List<PowerUp> getOwnedPowerUps() {
        return this.ownedPowerUps;
    }

    /**
     * Setter method to set a player's list of owned power ups
     * @param powerUpsList
     */

    public void setOwnedPowerUps(ArrayList<PowerUp> powerUpsList) {
        this.ownedPowerUps = powerUpsList;
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
     * Method to add points to a player after a kill
     * @param amount
     */

    public void addPointTokens(int amount) {
        int points = this.getPointTokens ();
        points += amount;
        setPointTokens (points);
    }

    /**
     * Method to let a player grab a weapon from the spawn point he's on
     * @param w
     */

    public void grabWeapon(Weapon w) {
        ((SpawnPoint) getPosition ()).removeWeapon(w);
        this.getOwnedWeapons().add(w);
    }

    /**
     * Method to know if two players are in the same room
     * @param p
     * @return
     */

    public boolean isInTheSameRoom(Player p) {
        if (this.getPosition ().isInTheSameRoom (p.getPosition ())) {
            return true;
        }
        return false;
    }

    /**
     * Method to know if this player can see another player
     * @param p
     * @return true (if this player can see p), false (otherwise)
     */

    public boolean canSee(Player p) {
        if (this.isInTheSameRoom (p)) {
            return true;
        } else {
            for (int i = 0; i < this.getPosition ().getLinks().size(); i++) {
                Square s = Game.getGameInstance ().getArena ().getSquare(this.getPosition ().getLinks ().get(i));
                if (s.getSquareColor ().equals (p.getPosition ().getSquareColor ())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Method to let this player grab the tile placed on his square
     * @param t
     */

    public void grabTile(Tile t) {
        Ammo a;
        if (t.getFormat ().isPowerUpIsPresent ()) {
            this.getOwnedPowerUps ().add((PowerUp) Game.getGameInstance ().getPowerUpsDeck ().drawCard ());
        }
        for (int i = 0; i < t.getTileContent ().size(); i++) {
            a = t.getTileContent ( ).get (i);
            if (this.getBoard ().getAmountOfAmmo (a) < 3) {
                this.getBoard ( ).getOwnedAmmo ( ).add (a);
            }
        }
        t.discardTile();
    }

    /**
     * Method to let this player use one of his owned weapons as long as it's loaded
     * @param w
     * @param p
     */

    public void useWeapon(Weapon w, Player p) {
        //write when we have weapon class done
    }

    /**
     * Method to let this player use one of his owned power ups
     * @param pup
     */

    public void usePowerUp(PowerUp pup) {
        //write when we have powerup class done
    }
}