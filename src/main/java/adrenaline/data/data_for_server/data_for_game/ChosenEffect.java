package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

/**
 * This class is used to inform the model about which weapon effect has been chosen for Shoot action
 */

public class ChosenEffect extends DataForServer implements DataForController {
    private int effectNumber;

    public ChosenEffect(String nickname, int effectNumber) {
        super(nickname);
        this.effectNumber = effectNumber;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.askTargets (super.getNickname (), effectNumber);
    }
}
