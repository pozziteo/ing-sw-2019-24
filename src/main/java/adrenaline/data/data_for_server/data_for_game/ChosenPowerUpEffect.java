package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

public class ChosenPowerUpEffect extends DataForServer implements DataForController{
    private String targetName;
    private int squareId;

    public ChosenPowerUpEffect(String nickname, String targetName, int squareId) {
        super(nickname);
        this.targetName = targetName;
        this.squareId = squareId;
    }

    public ChosenPowerUpEffect(String nickname, int squareId) {
        super(nickname);
        this.squareId = squareId;
        this.targetName = null;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.usePowerUpEffect(super.getNickname (), targetName, squareId);
    }
}
