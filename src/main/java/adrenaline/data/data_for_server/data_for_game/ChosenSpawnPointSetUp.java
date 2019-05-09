package adrenaline.data.data_for_server.data_for_game;

import adrenaline.data.data_for_server.DataForServer;
import adrenaline.model.GameModel;

public class ChosenSpawnPointSetUp extends DataForServer implements DataForController {
    private String spawnPointColor;

    public ChosenSpawnPointSetUp(String nickname, String color) {
        super(nickname);
        this.spawnPointColor = color;
    }

    @Override
    public void updateGame(GameModel game) {
        //TODO
    }
}
