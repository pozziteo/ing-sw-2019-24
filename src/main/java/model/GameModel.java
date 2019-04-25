package model;

import controller.Controller;
import data.data_for_view.MapData;
import network.visitors.Account;

import java.util.ArrayList;

public class GameModel {
    private Game game;
    private Controller controller;
    private ArrayList<Account> players;

    public GameModel(Game game, Controller controller) {
        this.game = game;
        this.controller = controller;
        this.players = new ArrayList<>();
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
