package adrenaline.model.player;

import adrenaline.model.Game;
import adrenaline.model.deck.PowerUp;
import adrenaline.model.deck.Weapon;
import adrenaline.model.map.Square;

import java.io.Serializable;
import java.util.*;

/**
 * Player is the class that represents every client taking part in the game.
 */

public class Player implements Serializable {
    private Game game;
    private String playerName;
    private String playerColor;
    private Board playerBoard;
    private int pointTokens;
    private Square position;
    private boolean firstPlayer;
    private ArrayList<Weapon> ownedWeapons;
    private ArrayList<PowerUp> ownedPowerUps;
    private int givenMarks;
    private Action[] performedActions;

    public Player() {

    }

    public Player(String playerName, Game game, String color) {
        this.game = game;
        this.playerName = playerName;
        this.playerColor = color;
        this.playerBoard = new Board();
        this.pointTokens = 0;
        this.ownedWeapons = new ArrayList<>();
        this.ownedPowerUps = new ArrayList<>();
        this.performedActions = new Action[2];
    }

    /**
     * Method to get the game this player is taking part of
     * @return game
     */

    public Game getGame() {
        return this.game;
    }

    /**
     * Getter method to obtain this player's nickname
     * @return nickname
     */

    public String getPlayerName() {
        return this.playerName;
    }

    /**
     * Getter method to obtain player's color
     * @return color which is used to univocally distinguish players
     */

    public String getPlayerColor() {
        return this.playerColor;
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
     * Setter method to signal this player is the first to act
     * @param value true (if first), false (otherwise)
     */

    public void setFirstPlayer(boolean value) {
        this.firstPlayer = value;
    }

    /**
     * Getter method to obtain a player's board
     * @return Board
     */

    public Board getBoard(){return this.playerBoard;}

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
     * Getter method to obtain the actions performed by a player in a single turn
     * @return the actions performed in a turn
     */
    public Action[] getPerformedActions() {
        return this.performedActions;
    }

    /**
     * Setter method to set a player's list of owned power ups
     * @param powerUpsList
     */

    public void setOwnedPowerUps(ArrayList<PowerUp> powerUpsList) {
        this.ownedPowerUps = powerUpsList;
    }

    /**
     * giveMark() adds a certain amount of marks to a Player
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
     * Method to set this player's spawn point based on the discarded power up
     * @param powerUpColor is the color of the discarded power up
     */

    public void chooseSpawnPoint(String powerUpColor) {
        for (int i = 0; i < this.game.getMap ().getDimension (); i++) {
            Square s = this.game.getMap ().getSquare (i);
            if (s.getSquareColor ().equals(powerUpColor) && s.isSpawnPoint()) {
                this.setPosition (s);
            }
        }
        for (PowerUp pup : ownedPowerUps){
            if (pup.getType().getColor().equals(powerUpColor)){
                this.game.getPowerUpsDeck ().discardCard (pup);
            }
        }
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
     * @param player
     * @return true (if this player can see p), false (otherwise)
     */

    public boolean canSee(Player player) {
        return this.canSee(player.getPosition());

    }

    public boolean canSee(Square square) {
        if (this.getPosition().isInTheSameRoom(square)) {
            return true;
        } else {
            for (int i = 0; i < this.getPosition().getLinks().size(); i++) {
                Square s = this.getGame().getMap().getSquare(this.getPosition().getLinks().get(i));
                if (s.getSquareColor().equals(square.getSquareColor())) {
                    return true;
                }
            }
        }
        return false;
    }
}