package model;

import model.deck.*;
import model.map.*;
import model.player.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * Class used to access the entire model package
 */

public class Game {
    private int gameID;
    private int currentTurn;
    private Map arena;
    private List<Player> players;
    private int skullsRemaining;
    private WeaponsDeck weaponsDeck;
    private PowerUpsDeck powerUpsDeck;
    private TilesDeck tilesDeck;
    private List<Player> ranking;
    private boolean endGame;

    public Game(int numberOfPlayers) {
        this.gameID = 1;
        this.currentTurn = 1;
        this.endGame = false;
        this.players = new ArrayList<> ();
        this.ranking = new ArrayList<> ();
        this.setArena();
        this.setWeaponsDeck ();
        this.setPowerUpDeck ();
        this.setTilesDeck ();

        Player p1 = new Player(this, "red");
        this.players.add(p1);
        Player p2 = new Player(this, "yellow");
        this.players.add(p2);
        Player p3 = new Player(this, "blue");
        this.players.add(p3);

        if(numberOfPlayers >= 4) {
            Player p4 = new Player(this, "green");
            this.players.add(p4);
        }
        if (numberOfPlayers == 5) {
            Player p5 = new Player(this, "grey");
            this.players.add(p5);
        }

        for (Player p : this.players) {
            this.ranking.add(p);
        }
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
        this.currentTurn++;
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
     * @return arena
     * @throws FileNotFoundException
     */
    public void setArena() {
        try {
            this.arena = new ArenaBuilder().createMap();
        } catch (FileNotFoundException exc) {
            System.err.println("Error: Invalid map file selected");
            exc.printStackTrace();
        }
    }

    /**
     * Getter method to obtain the list of players in a game
     * @return players
     */

    public List<Player> getPlayers() {
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
            this.endGame = true;
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
     * Setter method to draw a Tile from the deck and place it on Square s
     */

    public void setTile(Square s) {
        Tile t = (Tile) this.getTilesDeck ().drawCard ();
        s.setPlacedTile(t);
        this.getTilesDeck ().discardCard (t);
    }

    /**
     * Getter method to obtain the list of players who won a round in the same session
     * @return winnersList
     */

    public List<Player> getRanking() {
        return this.ranking;
    }

    /**
     * Method to update the ranking every time a player has been killed
     */

    public void updateRanking() {
        this.ranking.sort( (Player o1, Player o2) -> {
                Integer points1 = o1.getPointTokens();
                Integer points2 = o2.getPointTokens();
                return points2.compareTo(points1);
            }
        );
    }

    /**
     * Method to determine who made the most points and won the game
     * @return winner
     */

    public Player getWinner() {
       return this.ranking.get(0);
    }


}
