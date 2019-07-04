package adrenaline.data.data_for_server.data_for_game;

import adrenaline.controller.Controller;
import adrenaline.data.data_for_server.DataForServer;

/**
 * This class is used to inform the model about whether or not the client wants to
 * reload any weapon and if so, which one they want to reload
 */

public class ReloadResponse extends DataForServer implements DataForController {
    private boolean positive; //true if client wants to reload
    private String weaponName;
    private boolean isBeforeShoot;

    public ReloadResponse(String nickname, boolean positive, String weaponName, boolean isBeforeShoot) {
        super(nickname);
        this.positive = positive;
        this.weaponName = weaponName;
        this.isBeforeShoot = isBeforeShoot;
    }

    @Override
    public void updateGame(Controller controller) {
        if (!isBeforeShoot)
            controller.reloadWeapon(super.getNickname (), positive, weaponName);
        else
            controller.reloadWeaponBeforeShoot (super.getNickname (), positive, weaponName);
    }
}
