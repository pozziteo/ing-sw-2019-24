package adrenaline.model;

import adrenaline.model.deck.*;
import adrenaline.model.deck.powerup.PowerUp;
import adrenaline.model.deck.PowerUpsDeck;
import adrenaline.model.deck.PowerUpsDeckCreator;
import adrenaline.model.map.*;
import adrenaline.model.map.Map;
import adrenaline.model.player.Action;
import adrenaline.model.player.Player;

import java.io.FileNotFoundException;
import java.io.Serializable;
import java.util.*;

/**
 * Class used to access the entire model package
 */

public class Game implements Serializable {

    private static final long serialVersionUID = -8605988129141962335L;

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
    private Map map;
    private List<Player> players;
    private Player firstPlayer;
    private int skullsRemaining;
    private WeaponsDeck weaponsDeck;
    private PowerUpsDeck powerUpsDeck;
    private TilesDeck tilesDeck;
    private List<Player> ranking;
    private int finalFrenzyIndex;
    private boolean finalFrenzy;
    private boolean startGame;
    private boolean endGame;
    private LinkedList<Action> currentTurnActions;
    private List<String> deathTrack;

    public Game(String[] playerNames) {
        this.gameID = new Random().nextInt() * 1000000;
        this.currentTurn = 0;
        this.finalFrenzy = false;
        this.skullsRemaining = 1;//TODO rimettere l'8
        this.players = new ArrayList<>();
        this.ranking = new ArrayList<>();
        this.startGame = false;
        this.endGame = false;
        this.finalFrenzyIndex = 0;
        this.currentTurnActions = new LinkedList<>();
        this.deathTrack = new ArrayList<>();

        this.weaponsDeck = new WeaponsDeckCreator().createDeck();
        this.powerUpsDeck = new PowerUpsDeckCreator().createDeck();
        this.tilesDeck = new TilesDeckCreator().createDeck();

        List<PlayerColor> colors = Arrays.asList(PlayerColor.values());
        Collections.shuffle(colors);

        int n = playerNames.length;

        while (n > 0) {
            Player p = new Player(playerNames[n - 1], this, colors.get(n - 1).getColor());
            p.getOwnedPowerUps().add((PowerUp) this.powerUpsDeck.drawCard());
            p.getOwnedPowerUps().add((PowerUp) this.powerUpsDeck.drawCard());
            this.players.add(p);
            n--;
        }

        this.ranking.addAll(players);
        this.firstPlayer = this.players.get(0);
    }

    /**
     * Method to start the game, it lets the players pick a weapon or a tile
     */
    public void startGame() {
        this.startGame = true;
        for (Square square : map.getArena()) {
            if (square.isSpawnPoint()) {
                for (int i = 0; i < 3; i++) {
                    ((SpawnPoint) square).setWeapons((Weapon) weaponsDeck.drawCard());
                }
            } else {
                ((NormalSquare) square).setPlacedTile((Tile) tilesDeck.drawCard());
            }
        }
    }

    // *********************************************
    // GETTER METHODS
    // *********************************************

    /**
     * Getter method to obtain the current game's id
     *
     * @return gameId
     */

    public int getGameID() {
        return this.gameID;
    }

    /**
     * Getter method to obtain the turn number in a game
     *
     * @return turn number
     */

    public int getCurrentTurn() {
        if (currentTurn < players.size())
            return currentTurn;
        else
            currentTurn = 0;
        return currentTurn;
    }

    /**
     * Method to increment the turn counter
     */
    public void incrementTurn() {
        this.currentTurn++;
        if (finalFrenzy && currentTurn == finalFrenzyIndex)
            endGame = true;
        this.currentTurnActions = new LinkedList<> ( );
        replaceEmptySlots ( );
    }

    /**
     * Getter method to obtain the arena for a game
     *
     * @return arena
     */

    public Map getMap() {
        return this.map;
    }


    /**
     * Getter method to obtain the list of players in a game
     *
     * @return players
     */

    public List<Player> getPlayers() {
        return this.players;
    }

    /**
     * Getter method to know how many kills can still be made before the game is ended
     * If skullsRemaining == 0 the game is ended
     *
     * @return number of remaining skulls
     */

