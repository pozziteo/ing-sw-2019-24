package adrenaline.model;

import adrenaline.data.data_for_view.MapData;
import adrenaline.network.Account;

import java.util.ArrayList;

//TODO javadoc

public class GameModel {
    private Game game;
    private ArrayList<Account> players;

    public GameModel() {
        this.players = new ArrayList<>();
    }

    public void startNewGame() {
        this.game = new Game(players.size ());
    }

    public Game getGame() {
        return this.game;
    }

    public void setPlayers(Account account) {
        this.players.add(account);
    }

    private MapData updateMapData(Account account) {
        return new MapData(account, game.getMap ().getArena ());
    }

    public void notifyNewMapData() {
        for (Account a : players) {
            MapData updatedMap = updateMapData (a);
            updatedMap.sendToView ();
        }
    }

}
