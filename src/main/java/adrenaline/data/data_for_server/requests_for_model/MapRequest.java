package adrenaline.data.data_for_server.requests_for_model;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.data.data_for_server.data_for_game.DataForController;

public class MapRequest extends DataForServer implements DataForController {

    public MapRequest(String nickname) {
        super(nickname);
    }

    @Override
    public void updateGame(Controller controller) {
        controller.getGameModel ().updateMap (controller.getLobby().findPlayer (super.getNickname ()));
    }
}