    public int getSkullsRemaining() {
        return this.skullsRemaining;
    }

    /**
     * Getter method to obtain the deck of weapons in a game
     *
     * @return weaponsDeck
     */

    public WeaponsDeck getWeaponsDeck() {
        return this.weaponsDeck;
    }

    /**
     * Getter method to obtain the deck of power ups in a game
     *
     * @return powerUpsDeck
     */

    public PowerUpsDeck getPowerUpsDeck() {
        return this.powerUpsDeck;
    }

    /**
     * Getter method to obtain the deck of tiles in a game
     *
     * @return tilesDeck
     */

    public TilesDeck getTilesDeck() {
        return this.tilesDeck;
    }

    /**
     * Getter method to obtain the first player
     *
     * @return the first player of the game
     */
    public Player getFirstPlayer() {
        return this.firstPlayer;
    }

    /**
     * Getter method to obtain the list of players who won a round in the same session
     *
     * @return winnersList
     */

    public List<Player> getRanking() {
        return this.ranking;
    }

    /**
     * Getter method to establish if the game is in the Final Frenzy turn
     *
     * @return true if the game is in Final Frenzy, otherwise false
     */
    public boolean isFinalFrenzy() {
        return this.finalFrenzy;
    }

    /**
     * Getter method to establish if the game is started
     * @return true if it started
     */
    public boolean isStartGame() {
        return this.startGame;
    }

    /**
     * Getter method to establish if the game is ending
     * @return true if the game is ending
     */
    public boolean isEndGame() {
        return this.endGame;
    }

    /**
     * Method to determine who made the most points and won the game
     *
     * @return winner
     */

    public Player getWinner() {
        return this.ranking.get(0);
    }

    /**
     * Method to set how many actions a player made
     * @param a is the type of action
     */
    public void setCurrentAction(Action a) {
        if (currentTurnActions.size() < 2) {
            currentTurnActions.add(a);
        }
    }

    /**
     * Method to update the list of current action
     * @param a is the type of action
     */
    public void updateCurrentAction(Action a) {
        this.currentTurnActions.remove(getCurrentAction ());
        this.currentTurnActions.add(a);
    }

    /**
     * Getter method
     * @return the last action performed by a player
     */
    public Action getCurrentAction() {
        return this.currentTurnActions.getLast();
    }

    /**
     * Getter method
     * @return the number of action made
     */
    public int getCurrentTurnActionNumber() {
        return this.currentTurnActions.size();
    }

    // **********************************************
    // SETTER METHODS
    // **********************************************

    /**
     * Setter method to set the arena for a game
     */

