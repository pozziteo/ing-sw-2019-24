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
import java.util.stream.Collectors;

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
    private boolean finalFrenzy;
    private boolean endGame;
    private List<Action> currentTurnActions;

    public Game(String[] playerNames) {
        this.gameID = new Random().nextInt() * 1000000;
        this.currentTurn = 0;
        this.finalFrenzy = false;
        this.skullsRemaining = 8;
        this.players = new ArrayList<> ();
        this.ranking = new ArrayList<> ();
        this.endGame = false;
        this.currentTurnActions = new ArrayList<> ();

        this.weaponsDeck = new WeaponsDeckCreator ().createDeck();
        this.powerUpsDeck = new PowerUpsDeckCreator().createDeck();
        this.tilesDeck = new TilesDeckCreator ().createDeck();

        List<PlayerColor> colors = Arrays.asList(PlayerColor.values());
        Collections.shuffle(colors);

        int n = playerNames.length;

        while (n > 0) {
            Player p = new Player(playerNames[n-1], this, colors.get(n-1).getColor());
            p.getOwnedPowerUps ().add ((PowerUp) this.powerUpsDeck.drawCard ());
            p.getOwnedPowerUps ().add ((PowerUp) this.powerUpsDeck.drawCard ());
            this.players.add(p);
            n--;
        }

        this.ranking.addAll(players);
        this.firstPlayer = this.players.get (0);
    }

    public void startGame() {
        for (Square square : map.getArena ()) {
            if (square.isSpawnPoint()) {
                for (int i = 0; i < 3; i++) {
                    ((SpawnPoint) square).setWeapons ((Weapon) weaponsDeck.drawCard ());
                }
            } else {
                ((NormalSquare) square).setPlacedTile ((Tile) tilesDeck.drawCard ());
            }
        }
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
        if (currentTurn < players.size ())
            return currentTurn;
        else
            currentTurn = 0;
        return currentTurn;
    }

    public void incrementTurn() {
        this.currentTurnActions = new ArrayList<> ();
        replaceEmptySlots();
        this.currentTurn++;
    }

    /**
     * Getter method to obtain the arena for a game
     * @return arena
     */

    public Map getMap() {
        return this.map;
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

    public boolean isEndGame() {
        return this.endGame;
    }

    /**
     * Method to determine who made the most points and won the game
     * @return winner
     */

    public Player getWinner() {
        return this.ranking.get(0);
    }

    public void setCurrentAction(Action a) {
        if (currentTurnActions.size() < 2) {
            currentTurnActions.add (a);
        }
    }

    public int getCurrentTurnActionNumber() {
        return this.currentTurnActions.size();
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

    public void setArena(String fileName) {
        try {
            this.map = new ArenaBuilder ().createMap(fileName);
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

    public Player findByNickname(String nickname) {
        for (Player p : players) {
            if (p.getPlayerName ().equals(nickname))
                return p;
        }
        return null;
    }

    private void replaceEmptySlots() {
        for (Square s : map.getArena ()) {
            if (s.isSpawnPoint()) {
                for (int i = 0; i < ((SpawnPoint)s).getWeapons ().length; i++) {
                    if (((SpawnPoint)s).getWeapons ()[i] == null) {
                        ((SpawnPoint)s).setWeapons ((Weapon) weaponsDeck.drawCard ());
                    }
                }
            } else {
                Tile tile = ((NormalSquare)s).getPlacedTile ();
                if (tile == null)
                    ((NormalSquare) s).setPlacedTile ((Tile) tilesDeck.drawCard ());
            }
        }
    }

    public void replaceWeapon(Weapon weapon) {
        for (Square s : map.getArena ()) {
            if (s.isSpawnPoint()) {
                for (int i = 0; i < ((SpawnPoint)s).getWeapons ().length; i++) {
                    if (((SpawnPoint)s).getWeapons ()[i] == null) {
                        ((SpawnPoint)s).setWeapons (weapon);
                        break;
                    }
                }
            }
        }
    }

    /**
     * Method that assigns points
     * @param deadPlayer is the one who died
     */
    public void givePoints(Player deadPlayer){
        List<Player> toCompute = new ArrayList<>();
        List<Player> sortedPlayers;
        int point;

        //assegno punteggio a point(punteggio max guadagnabile) in base a quante morti ha il giocatore morto
        switch (deadPlayer.getDeaths()){
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
        if(isFinalFrenzy()) point = 2;

        //aggiungo in giocatori che fanno almeno un danno in 'toCompute'
        for (Player p: players){
            if(deadPlayer.getBoard().getDamageAmountGivenByPlayer(p) > 0) {
                toCompute.add(p);
            }
        }
        //ordino tutti i giocatori in base al numero di danni inflitti
        sortedPlayers = sortPlayerByHits(toCompute, deadPlayer);

        //al primo giocatore assegno il massimo, al secondo 2 in meno e cosi` via
        for(Player p: sortedPlayers){
            if (deadPlayer.getBoard().isFirstBlood(p)) {
                p.addPointTokens(1);
            }
            p.addPointTokens(point);
            if (point == 2)
                point -= 1;
            else if (point == 1)
                point = point;
            else
                point -= 2;
        }
        //aggiunge una morte al 'deadPlayer'
        deadPlayer.addDeaths();
    }

    /**
     * Method that divides the players in two different lists: 'tiedPlayers' and 'list'(non-tying players)
     * @param toCompute is the list of all players who dealed damage to the 'deadPlayer'
     * @param deadPlayer is the player who died
     * @return a final list, sorted by the order required to give points
     */
    private List<Player> sortPlayerByHits(List<Player> toCompute, Player deadPlayer){
        List<Player> list = new ArrayList<>();
        List<Player> tiedPlayers = new ArrayList<>();
        List<Player> finalList;
        for (int i =0; i<toCompute.size(); i++){
            for (Player p: toCompute){
                for(Player p1: toCompute.subList(toCompute.indexOf(p)+1, toCompute.size())){
                    if(deadPlayer.getBoard().getDamageAmountGivenByPlayer(p)==deadPlayer.getBoard().getDamageAmountGivenByPlayer(p1)){
                        tiedPlayers.add(p);
                        tiedPlayers.add(p1);
                    }else if(deadPlayer.getBoard().getDamageAmountGivenByPlayer(p)>deadPlayer.getBoard().getDamageAmountGivenByPlayer(p1)) {
                        list.set(i, p);
                    }else {
                        list.set(i, p1);
                    }
                }
            }
        }
        tiedPlayers = tiedPlayers.stream().distinct().collect(Collectors.toList());
        List<Player> tiedPlayersFinal = compareTiedPlayers(tiedPlayers, deadPlayer);
        finalList = mergeArrays(tiedPlayersFinal, list, deadPlayer);
        return finalList;
    }

    /**
     * Method that sorts a list of tying players
     * @param tiedPlayers is the list
     * @param deadPlayer is the player who died
     * @return a sorted list
     */
    private List<Player> compareTiedPlayers(List<Player> tiedPlayers, Player deadPlayer){
        List<Integer> position = new ArrayList<>();
        for (Player p: tiedPlayers){
            position.add(deadPlayer.getBoard().getDamageTaken().indexOf(p.getPlayerColor()));
        }
        Collections.sort(position);
        for (int i=0; i<position.size(); i++){
            for (Player p: tiedPlayers){
                if(p.getPlayerColor().equalsIgnoreCase(deadPlayer.getBoard().getDamageTaken().get(i)))
                    tiedPlayers.set(i, p);
            }
        }
        return tiedPlayers;
    }

    /**
     * Method that merges the list of tying and non-tying players
     * @param tiedPlayers is the list of tying players
     * @param list is the list of non-tying players
     * @param deadPlayer is the player who died
     * @return a sorted list of 'tiedPlayers' and 'list'
     */
    private List<Player> mergeArrays(List<Player> tiedPlayers, List<Player> list, Player deadPlayer) {
        for(Player p: tiedPlayers)
            for (Player p1 : list)
                if (deadPlayer.getBoard().getDamageAmountGivenByPlayer(p) > deadPlayer.getBoard().getDamageAmountGivenByPlayer(p1)) {
                    //dentro list in posizione p1 concateno tiedPlayers
                    list.addAll(list.indexOf(p1), tiedPlayers);
                    break;
                }
        return list;
    }
}
