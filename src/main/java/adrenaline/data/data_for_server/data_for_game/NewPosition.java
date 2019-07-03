package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

/**
 * This class is used to inform the model about the id of the square where the client wants to move
 */

public class NewPosition extends DataForServer implements DataForController {
    private int squareId;

    public NewPosition(String nickname, int squareId) {
        super(nickname);
        this.squareId = squareId;
    }

    /**
     * Getter method for square id
     * @return id
     */

    public int getSquareId() {
        return this.squareId;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.executeAction(super.getNickname (), squareId);
    }
}
