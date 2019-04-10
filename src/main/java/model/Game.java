package model;

import model.deck.*;
import model.map.*;
import model.player.*;

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Class used to access the entire model package
 */

public class Game {
    private int gameID;
    private int currentTurn;
    private Map arena;
    private List<Player> players;
    private Player firstPlayer;
    private int skullsRemaining;
    private WeaponsDeck weaponsDeck;
    private PowerUpsDeck powerUpsDeck;
    private TilesDeck tilesDeck;
    private List<Player> ranking;
    private boolean endGame;

    public Game(int numberOfPlayers) {
        this.gameID = (int) (Math.random () * 1000000);
        this.currentTurn = 1;
        this.endGame = false;
        this.skullsRemaining = 8;
        this.players = new ArrayList<> ();
        this.ranking = new ArrayList<> ();
        this.setArena();

        WeaponsDeckCreator deckCreator1 = new WeaponsDeckCreator();
        this.weaponsDeck = deckCreator1.createDeck();

        PowerUpsDeckCreator deckCreator2 = new PowerUpsDeckCreator ();
        this.powerUpsDeck = deckCreator2.createDeck();

        TilesDeckCreator deckCreator = new TilesDeckCreator();
        this.tilesDeck = deckCreator.createDeck();

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

        Collections.shuffle(this.players);
        this.ranking.addAll(players);
        this.firstPlayer = this.players.get (0);
    }

    // *********************************************
    // GETTER METHODS
    // *********************************************

    /**
     * Getter method to obtain the current game's id
     * @return gameId
     */

    public int getGameID() {
        return this.gameID;
    }

    /**
     * Getter method to obtain the turn number in a game
     * @return turn number
     */

    public int getCurrentTurn() {
        return this.currentTurn;
    }

    /**
     * Getter method to obtain the arena for a game
     * @return arena
     */

    public Map getArena() {
        return this.arena;
    }


    /**
     * Getter method to obtain the list of players in a game
     * @return players
     */

    public List<Player> getPlayers() {
        return this.players;
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
     * Getter method to obtain the deck of weapons in a game
     * @return weaponsDeck
     */

    public WeaponsDeck getWeaponsDeck() {
        return this.weaponsDeck;
    }

    /**
     * Getter method to obtain the deck of power ups in a game
     * @return powerUpsDeck
     */

    public PowerUpsDeck getPowerUpsDeck() {
        return this.powerUpsDeck;
    }

    /**
     * Getter method to obtain the deck of tiles in a game
     * @return tilesDeck
     */

    public TilesDeck getTilesDeck() {
        return this.tilesDeck;
    }

    /**
     * Getter method to obtain the list of players who won a round in the same session
     * @return winnersList
     */

    public List<Player> getRanking() {
        return this.ranking;
    }

    /**
     * Method to determine who made the most points and won the game
     * @return winner
     */

    public Player getWinner() {
        return this.ranking.get(0);
    }

    // **********************************************
    // SETTER METHODS
    // **********************************************

    /**
     * Setter method to draw a Tile from the deck and place it on Square s
     */

    public void setTileOnSquare(Square s) {
        Tile t = (Tile) this.getTilesDeck ().drawCard ();
        s.setPlacedTile(t);
    }


    /**
     * Setter method to set the arena for a game
     */

    public void setArena() {
        try {
            this.arena = new ArenaBuilder().createMap();
        } catch (FileNotFoundException exc) {
            System.err.println("Error: Invalid map file selected");
            exc.printStackTrace();
        }
    }

    // *********************************************
    // METHODS
    // *********************************************

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

}
