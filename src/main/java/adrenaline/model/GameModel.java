package adrenaline.model;

import adrenaline.data.data_for_view.MapData;
import adrenaline.network.Account;

//TODO javadoc

public class GameModel {
    private Game game;
    private String[] playerNames;

    public GameModel(String[] playerNames) {
        this.playerNames = playerNames;
    }

    public void startNewGame() {
        this.game = new Game(playerNames);
    }

    public Game getGame() {
        return this.game;
    }

    public void setMap(String fileName) {
        this.game.setArena (fileName);
    }

    public MapData updateMapData(Account account) {
        return new MapData(account, game.getMap ().getArena ());
    }

}
