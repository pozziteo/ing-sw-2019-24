package adrenaline.model;

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

}
