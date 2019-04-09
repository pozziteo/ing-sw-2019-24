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
    private ArrayList<Player> ranking;
    private static Game gameInstance;

    public Game() {
        this.gameID = 1;
        this.currentTurn = 1;
        setWeaponsDeck ();
        setPowerUpDeck ();
        setTilesDeck ();
        this.ranking = new ArrayList<>(players);
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
        if (this.skullsRemaining == 0) {
            Player w = getWinner ();
            this.endGame (w);
        }
        return this.skullsRemaining;
    }

    /**
     * Setter method to set the amount of kills in a game
     * @param skulls
     */

    public void setSkullsRemaining(int skulls) {
        this.skullsRemaining = skulls;
    }

    /**
     * Getter method to obtain the deck of weapons in a game
     * @return weaponsDeck
     */

    public WeaponsDeck getWeaponsDeck() {
        return this.weaponsDeck;
    }

    /**
     * Setter method to create the deck of weapons for a game
     */

    public void setWeaponsDeck() {
        WeaponsDeckCreator deckCreator = new WeaponsDeckCreator();
        this.weaponsDeck = deckCreator.createDeck();
    }

    /**
     * Getter method to obtain the deck of power ups in a game
     * @return powerUpsDeck
     */

    public PowerUpsDeck getPowerUpsDeck() {
        return this.powerUpsDeck;
    }

    /**
     * Setter method to create the deck of power ups for a game
     */

    public void setPowerUpDeck() {
        PowerUpsDeckCreator deckCreator = new PowerUpsDeckCreator ();
        this.powerUpsDeck = deckCreator.createDeck();
    }

    /**
     * Getter method to obtain the deck of tiles in a game
     * @return tilesDeck
     */

    public TilesDeck getTilesDeck() {
        return this.tilesDeck;
    }

    /**
     * Setter method to create the deck of tiles for a game
     */

    public void setTilesDeck() {
        TilesDeckCreator deckCreator = new TilesDeckCreator();
        this.tilesDeck = deckCreator.createDeck();
    }

    /**
     * Getter method to obtain the list of players who won a round in the same session
     * @return winnersList
     */

    public ArrayList<Player> getRanking() {
        return this.ranking;
    }

    /**
     * Method to determine who made the most points and won the game
     * @return winner
     */

    public Player getWinner() {
        Player winner = new Player();
        int maxPoints = 0;
        for (Player p : players) {
            if (p.getPointTokens () > maxPoints) {
                maxPoints = p.getPointTokens ();
                winner = p;
            }
        }
        return winner;
    }

    /**
     * Setter method to add a player to the winners list
     * @param winner
     */

    public void setRanking(Player winner) {
        this.ranking.add(winner);
    }

    /**
     * Method to end a round within the same session
     * @param winner
     */

    public void endGame(Player winner) {
        this.gameID++;
        this.currentTurn = 0;
        this.arena = null;
        this.weaponsDeck.reloadDeck ();
        this.powerUpsDeck.reloadDeck ();
        this.tilesDeck.reloadDeck ();
        this.setRanking(winner);
    }

}
