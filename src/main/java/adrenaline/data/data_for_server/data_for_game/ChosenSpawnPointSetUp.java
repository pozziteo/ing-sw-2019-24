package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

/**
 * This class is used to inform the model about the color of the room in which the client will spawn
 */

public class ChosenSpawnPointSetUp extends DataForServer implements DataForController {
    private String spawnPointColor;

    public ChosenSpawnPointSetUp(String nickname, String color) {
        super(nickname);
        this.spawnPointColor = color;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.setSpawnPoint(super.getNickname(), spawnPointColor);
    }
}
