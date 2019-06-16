package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

public class ChosenPowerUp extends DataForServer implements DataForController {
    private String powerUpName;
    private boolean asAmmo;

    public ChosenPowerUp(String nickname, String powerUpName, boolean asAmmo) {
        super(nickname);
        this.powerUpName = powerUpName;
        this.asAmmo = asAmmo;
    }

    @Override
    public void updateGame(Controller controller) {
        if (asAmmo)
            controller.usePowerUpAsAmmo(super.getNickname(), powerUpName);
        else
            controller.choosePowerUpOption(super.getNickname(), powerUpName);
    }
}
