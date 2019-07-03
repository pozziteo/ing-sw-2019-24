package adrenaline.data.data_for_server.requests_for_model;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;
import adrenaline.data.data_for_server.data_for_game.DataForController;

/**
 * This class is used to request square information from the server
 */

public class SquareDetailsRequest extends DataForServer implements DataForController {

    public SquareDetailsRequest(String nickname) {
        super(nickname);
    }

    @Override
    public void updateGame(Controller controller) {
        controller.getGameModel ().updateSquareDetails (controller.getLobby().findPlayer (super.getNickname ()));
    }
}