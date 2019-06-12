package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

public class ChosenPowerUp extends DataForServer implements DataForController {
    private String powerUpName;

    public ChosenPowerUp(String nickname, String powerUpName) {
        super(nickname);
        this.powerUpName = powerUpName;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.choosePowerUpOption(super.getNickname(), powerUpName);
    }
}
