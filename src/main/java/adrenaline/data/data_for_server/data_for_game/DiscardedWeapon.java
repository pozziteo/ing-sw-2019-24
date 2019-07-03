package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

/**
 * This class is used to inform the model about which weapon has been discarded
 */

public class DiscardedWeapon extends DataForServer implements DataForController {
    private String weaponName;

    public DiscardedWeapon(String nickname, String weaponName) {
        super(nickname);
        this.weaponName = weaponName;
    }

    @Override
    public void updateGame(Controller controller) {
        controller.discardWeapon(super.getNickname (), weaponName);
    }
}
