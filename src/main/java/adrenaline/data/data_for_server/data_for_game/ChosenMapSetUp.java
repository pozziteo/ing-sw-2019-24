package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

/**
 * This class is used to inform the model about which map has been chosen for the game
 */

public class ChosenMapSetUp extends DataForServer implements DataForController {
    private String filename;

    public ChosenMapSetUp(String nickname, String filename) {
        super(nickname);
        this.filename = filename;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.initializeMap(filename);
    }
}
