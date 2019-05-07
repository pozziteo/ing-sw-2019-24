package adrenaline.model;

import adrenaline.data.data_for_client.data_for_view.MapData;
import adrenaline.network.Account;

import java.io.Serializable;

//TODO javadoc

public class GameModel implements Serializable {
    private static final long serialVersionUID = 5055517717183645074L;

    private Game game;

    public GameModel(String[] playerNames) {
        this.game = new Game(playerNames);
    }

    public Game getGame() {
        return this.game;
    }

    //METHODS TO UPDATE THE VIEW

    public MapData updateMapData(Account account) {
        return new MapData(account, game.getMap ().getArena ());
    }

}
