package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

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
