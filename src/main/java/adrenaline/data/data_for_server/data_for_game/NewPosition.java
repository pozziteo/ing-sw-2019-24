package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

public class NewPosition extends DataForServer implements DataForController {
    private int squareId;

    public NewPosition(String nickname, int squareId) {
        super(nickname);
        this.squareId = squareId;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.executeAction(super.getNickname (), squareId);
    }
}
