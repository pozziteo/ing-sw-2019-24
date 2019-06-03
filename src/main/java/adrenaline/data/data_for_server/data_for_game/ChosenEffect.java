package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

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
