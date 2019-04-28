package adrenaline.model;

import adrenaline.data.data_for_view.MapData;
import adrenaline.network.Account;

//TODO javadoc

public class GameModel {
    private Game game;
    private int numberOfPlayers;

    public GameModel(int n) {
        this.numberOfPlayers = n;
    }

    public void startNewGame() {
        this.game = new Game(numberOfPlayers);
    }

    public Game getGame() {
        return this.game;
    }

    public void setMap(String fileName) {
        this.game.setArena (fileName);
    }

    private MapData updateMapData(Account account) {
        return new MapData(account, game.getMap ().getArena ());
    }

}
