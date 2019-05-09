package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

public class ChosenSpawnPointSetUp extends DataForServer implements DataForController {
    private String spawnPointColor;

    public ChosenSpawnPointSetUp(String nickname, String color) {
        super(nickname);
        this.spawnPointColor = color;
    }

    @Override
    public void updateGame(Controller controller) {
        //TODO
    }
}