    public void setArena(String fileName) {
        try {
            this.map = new ArenaBuilder().createMap(fileName);
        } catch (FileNotFoundException exc) {
            System.err.println("Error: Invalid map file selected");
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
        this.ranking.sort((Player o1, Player o2) -> {
                    Integer points1 = o1.getBoard().getPointTokens();
                    Integer points2 = o2.getBoard().getPointTokens();
                    if (!points1.equals(points2)) {
                        return points2.compareTo(points1);
                    } else {
                        Integer tiedPoints1 = getDeathTrackPlayerPoint(o1);
                        Integer tiedPoints2 = getDeathTrackPlayerPoint(o2);
                        return tiedPoints2.compareTo(tiedPoints1);
                    }
                }
        );
    }

    /**
     * Getter method
     * @param p is the player
     * @return how many player's points are on the death track
     */
    public int getDeathTrackPlayerPoint(Player p){
        int a=0;
        for (String s : deathTrack) {
            if (s.equalsIgnoreCase(p.getPlayerColor())) {
                a++;
            }
        }
        return a;
    }

    public boolean isBeforeFirstPlayer(Player player) {
        return (!player.equals(firstPlayer) && players.indexOf(player) < players.indexOf(firstPlayer));
    }

    /**
     * Method to find a player by his nickname
     * @param nickname is the nickname of the player to search
     * @return the player if he was in the game
     */
    public Player findByNickname(String nickname) {
        for (Player p : players) {
            if (p.getPlayerName().equals(nickname))
                return p;
        }
        return null;
    }

    /**
     * Method to fill an empty slots after a player draws a card
     */
    private void replaceEmptySlots() {
        for (Square s : map.getArena()) {
            if (s.isSpawnPoint()) {
                for (int i = 0; i < ((SpawnPoint) s).getWeapons().length; i++) {
                    if (((SpawnPoint) s).getWeapons()[i] == null) {
                        ((SpawnPoint) s).setWeapons((Weapon) weaponsDeck.drawCard());
                    }
                }
            } else {
                Tile tile = ((NormalSquare) s).getPlacedTile();
                if (tile == null)
                    ((NormalSquare) s).setPlacedTile((Tile) tilesDeck.drawCard());
            }
        }
    }

    /**
     * Method that adds one death to the 'DeadPlayer',
     * adds the killer in the 'deathTrack' and
     * reduce the number of skulls remaining
     * @param deadPlayer is the one who died
     */
    private void handlePlayerDeath(Player deadPlayer){
        if(deadPlayer.getBoard().getDamageTaken().size()>10)
            deathTrack.add(deadPlayer.getBoard().getDamageTaken().get(10));
        if(deadPlayer.getBoard().getDamageTaken().size()>11)
            deathTrack.add(deadPlayer.getBoard().getDamageTaken().get(11));
        deadPlayer.addDeaths();
        skullsRemaining--;
    }

    /**
     * Method for the OverKill
     *
     * @param deadPlayer is the player who died
     */
    public void overKill(Player deadPlayer) {
        if (deadPlayer.getBoard().getDamageTaken().size() >= 12)
            for (Player p : players)
                if (p.getPlayerColor().equalsIgnoreCase(deadPlayer.getBoard().getDamageTaken().get(11)))
                    deadPlayer.giveMark(1, p);
    }

    /**
     * Method that assigns points
     *
     * @param deadPlayer is the one who died
     */
    public void givePoints(Player deadPlayer) {
        List<Player> toCompute = new ArrayList<>();
        int point;

        //assegno punteggio a point(punteggio max guadagnabile) in base a quante morti ha il giocatore morto
        switch (deadPlayer.getDeaths()) {
            case 0:
                point = 8;
                break;
            case 1:
                point = 6;
                break;
            default:
                point = 4;
                break;
        }
        if (isFinalFrenzy()) point = 2;

        //aggiungo in giocatori che fanno almeno un danno in 'toCompute'
        for (Player p : players) {
            if (deadPlayer.getBoard().getDamageAmountGivenByPlayer(p) >= 1) {
                toCompute.add(p);
            }
        }

        toCompute.sort((player1, player2) -> {
            Integer points1 = deadPlayer.getBoard().getDamageAmountGivenByPlayer(player1);
            Integer points2 = deadPlayer.getBoard().getDamageAmountGivenByPlayer(player2);
            if (!points1.equals(points2)) {
                return points2.compareTo(points1);
            } else {
                Integer index1 = deadPlayer.getBoard().getDamageTaken().indexOf(player1.getPlayerColor());
                Integer index2 = deadPlayer.getBoard().getDamageTaken().indexOf(player2.getPlayerColor());
                return index1.compareTo(index2);
            }
        });

        //al primo giocatore assegno il massimo, al secondo 2 in meno e cosi` via
        for (Player p : toCompute) {
            if (deadPlayer.getBoard().isFirstBlood(p)) {
                p.getBoard().addPointTokens(1);
            }
            p.getBoard().addPointTokens(point);
            switch (point) {
                case 1:
                    break;
                case 2:
                    point = point - 1;
                    break;
                default:
                    point = point - 2;
                    break;
            }
        }
        //aggiunge una morte al 'deadPlayer'
        handlePlayerDeath (deadPlayer);
        if (getSkullsRemaining() == 0) {
            this.finalFrenzy = true;
            this.finalFrenzyIndex = currentTurn;
        }
    }

    /**
     * Method to replace a weapon
     * @param player is the player
     * @param weapon is the new weapon
     */
    public void replaceWeapon(Player player, Weapon weapon) {
        SpawnPoint playerPosition = (SpawnPoint) player.getPosition ();
        playerPosition.setWeapons (weapon);
    }
}