package model;

import model.deck.*;
import model.map.*;
import model.map.Map;
import model.player.*;

import java.io.FileNotFoundException;
import java.util.*;

/**
 * Class used to access the entire model package
 */

public class Game {

    private enum PlayerColor {
        RED("Red"),
        YELLOW("Yellow"),
        BLUE("Blue"),
        GREEN("Green"),
        GREY("Grey");

        private String color;

        PlayerColor(String color) {
            this.color = color;
        }

        private String getColor() {
            return color;
        }
    }

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
    private boolean finalFrenzy;
    private boolean endGame;

    public Game(int numberOfPlayers) {
        this.gameID = new Random().nextInt() * 1000000;
        this.currentTurn = 1;
        this.finalFrenzy = false;
        this.skullsRemaining = 8;
        this.players = new ArrayList<> ();
        this.ranking = new ArrayList<> ();
        this.endGame = false;
        this.setArena();

        this.weaponsDeck = new WeaponsDeckCreator().createDeck();
        this.powerUpsDeck = new PowerUpsDeckCreator().createDeck();
        this.tilesDeck = new TilesDeckCreator().createDeck();

        List<PlayerColor> colors = Arrays.asList(PlayerColor.values());
        Collections.shuffle(colors);

        while (numberOfPlayers > 0) {
            Player p = new Player(this, colors.get(numberOfPlayers-1).getColor());
            p.getOwnedPowerUps ().add ((PowerUp) this.powerUpsDeck.drawCard ());
            p.getOwnedPowerUps ().add ((PowerUp) this.powerUpsDeck.drawCard ());
            this.players.add(p);
            numberOfPlayers--;
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
            this.finalFrenzy = true;
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
     * Getter method to obtain the first player
     * @return the first player of the game
     */
    public Player getFirstPlayer() {
        return this.firstPlayer;
    }
    /**
     * Getter method to obtain the list of players who won a round in the same session
     * @return winnersList
     */

    public List<Player> getRanking() {
        return this.ranking;
    }

    /**
     * Getter method to establish if the game is in the Final Frenzy turn
     * @return true if the game is in Final Frenzy, otherwise false
     */
    public boolean isFinalFrenzy() {
        return this.finalFrenzy;
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

    public void setTileOnSquare(NormalSquare s) {
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

    /**
     * Setter method to change the boolean value of endGame and end this game
     */

    public void setEndGame(boolean value) {
        this.endGame = value;
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
