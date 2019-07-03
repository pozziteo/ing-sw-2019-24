package adrenaline.data.data_for_server.requests_for_model;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.data.data_for_server.data_for_game.DataForController;

/**
 * This class is used to request the client's own player board information from the server
 */

public class MyBoardRequest extends DataForServer implements DataForController {

    public MyBoardRequest(String nickname) {
        super(nickname);
    }

    @Override
    public void updateGame(Controller controller) {
        controller.getGameModel ().updatePlayerBoard (controller.getLobby().findPlayer (super.getNickname ()));
    }
}