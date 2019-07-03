package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

/**
 * This class is used to inform the model about which weapon has been chosen for Shoot action
 */

public class ChosenWeapon extends DataForServer implements DataForController {
    private String weapon;

    public ChosenWeapon(String nickname, String weapon) {
        super(nickname);
        this.weapon = weapon;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.sendModeOptions(super.getNickname (), weapon);
    }
}
