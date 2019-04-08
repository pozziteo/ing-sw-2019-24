package model;

import model.deck.*;
import model.map.*;
import model.player.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;

/**
 * Singleton class used to access the entire model package
 */

public class Game {
    private int gameID;
    private int currentTurn;
    private Map arena;
    private ArrayList<Player> players;
    private int skullsRemaining;
    private WeaponsDeck weaponsDeck;
    private PowerUpsDeck powerUpsDeck;
    private TilesDeck tilesDeck;
    private ArrayList<Player> winnersList;
    private static Game gameInstance;

    public Game() {
        this.gameID = 1;
        this.currentTurn = 1;
        this.weaponsDeck = setWeaponsDeck ();
        this.powerUpsDeck = setPowerUpDeck ();
        this.tilesDeck = setTilesDeck ();
        this.winnersList = null;
    }

    /**
     * Getter method implemented with Singleton design pattern
     * @return instance of Game
     */

    public static Game getGameInstance() {
        if (gameInstance == null) {
            gameInstance = new Game();
        }
        return gameInstance;
    }

    /**
     * Getter method to obtain the current game's id
     * @return gameId
     */

    public int getGameID() {
        return this.gameID;
    }

    /**
     * Setter method to set the current game's Id
     * @param id
     */

    public void setGameID(int id) {
        this.gameID = id;
    }

    /**
     * Getter method to obtain the turn number in a game
     * @return turn number
     */

    public int getCurrentTurn() {
        return this.currentTurn;
    }

    /**
     * Setter method to set the turn number in a game
     * @param turn
     */

    public void setCurrentTurn(int turn) {
        this.currentTurn = turn;
    }

    /**
     * incrementTurn() increments the turn number by one after a turn is completed
     */

    public void incrementTurn() {
        int turn = this.getCurrentTurn () + 1;
        this.setCurrentTurn (turn);
    }

    /**
     * Getter method to obtain the arena for a game
     * @return arena
     */

    public Map getArena() {
        return this.arena;
    }

    /**
     * Setter method to set the arena for a game
     * @param fileName which is the name of the file where the chosen arena is written
     * @return arena
     * @throws FileNotFoundException
     */

    public void setArena(String fileName) throws FileNotFoundException {
        this.arena = Map.getInstance (fileName);
    }

    /**
     * Getter method to obtain the list of players in a game
     * @return players
     */

    public ArrayList<Player> getPlayers() {
        return this.players;
    }

    /**
     * Setter method to set the list of players in a game
     * @param players
     */

    public void setPlayers(ArrayList<Player> players) {
        this.players = players;
    }

    /**
     * Getter method to know how many kills can still be made before the game is ended
     * If skullsRemaining == 0 the game is ended
     * @return number of remaining skulls
     */

    public int getSkullsRemaining() {
        return this.skullsRemaining;
    }

    /**
     * Setter method to set the amount of kills in a game
     * @param skulls
     */

    public void setSkullsRemaining(int skulls) {
        this.skullsRemaining = skulls;
    }

    public WeaponsDeck getWeaponsDeck() {
        return this.weaponsDeck;
    }

    public WeaponsDeck setWeaponsDeck() {
        WeaponsDeckCreator deckCreator = new WeaponsDeckCreator();
        return this.weaponsDeck = deckCreator.createDeck();
    }

    public PowerUpsDeck getPowerUpsDeck() {
        return this.powerUpsDeck;
    }

    public PowerUpsDeck setPowerUpDeck() {
        PowerUpsDeckCreator deckCreator = new PowerUpsDeckCreator ();
        return this.powerUpsDeck = deckCreator.createDeck();
    }

    public TilesDeck getTilesDeck() {
        return this.tilesDeck;
    }

    public TilesDeck setTilesDeck() {
        TilesDeckCreator deckCreator = new TilesDeckCreator();
        return this.tilesDeck = deckCreator.createDeck();
    }

    public ArrayList<Player> getWinnersList() {
        return this.winnersList;
    }

    public Player getWinner() {
        Player winner = new Player();
        int maxPoints = 0;
        for (int i=0; i < players.size (); i++) {
            if (players.get(i).getPointTokens () > maxPoints) {
                maxPoints = players.get(i).getPointTokens ();
                winner = players.get (i);
            }
        }
        return winner;
    }

    public void setWinnersList(Player winner) {
        this.winnersList.add(winner);
    }

    public void endGame(Player winner) {
        this.gameID++;
        this.currentTurn = 0;
        this.arena = null;
        this.weaponsDeck = null;
        this.powerUpsDeck = null;
        this.tilesDeck = null;
        this.setWinnersList(winner);
    }

}
