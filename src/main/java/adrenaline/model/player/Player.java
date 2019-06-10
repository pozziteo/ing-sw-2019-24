package adrenaline.model.player;

import adrenaline.exceptions.NotEnoughAmmoException;
import adrenaline.model.Game;
import adrenaline.model.deck.Ammo;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.deck.Weapon;
import adrenaline.model.deck.powerup.PowerUpType;
import adrenaline.model.map.Square;

import java.util.*;

/**
 * Player is the class that represents every client taking part in the game.
 */

public class Player {
    private Game game;
    private String playerName;
    private String playerColor;
    private Board playerBoard;
    //private int pointTokens;
    private Square position = null;
    private ArrayList<Weapon> ownedWeapons;
    private ArrayList<PowerUp> ownedPowerUps;
    private int givenMarks;
    private Action[] performedActions;
    private int deaths;
    private boolean waitingForRespawn;

    public Player() {

    }

    public Player(String playerName, Game game, String color) {
        this.game = game;
        this.playerName = playerName;
        this.playerColor = color;
        this.playerBoard = new Board(this);
       // this.pointTokens = 0;
        this.deaths = 0;
        this.ownedWeapons = new ArrayList<>();
        this.ownedPowerUps = new ArrayList<>();
        this.performedActions = new Action[2];
        this.waitingForRespawn = false;
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
     *Getter method for player's deaths
     * @return the number of deaths
     */
    public int getDeaths(){
        return this.deaths;
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
        square.setPlayerOnSquare (this);
    }

    /**
     * Getter method to obtain a player's board
     * @return Board
     */

    public Board getBoard(){return this.playerBoard;}


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

    public boolean isWaitingForRespawn() {
        return this.waitingForRespawn;
    }

    public void setWaitingForRespawn(boolean value) {
        this.waitingForRespawn = value;
    }

    public boolean hasUsablePowerUps() {
        for (PowerUp p : ownedPowerUps) {
            if (p.getPowerUpsName ().equals("Newton") || p.getPowerUpsName ().equals("Teleporter"))
                return true;
        }
        return false;
    }

    /**
     * Method that increments the number of deaths of this
     */
    public void addDeaths(){
        this.deaths++;
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
            if (pup.getAmmo().getColor().equals(powerUpColor)){
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

    /**
     * Method to check if a player has the Tagback Grenade
     * @return true if the player does have it
     */
    public boolean hasGrenade(){
        for (PowerUp p: ownedPowerUps){
            if(p.getType()== PowerUpType.TAGBACK_GRENADE){
                return true;
            }
        }
        return false;
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

    public void reloadWeapon(Weapon toReload) throws NotEnoughAmmoException {
        if (hasEnoughAmmo (toReload.getType ().getReloadingAmmo ())) {
            ownedWeapons.add(toReload);
            playerBoard.getUnloadedWeapons ().remove(toReload);
            payAmmo(toReload.getType ().getReloadingAmmo ());
        } else
            throw new NotEnoughAmmoException ();
    }

    public Weapon findLoadedWeapon(String name) {
        Weapon weapon = null;
        for (Weapon w : ownedWeapons) {
            if (w.getWeaponsName ().equals(name)) {
                weapon = w;
                break;
            }
        }
        return weapon;
    }

    public Weapon findUnloadedWeapon(String name) {
        Weapon weapon = null;
        for (Weapon w : playerBoard.getUnloadedWeapons ()) {
            if (w.getWeaponsName ().equals(name)) {
                weapon = w;
                break;
            }
        }
        return weapon;
    }

    protected void deathEvent(){
        if(this.getBoard().getDamageTaken().size()==12){
            this.getGame().overKill(this);
        }
        this.getGame().givePoints(this);
    }

    public boolean hasEnoughAmmo(List<Ammo> neededAmmo) {
        List<Ammo> ammos = new ArrayList<> (playerBoard.getOwnedAmmo ());

        for (Ammo a : neededAmmo) {
            if (! ammos.remove(a))
                return false;
        }

        return true;
    }

    public void payAmmo(List<Ammo> neededAmmo) {
        for (Ammo a : neededAmmo) {
            playerBoard.getOwnedAmmo ().remove (a);
        }
    }
}