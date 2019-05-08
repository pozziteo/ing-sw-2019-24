package adrenaline.data.data_for_server.data_for_game;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.GameModel;

public class MapSetUp extends DataForServer implements DataForController {
    private String filename;

    public MapSetUp(String nickname, String filename) {
        super(nickname);
        this.filename = filename;
    }

    @Override
    public void updateGame(GameModel game) {
        game.getGame ().setArena (filename);
    }
}
